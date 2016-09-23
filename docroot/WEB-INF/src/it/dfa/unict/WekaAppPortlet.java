package it.dfa.unict;

import it.dfa.unict.pojo.AppInput;
import it.dfa.unict.pojo.InputFile;
import it.dfa.unict.pojo.Link;
import it.dfa.unict.pojo.Task;
import it.dfa.unict.util.Constants;
import it.dfa.unict.util.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.ProcessAction;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class WekaAppPortlet extends MVCPortlet {

	private final Log _log = LogFactoryUtil.getLog(WekaAppPortlet.class);

	public static String pilotScript;

	/**
	 * Initializes portlet's configuration with pilot-script file path.
	 * 
	 * @see com.liferay.util.bridges.mvc.MVCPortlet#init()
	 */
	@Override
	public void init() throws PortletException {
		super.init();
		pilotScript = getPortletContext().getRealPath(Constants.FILE_SEPARATOR)
				+ "WEB-INF/job/" + getInitParameter("pilot-script");
	}

	/**
	 * Processes the 'multipart/form-data' form uploading file, gets the jobs
	 * label finally submits job towards a list of enabled DCI specified in the
	 * configuration.
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws IOException
	 * @throws PortletException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@ProcessAction(name = "submit")
	public void submit(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException,
			PortletException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		AppInput appInput = new AppInput();

		// TODO: This should be replaced with a value taken from preferences.
		// Represents the application identifier assigned to WEKA in the
		// Futuregateway
		appInput.setApplication(8);

		// Just a description of the job, could passed from the JSP maybe.
		appInput.setDescription("W");

		PortletPreferences preferences = actionRequest.getPreferences();

		String JSONAppPrefs = GetterUtil.getString(preferences.getValue(
				Constants.APP_PREFERENCES, null));
		AppPreferences appPrefs = Utils.getAppPreferences(JSONAppPrefs);

		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.TS_FORMAT);
		String timestamp = dateFormat.format(Calendar.getInstance().getTime());

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		User user = themeDisplay.getUser();
		String username = user.getScreenName();

		UploadPortletRequest uploadRequest = PortalUtil
				.getUploadPortletRequest(actionRequest);

		File uploadedFile = processInputFile(uploadRequest, username,
				timestamp, appInput);

		if (uploadedFile != null && uploadedFile.length() == 0) {
			SessionErrors.add(actionRequest, "empty-file");
		} else {
			String[] inputSandbox = null;

			List<InputFile> inputFiles = new ArrayList<InputFile>();
			if (uploadedFile != null) {
				InputFile inputFile = new InputFile();
				inputFile.setName(uploadedFile.getName());

				inputFiles.add(inputFile);
				// Path to the uploaded file in the Liferay server
				inputSandbox = new String[] { uploadedFile.getAbsolutePath() };
			}

			appInput.setInputFiles(inputFiles);

			// this should be passed from the UI
			appInput.getArguments().add("weka.classifiers.bayes.NaiveBayes");
			for (InputFile inputFile : inputFiles) {
				appInput.getArguments().add(inputFile.getName());
			}

			_log.info(appInput);

			FutureGatewayClient client = new FutureGatewayClient();

			// 1. Create FG task
			Task t = client.createTask(appInput, username);
			_log.info(t);
			if (t.getStatus().equals("WAITING") && inputSandbox != null) {
				// 2. upload input file
				String uploadPath = "";
				List<Link> links = t.getLinks();
				for (Link link : links) {
					if (link.getRel().equals("input")) {
						uploadPath = link.getHref();
						break;
					}
				}
				String t2 = client.uploadFile(uploadPath, inputSandbox);
				_log.info(t2);

				/*
				 * TODO Check the FG response to see if the file was correctly
				 * uploaded --> "gestatus": "triggered" in the respose notify
				 * user that task was correctly submitted and he can check
				 * status on my-jobs page
				 */
			} else {
				// TODO Manage this condition
			}

		}

		// Hide default Liferay success/error messages
		PortletConfig portletConfig = (PortletConfig) actionRequest
				.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		LiferayPortletConfig liferayPortletConfig = (LiferayPortletConfig) portletConfig;
		SessionMessages.add(actionRequest, liferayPortletConfig.getPortletId()
				+ SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
	}

	/**
	 * @param uploadRequest
	 * @param username
	 * @param timestamp
	 * @param appInput
	 * @return
	 * @throws IOException
	 */
	private File processInputFile(UploadPortletRequest uploadRequest,
			String username, String timestamp, AppInput appInput)
			throws IOException {

		File file = null;
		String fileInputName = "fileupload";

		String sourceFileName = uploadRequest.getFileName(fileInputName);

		if (Validator.isNotNull(sourceFileName)) {
			_log.debug("Uploading file: " + sourceFileName + " ...");

			String fileName = FileUtil.stripExtension(sourceFileName);
			_log.debug(fileName);

			String extension = FileUtil.getExtension(sourceFileName);
			_log.debug(extension);

			// Get the uploaded file as a file.
			File uploadedFile = uploadRequest.getFile(fileInputName, true);
			File folder = new File(Constants.ROOT_FOLDER_NAME);
			// This is our final file path.
			file = new File(folder.getAbsolutePath() + Constants.FILE_SEPARATOR
					+ username + "_" + timestamp + "_" + fileName
					+ ((!extension.isEmpty()) ? "." + extension : ""));
			FileUtil.move(uploadedFile, file);

		}
		return file;
	}
}

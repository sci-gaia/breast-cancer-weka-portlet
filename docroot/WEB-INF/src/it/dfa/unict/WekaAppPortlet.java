package it.dfa.unict;

import it.dfa.unict.pojo.AppInput;
import it.dfa.unict.pojo.InputFile;
import it.dfa.unict.pojo.Link;
import it.dfa.unict.pojo.Task;
import it.dfa.unict.util.Constants;
import it.dfa.unict.util.Utils;

import java.io.File;
import java.io.IOException;
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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.sun.jersey.api.client.UniformInterfaceException;

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

	@ProcessAction(name = "uploadFile")
	public void uploadFile(ActionRequest actionRequest,
			ActionResponse actionResponse) {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		User user = themeDisplay.getUser();
		String username = user.getScreenName();

		UploadPortletRequest uploadRequest = PortalUtil
				.getUploadPortletRequest(actionRequest);
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.TS_FORMAT);
		String timestamp = dateFormat.format(Calendar.getInstance().getTime());
		try {

			File uploadedFile = processInputFile(uploadRequest, username,
					timestamp);
			if (uploadedFile != null && uploadedFile.length() == 0) {
				SessionErrors.add(actionRequest, "empty-file");
			} else {
				InputFile inputFile = new InputFile();
				inputFile.setName(uploadedFile.getName());

				// Path to the uploaded file in the Liferay server
				String filePath = uploadedFile.getAbsolutePath();

				_log.debug("PATH: " + filePath);

				PortalUtil.copyRequestParameters(actionRequest, actionResponse);
				actionResponse.setRenderParameter("filePath", filePath);
				actionResponse.setRenderParameter("filter",
						ParamUtil.getString(uploadRequest, "filters"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Hide default Liferay success/error messages
		PortletConfig portletConfig = (PortletConfig) actionRequest
				.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		LiferayPortletConfig liferayPortletConfig = (LiferayPortletConfig) portletConfig;
		SessionMessages.add(actionRequest, liferayPortletConfig.getPortletId()
				+ SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
	}

	@ProcessAction(name = "submit")
	public void submit(ActionRequest actionRequest,
			ActionResponse actionResponse) {

		PortletPreferences preferences = actionRequest.getPreferences();
		String JSONAppPrefs = GetterUtil.getString(preferences.getValue(
				Constants.APP_PREFERENCES, null));
		_log.info(JSONAppPrefs);
		AppPreferences appPrefs =

		Utils.getAppPreferences(JSONAppPrefs);
		_log.debug(appPrefs);

		if (appPrefs.getApplicationId() <= 0) {
			SessionErrors.add(actionRequest, "wrong-app-id");
		} else if (Validator.isIPAddress(appPrefs.getFgHost())
				|| Validator.isHostName(appPrefs.getFgHost())) {

			AppInput appInput = new AppInput();
			appInput.setApplication(appPrefs.getApplicationId());

			// Just a description of the job, could passed from the JSP maybe.
			appInput.setDescription("WEKA Application");

			String[] inputSandbox = null;
			String inputFilePath = ParamUtil.getString(actionRequest,
					"fileName");
			File inputARFF = new File(inputFilePath);
			List<InputFile> inputFiles = new ArrayList<InputFile>();

			InputFile inputFile = new InputFile();
			inputFile.setName(inputARFF.getName());
			inputFiles.add(inputFile);

			inputSandbox = new String[] { inputFilePath };
			appInput.setInputFiles(inputFiles);

			String filter = ParamUtil.getString(actionRequest, "filter", null);
			if (filter != null && !filter.isEmpty()) {
				appInput.getArguments().add("-f " + filter);
			}

			String folds = ParamUtil.getString(actionRequest, "folds", null);
			if (folds != null && !folds.isEmpty()) {
				appInput.getArguments().add(" -x " + folds);
			}

			appInput.getArguments().add(inputARFF.getName());

			String classifier = ParamUtil
					.getString(actionRequest, "classifier");
			appInput.getArguments().add(classifier);

			_log.info(appInput);
			FutureGatewayClient client = new FutureGatewayClient(
					appPrefs.getFgHost(), appPrefs.getFgPort(),
					appPrefs.getFgAPIVersion());

			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
					.getAttribute(WebKeys.THEME_DISPLAY);
			User user = themeDisplay.getUser();
			String username = user.getScreenName();
			// 1. Create FG task
			try {
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
					// TODO Check the FG response to see if the file was
					// correctly uploaded --> "gestatus": "triggered" in the
					// respose notify user that task was correctly submitted and
					// he can check status on my-jobs page
				} else {
					// TODO Manage this condition
				}
			} catch (UniformInterfaceException | IOException e) {
				_log.error(e.getMessage());
				SessionErrors.add(actionRequest, "error");
				actionResponse.setRenderParameter("jspPage", "/jsps/view.jsp");
			}

			actionResponse.setRenderParameter("jspPage", "/jsps/submit.jsp");
		} else {
			SessionErrors.add(actionRequest, "wrong-fg-host");
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
	 * @return
	 * @throws IOException
	 */
	private File processInputFile(UploadPortletRequest uploadRequest,
			String username, String timestamp) throws IOException {

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

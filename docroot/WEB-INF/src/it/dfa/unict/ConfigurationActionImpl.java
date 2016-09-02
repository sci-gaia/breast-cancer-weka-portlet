package it.dfa.unict;

import it.dfa.unict.util.Constants;
import it.dfa.unict.util.Utils;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class ConfigurationActionImpl implements ConfigurationAction {

	private final Log _log = LogFactoryUtil
			.getLog(ConfigurationActionImpl.class);

	@Override
	public void processAction(PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

		String portletResource = ParamUtil.getString(actionRequest,
				"portletResource");

		_log.debug("processAction()");
		String cmd = ParamUtil.getString(actionRequest, Constants.CMD, "");
		_log.debug("Command: " + cmd);

		switch (cmd) {
		case Constants.SAVE_PREFS:
			savePrefereces(actionRequest, portletResource);
			break;
		case Constants.SAVE_PILOT:
			savePilot(actionRequest, portletResource);
			break;
		default:
			_log.warn("Unsupported command.");
			SessionErrors.add(actionRequest, "usupported-action");
		}

	}

	@Override
	public String render(PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
			throws Exception {

		String page = ParamUtil.getString(renderRequest, "render-page",
				Constants.CONFIGURATION_PAGE);

		_log.debug("Loading: " + Constants.CONFIGURATION_JSPS_FOLDER + page);
		return Constants.CONFIGURATION_JSPS_FOLDER + page;
	}

	private void savePrefereces(ActionRequest actionRequest,
			String portletResource) throws PortalException, SystemException {

		PortletPreferences preferences = PortletPreferencesFactoryUtil
				.getPortletSetup(actionRequest, portletResource);

		AppPreferences appPreferences_new = new AppPreferences();

		String fgHost = ParamUtil.getString(actionRequest,
				"fgHost", "");
		int fgPort = ParamUtil.getInteger(actionRequest,
				"fgPort");
		String fgAPIVersion = ParamUtil.getString(actionRequest,
				"fgAPIVersion", "");
		int applicationId = ParamUtil.getInteger(actionRequest,
				"applicationId");
		
		String pilotScript = ParamUtil.getString(actionRequest,
				"pref_pilotScript", "");

		appPreferences_new.setFgHost(fgHost);
		appPreferences_new.setFgPort(fgPort);
		appPreferences_new.setFgAPIVersion(fgAPIVersion);
		appPreferences_new.setApplicationId(applicationId);
		appPreferences_new.setPilotScript(pilotScript);


		String JSONAppPrefs_new = JSONFactoryUtil
				.looseSerialize(appPreferences_new);
		_log.debug(JSONAppPrefs_new);

		try {
			preferences.setValue(Constants.APP_PREFERENCES, JSONAppPrefs_new);
			preferences.store();
			_log.debug(preferences.getValue(Constants.APP_PREFERENCES, null));
			SessionMessages.add(actionRequest, Constants.CONFIG_SAVED_SUCCESS);
		} catch (ReadOnlyException e) {
			_log.error(e.getMessage());
			SessionErrors.add(actionRequest, e.getMessage());
			e.printStackTrace();
		} catch (ValidatorException e) {
			_log.error(e.getMessage());
			SessionErrors.add(actionRequest, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			_log.error(e.getMessage());
			SessionErrors.add(actionRequest, e.getMessage());
			e.printStackTrace();
		}

	}

	private void savePilot(ActionRequest actionRequest, String portletResource)
			throws IOException {
		String pilotScript = ParamUtil.getString(actionRequest, "pilotScript");
		pilotScript.replaceAll("\r", "");
		Utils.string2File(WekaAppPortlet.pilotScript, pilotScript);

		SessionMessages.add(actionRequest, "pilot-update-success");
	}

}

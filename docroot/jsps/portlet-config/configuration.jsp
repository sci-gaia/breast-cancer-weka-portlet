<%-- 
    Document   : edit.jsp
    Created on : Feb 15, 2016, 10:08:35 AM
    Author     : mario
--%>

<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="it.dfa.unict.AppPreferences"%>
<%@page import="it.dfa.unict.util.Constants"%>
<%@page import="it.dfa.unict.util.Utils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@include file="../../init.jsp"%>

<%
	PortletPreferences preferences = null;

	String portletResource = ParamUtil.getString(request,
			"portletResource");

	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(
				request, portletResource);
	}

	String JSONAppPrefs = GetterUtil.getString(preferences.getValue(
			Constants.APP_PREFERENCES, null));
	AppPreferences appPreferences = Utils
			.getAppPreferences(JSONAppPrefs);
%>

<liferay-portlet:actionURL portletConfiguration="true"
	var="savePreferencesUrl">
	<portlet:param name="<%=Constants.CMD%>"
		value="<%=Constants.SAVE_PREFS%>" />
</liferay-portlet:actionURL>


<liferay-portlet:renderURL portletConfiguration="true"
	var="pilotScriptUrl">
	<liferay-portlet:param name="render-page"
		value="<%=Constants.VIEW_PILOT_PAGE%>" />
	<liferay-portlet:param name="pilotScript"
		value="<%=appPreferences.getPilotScript()%>" />
</liferay-portlet:renderURL>

<liferay-ui:success key="<%=Constants.CONFIG_SAVED_SUCCESS%>"
	message="<%=Constants.CONFIG_SAVED_SUCCESS%>" />

<aui:form action="${savePreferencesUrl}" name="aForm" method="post">
	<aui:fieldset label="generic-pref-lbl">
		<aui:field-wrapper>
			<aui:button name="save" value="save" type="submit" />
		</aui:field-wrapper>
		<aui:layout>
			<aui:column columnWidth="50" first="true">
				<aui:input type="text" name="fgHost" id="fgHostId"
					label="Futuregateway host:" size="60"
					value="<%=appPreferences.getFgHost()%>">
					<aui:validator name="required" />
				</aui:input>

				<aui:input type="text" name="fgPort" id="fgPortId"
					label="Futuregateway port:" size="60"
					value="<%=(appPreferences.getFgPort() == Constants.HTTP_PORT || appPreferences
										.getFgPort() == 0) ? ""
										: appPreferences.getFgPort()%>">
					<aui:validator name="digits" errorMessage="port-number" />
				</aui:input>

				<aui:input type="text" name="fgAPIVersion" id="fgAPIVersionId"
					label="API version:" size="60"
					value="<%=appPreferences.getFgAPIVersion()%>">
					<aui:validator name="required" />
				</aui:input>

				<aui:input type="text" name="applicationId" id="applicationId"
					label="Application identifier" size="60"
					value="<%=appPreferences.getApplicationId()%>">
					<aui:validator name="required" />
					<aui:validator name="digits" errorMessage="app-id" />
				</aui:input>

				<liferay-ui:success key="pilot-update-success"
					message="pilot-update-success" />
				<%-- <aui:button id="pilot" name="piltot_btn" value="Edit pilot script" --%>
				<%-- onClick="<%= pilotScriptUrl %>" /> --%>
			</aui:column>

		</aui:layout>
	</aui:fieldset>
</aui:form>

<%-- 
    Document   : edit.jsp
    Created on : Feb 24, 2016, 10:08:35 AM
    Author     : mario
--%>
<%@page import="it.dfa.unict.WekaAppPortlet"%>

<%@page import="it.dfa.unict.util.Utils"%>
<%@page import="it.dfa.unict.util.Constants"%>
<%@include file="../../init.jsp"%>
<%
String pilotFilePath = WekaAppPortlet.pilotScript;


	String pilotScript = Utils.file2String(pilotFilePath);
%>
<liferay-portlet:renderURL portletConfiguration="true" var="backURL">
	<liferay-portlet:param name="render-page"
		value="<%=Constants.CONFIGURATION_PAGE%>" />
</liferay-portlet:renderURL>

<liferay-ui:header backLabel="&laquo; Back" title=""
	backURL="<%=backURL%>" />

<liferay-portlet:actionURL portletConfiguration="true"
	var="savePilotUrl">
	<portlet:param name="<%=Constants.CMD%>"
		value="<%=Constants.SAVE_PILOT%>" />
</liferay-portlet:actionURL>

<aui:fieldset label="pilot-config">
	<aui:layout>
		<aui:column columnWidth="100" first="true">
			<aui:form action="<%=savePilotUrl%>" method="post">
				<aui:input type="textarea" name="pilotScript" id="pilotScript"
					label="pilot-script" rows="20" cols="100" value="<%=pilotScript%>" />
				<aui:field-wrapper>
					<aui:button name="save" value="save" type="submit" />
					<aui:button name="cancel" value="reset" type="reset" />
				</aui:field-wrapper>
			</aui:form>
		</aui:column>
	</aui:layout>
</aui:fieldset>

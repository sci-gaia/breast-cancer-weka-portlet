<%@page import="java.io.File"%>
<%@page import="javax.portlet.WindowState"%>
<%@page import="com.liferay.portal.kernel.log.Log"%>
<%@page import="it.dfa.unict.WekaAppPortlet"%>
<%@page import="com.liferay.portal.kernel.log.LogFactoryUtil"%>
<%@page import="java.util.Calendar"%>
<%@page import="it.dfa.unict.util.Constants"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="../init.jsp"%>


<%
	String filePath = ParamUtil.getString(renderRequest, "filePath", "");
	String filter = ParamUtil.getString(renderRequest, "filter", "");

	PortletPreferences preferences = renderRequest.getPreferences();

	String JSONAppPrefs = GetterUtil.getString(preferences.getValue(
	Constants.APP_PREFERENCES, null));

	boolean isConfigured = true;

	if (JSONAppPrefs == null || JSONAppPrefs.isEmpty()) {
		isConfigured = false;
	}
	String tabsName = "Preprocessing, Outputs";
	String fileName = "";
	if (filePath != null && !filePath.isEmpty()) {
		File f = new File(filePath);
		fileName = f.getName();
		tabsName = "Classify, Outputs";
	}
%>

<aui:layout>
	<aui:column columnWidth="50" first="true">
		<img src="<%=request.getContextPath()%>/images/AppLogo.png"
			height="50%" width="50%" />
	</aui:column>
	<aui:column columnWidth="50" last="true">
		<%=LanguageUtil.get(portletConfig,
							themeDisplay.getLocale(), "brief-description")%>
	</aui:column>
</aui:layout>

<c:choose>
	<c:when test="<%=isConfigured%>">
		<liferay-ui:tabs names="<%=tabsName%>" refresh="<%=false%>">
			<c:choose>
				<c:when test="<%=filePath == null || filePath.isEmpty()%>">
					<liferay-ui:section>
						<liferay-ui:error key="wrong-app-id" message="wrong-app-id" />
						<liferay-ui:error key="wrong-fg-host" message="wrong-fg-host" />

						<portlet:actionURL name="uploadFile" var="uploadFileURL" />
						<aui:form action="<%=uploadFileURL%>" method="post"
							enctype="multipart/form-data">
							<aui:fieldset label="Preprocessing">
								<aui:input name="fileupload" label="Upload"
									title="Local file upload" type="file">
									<aui:validator name="acceptFiles">'data,csv,arff'</aui:validator>
								</aui:input>

								<aui:select label="filters" name="filters">
									<aui:option label="Select filter ..." value="" />
									<aui:option label="Missing values"
										value="weka.filters.unsupervised.attribute.ReplaceMissingValues" />
								</aui:select>
								<br />

								<aui:button type="hidden" value="Continue" name="continue" />
							</aui:fieldset>
						</aui:form>
					</liferay-ui:section>
				</c:when>
				<c:otherwise>
					<liferay-ui:section>
						<portlet:actionURL name="classify" var="classify" />
						<aui:form action="<%=classify%>" method="post">
							<aui:fieldset label="Classify">
								<p>
									From the uploaded <b><%=fileName%></b> file please select
									available algorithm and experiment type to be used for Mining
								</p>
								<h3>Test type</h3>
								<hr />
								<aui:input name="Crossfield validation" value="test"
									type="radio" />

								<aui:input name="training set" type="radio" value="test" />
								<hr />
								<aui:select label="Classifier" name="clasify">
									<aui:option label="Naive Bayes" value="naivebayes" />
								</aui:select>
								<br />

								<aui:button type="submit" value="submit" />
							</aui:fieldset>
						</aui:form>
					</liferay-ui:section>
				</c:otherwise>
			</c:choose>
			<liferay-ui:section>
				<h3>Outputs to be implemented...</h3>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="check-configuration" />
		</div>
	</c:otherwise>
</c:choose>

<aui:script>
	AUI().use('node', function(A) {
		console.log("CIAO");
		A.one('#<portlet:namespace/>fileupload').on('change', function(event) {
			A.one('#<portlet:namespace/>continue').set('type', 'submit');
		});
	});

	
</aui:script>
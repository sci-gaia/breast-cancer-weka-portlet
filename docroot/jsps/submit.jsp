<%@include file="../init.jsp"%>
<%
	String text1 = LanguageUtil.get(portletConfig,
			themeDisplay.getLocale(), "your-job");
	String text2 = LanguageUtil.get(portletConfig,
			themeDisplay.getLocale(), "success");
	String text3 = LanguageUtil.get(portletConfig,
			themeDisplay.getLocale(), "text3");
	String jobLabel = ParamUtil
			.getString(renderRequest, "jobLabel", "");
	String myJobs = LanguageUtil.get(portletConfig,
			themeDisplay.getLocale(), "my-jobs");
%>
<liferay-portlet:renderURL var="viewUrl" windowState="normal">
	<liferay-portlet:param name="mvcPath" value="/jsps/view.jsp" />
</liferay-portlet:renderURL>

<aui:layout>
	<aui:column columnWidth="50" first="true">
		<img src="<%=request.getContextPath()%>/images/AppLogo.png"
			height="100%" width="100%" />
		<aui:button id="view" name="view" value="perform-again"
			onClick="<%= viewUrl %>" />
	</aui:column>
	<aui:column columnWidth="50" last="true">
		<%=text1%>		
		<b><%=jobLabel%></b>
		<br />
		<br />
		<%=myJobs%>
		<a href="/my-jobs">MyJobs</a>.
	</aui:column>

</aui:layout>


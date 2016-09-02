<%-- 
    Document   : init
    Created on : Feb 15, 2016, 10:29:44 AM
    Author     : mario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portal.kernel.util.PrefsParamUtil"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.model.UserGroup"%>
<%@page import="com.liferay.portal.service.UserGroupLocalServiceUtil"%>
<%@page import="javax.portlet.PortletURL"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@taglib prefix="liferay-theme" uri="http://liferay.com/tld/theme"%>
<%@taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet"%>
<%@taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui"%>
<%@taglib prefix="liferay-util" uri="http://liferay.com/tld/util"%>

<%@taglib prefix="aui" uri="http://liferay.com/tld/aui"%>

<portlet:defineObjects/>
<liferay-theme:defineObjects/>

<%String currentURL = PortalUtil.getCurrentURL(request);%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>

<portlet:defineObjects />
<h2>WEKA Application</h2>
<p>
	<aui:a href="http://www.cs.waikato.ac.nz/ml/weka/" label="WEKA"></aui:a>
	is a collection of machine learning algorithms for data mining tasks.
	The algorithms can either be applied directly to a dataset or called
	from your own Java code. Weka contains tools for data pre-processing,
	classification, regression, clustering, association rules, and
	visualization.
<h3>Upload file</h3>
<hr />
<p></p>

<portlet:actionURL name="submit" var="uploadFileURL" />
<aui:form action="<%=uploadFileURL%>" method="post"
	enctype="multipart/form-data">

	<aui:input name="fileupload" title="Local file upload" type="file" />

	<!-- <aui:input name="url" title="URL" type="text" /> -->

	<p>From the uploaded file please select available algorithm and
		experiment type to be used for Mining</p>
	<h3>Test type</h3>
	<hr />

	<aui:select label="Test" name="test">
		<aui:option label="Cross-Validation" value="crossValidation"></aui:option>
	</aui:select>
	<h3>Classifiers</h3>
	<hr />
	<aui:select label="Classifier" name="clasify">
		<aui:option label="Naive Bayes" value="naivebayes"></aui:option>
	</aui:select>
	<br />

	<aui:button type="submit" value="submit"></aui:button>

</aui:form>
<!-- 
<portlet:renderURL var="clasfy">
<portlet:param name="mvcPath" value="/html/wekaapp/clasfy.jsp"></portlet:param>
</portlet:renderURL>
<a href="<%=clasfy%>">COntinue</a>
 -->

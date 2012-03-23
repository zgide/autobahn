<%@ include file="../common/includes.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" type="text/css"
    href="<c:url value="/js/jquery/tabs.css"/>" />
<h2><spring:message code="home.myprofile"
    text="My Profile" /></h2>

<div style="text-align: left;" class="home_image">
<br />
<div id="collection" style="padding: 15px">
<table class="class_intras">
    <tr>
        <td class="label"><spring:message code="myprofile.username" text="Username" /></td>
        <td>${authParameters.identifier}</td>
    </tr>
    <tr>
        <td class="label"><spring:message code="myprofile.organization" text="Organization" /></td>
        <td>${authParameters.organization}</td>
    </tr>
    <tr>
        <td class="label"><spring:message code="myprofile.projectMembership" text="Project membership" /></td>
        <td>${authParameters.projectMembership}</td>
    </tr>
    <tr>
        <td class="label"><spring:message code="myprofile.projectRole" text="Role" /></td>
        <td>${authParameters.projectRole}</td>
    </tr>
    <tr>
        <td class="label"><spring:message code="myprofile.email" text="Email" /></td>
        <td>${authParameters.email}</td>
    </tr>
    <tr>
        <td class="label"><spring:message code="myprofile.password" text="New Password" /></td>
        <td>
            <form:form commandName="changePassword" >
                <input type="hidden" name="_eventId" value="changePassword" />
                <input type="password" name="password" style="width:auto" size="10" />
                <input type="submit" value="Change" style="width: auto" />
            </form:form>
        </td>
    </tr>
</table>
<br />

<c:if test="${portsAllow != null && fn:length(portsAllow) > 0}">
    <table class="class_intras">
        <tr><th>
            <spring:message code="myprofile.port" text="Ports allowed (All other ports are denied)" />
        </th></tr>
        <c:forEach items="${portsAllow}" var="port" varStatus="loopStatus">
            <tr><td>${port}</td></tr>
        </c:forEach>
    </table>
    <br />
</c:if>

<c:if test="${(portsAllow == null || fn:length(portsAllow) <= 0) && portsDeny != null && fn:length(portsDeny) > 0}">
    <table class="class_intras">
        <tr><th>
            <spring:message code="myprofile.port" text="Ports denied (All other ports are allowed)" />
        </th></tr>
        <c:forEach items="${portsDeny}" var="port" varStatus="loopStatus">
            <tr><td>${port}</td></tr>
        </c:forEach>
    </table>
    <br />
</c:if>

<c:if test="${(portsAllow == null || fn:length(portsAllow) == 0) && (portsDeny == null || fn:length(portsDeny) <= 0)}">
    <br />
    All available ports are allowed.
</c:if>

<br />
<br />
</div>
</div>

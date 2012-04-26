<%@ include file="../common/includes.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" type="text/css"
    href="<c:url value="/js/jquery/tabs.css"/>" />
<h2><spring:message code="home.myprofile"
    text="My Profile" /></h2>

<div style="text-align: left;" class="home_image">
<br />
<div id="collection" style="padding: 15px">
The attributes listed below (except password) are transmitted when a request is submitted.
Each domain may choose to authorize the request or not based on these attributes.
<br/><br/>
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

Overview of restrictions on submitted requests:
<br/>
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

<c:if test="${vlanAllow != null}">
    <table class="class_intras">
        <tr><th>
            <spring:message code="myprofile.vlan" text="VLANs allowed (All other VLANs are denied)" />
        </th></tr>
        <tr><td>${vlanAllow}</td></tr>
    </table>
    <br />
</c:if>

<c:if test="${vlanAllow == null && vlanDeny != null}">
    <table class="class_intras">
        <tr><th>
            <spring:message code="myprofile.vlan" text="VLANs denied (All other VLANs are allowed)" />
        </th></tr>
        <tr><td>${vlanDeny}</td></tr>
    </table>
    <br />
</c:if>

<c:if test="${vlanAllow == null && vlanDeny == null}">
    <br />
    All available VLANs are allowed.
</c:if>

<c:if test="${maxCapacity >= 0}">
    <br />
    Maximum allowed capacity limit for a single request is ${maxCapacity} Mpbs.
</c:if>

<c:if test="${maxCapacity < 0}">
    <br />
    You have no maximum allowed capacity limit for a single request.
</c:if>

<br />
<br />
</div>
</div>

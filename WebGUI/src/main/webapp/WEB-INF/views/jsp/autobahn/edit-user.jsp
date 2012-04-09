<%@ include file="../common/includes.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" type="text/css"
    href="<c:url value="/js/jquery/tabs.css"/>" />
<h2><spring:message code="home.usersadmin" text="User Rights: ${userProfile.username}" /></h2>

<div style="text-align: left;" class="home_image">
<table width="100%">
    <tr>
        <th><spring:message code="useredit.key" text="Key" /></th>
        <th><spring:message code="userview.value" text="Value" /></th>
    </tr>
    <form:form commandName="userProfileManager">
        <input type="hidden" name="username" value="${userProfile.username}" />
        <tr>
            <td>Allowed ports</td>
            <td>
                <input type="text" name="portsAllow" style="width: auto" size="80" 
                value="${userProfile.portsAllow}" />
            </td>
        </tr>
        <tr>
            <td>Denied ports</td>
            <td>
                <input type="text" name="portsDeny" style="width: auto" size="80" 
                value="${userProfile.portsDeny}" />
            </td>
        </tr>
        <tr>
            <td>Allowed VLANs</td>
            <td>
                <input type="text" name="vlanAllow" style="width: auto" size="80" 
                value="${userProfile.vlanAllow}" />
            </td>
        </tr>
        <tr>
            <td>Denied VLANs</td>
            <td>
                <input type="text" name="vlanDeny" style="width: auto" size="80" 
                value="${userProfile.vlanDeny}" />
            </td>
        </tr>
        <tr>
            <td>Allowed maximum capacity per request</td>
            <td>
                <input type="text" name="maxCapacity" style="width: auto" size="10" 
                value="${userProfile.maxCapacity}" />
            </td>
        </tr>
        <tr>
            <td colspan="5" align="center" style="padding-top: 20px">
                <input type="submit" value="Save" style="width: auto" />
            </td>
        </tr>
    </form:form>
</table>
</div>

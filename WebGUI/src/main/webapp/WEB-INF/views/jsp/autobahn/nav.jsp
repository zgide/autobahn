<%@ include file="../common/includes.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<h1><span style="margin-left:50px;">&nbsp;</span></h1>

<sec:authorize ifAllGranted="ROLE_ANONYMOUS">
<fmt:message key="lnk.request" var="lnk"/>
<a href="<c:url value="/${lnk}"/>">Login</a>
</sec:authorize>

<fmt:message key="lnk.home" var="lnk"/>
<a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.home"/></a>

<sec:authorize ifAllGranted="ROLE_USER">
<fmt:message key="lnk.request" var="lnk"/>
<a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.request"/></a>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_USER">  
<fmt:message key="lnk.reservations" var="lnk"/>
<a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.reservations"/></a>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_USER">
<fmt:message key="lnk.reservationsMap" var="lnk"/>
<a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.reservationsMap"/></a>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_USER">
<fmt:message key="lnk.myprofile" var="lnk"/>
<a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.myprofile"/></a>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_NETWORKADMIN">
<fmt:message key="lnk.request" var="lnk"/>
<a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.request"/></a>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_NETWORKADMIN">  
<fmt:message key="lnk.reservations" var="lnk"/>
<a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.reservations"/></a>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_NETWORKADMIN">
<fmt:message key="lnk.reservationsMap" var="lnk"/>
<a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.reservationsMap"/></a>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_NETWORKADMIN">
<fmt:message key="lnk.myprofile" var="lnk"/>
<a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.myprofile"/></a>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_NETWORKADMIN">
  <fmt:message key="lnk.domainLogs" var="lnk"/>
  <a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.domainLogs"/></a>      
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_NETWORKADMIN">
  <fmt:message key="lnk.statistics" var="lnk"/>
  <a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.statistics"/></a>      
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_NETWORKADMIN">
  <br/><br/><br/><br/>
  <hr/>NOC Panel
  <hr/>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_NETWORKADMIN">
  <fmt:message key="lnk.intras" var="lnk"/>
  <a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.intras"/></a>      
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_NETWORKADMIN">
  <fmt:message key="lnk.calendar" var="lnk"/>
  <a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.calendar"/></a>      
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_ADMINISTRATOR">
<fmt:message key="lnk.request" var="lnk"/>
<a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.request"/></a>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_ADMINISTRATOR">  
<fmt:message key="lnk.reservations" var="lnk"/>
<a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.reservations"/></a>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_ADMINISTRATOR">
<fmt:message key="lnk.reservationsMap" var="lnk"/>
<a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.reservationsMap"/></a>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_ADMINISTRATOR">
<fmt:message key="lnk.myprofile" var="lnk"/>
<a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.myprofile"/></a>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_ADMINISTRATOR">
  <fmt:message key="lnk.domainLogs" var="lnk"/>
  <a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.domainLogs"/></a>	  
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_ADMINISTRATOR">
  <fmt:message key="lnk.statistics" var="lnk"/>
  <a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.statistics"/></a>      
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_ADMINISTRATOR">
  <fmt:message key="lnk.domainSettings" var="lnk"/>
  <a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.domainSettings"/></a>	
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_ADMINISTRATOR">
  <fmt:message key="lnk.user_administration" var="lnk"/>
  <a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.user_administration"/></a>      
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_ADMINISTRATOR">
  <br/>
  <hr/>NOC Panel
  <hr/>
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_ADMINISTRATOR">
  <fmt:message key="lnk.intras" var="lnk"/>
  <a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.intras"/></a>      
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_ADMINISTRATOR">
  <fmt:message key="lnk.calendar" var="lnk"/>
  <a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.calendar"/></a>      
</sec:authorize>

<sec:authorize ifAllGranted="ROLE_ADMINISTRATOR">
  <fmt:message key="lnk.dmacl" var="lnk"/>
  <a href="<c:url value="/${lnk}"/>"><fmt:message key="nav.dmacl"/></a>      
</sec:authorize>

<%@ include file="../common/endnav.jsp" %>

<br/><br/>

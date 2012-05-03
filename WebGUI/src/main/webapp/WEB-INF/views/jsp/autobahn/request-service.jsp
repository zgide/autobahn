<%@ include file="../common/includes.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<h2><spring:message code="service.htitle" text="Request Dynamic Circuit" /></h2>

<style>
.images {
	/*background:#fff url(h300.png) repeat-x;*/
	border:1px solid #ccc;
	position:relative;	
	height:auto;
	
	width:770px;
	float:left;	
	margin:30px;
	cursor:hand;
	
	z-index:1;
	
	/* CSS3 tweaks for modern browsers */
	-moz-border-radius:20px;
	-webkit-border-radius:20px;
	-moz-box-shadow:0 0 25px #666;
	/*-webkit-box-shadow:0 0 25px #666;	*/
	
	

}
	.pos1 {	
	margin: 10px;
	margin-left:70px;
	
}
.wizard {
border:1px solid #ccc;
position:relative;	
cursor:pointer;
 height: 530px;
}

</style>

<div class="images">
<c:if test="${serviceId!=null}">
	<br style="height: 50px"></br>
	<h3 style="margin: 20px"><spring:message code="service.submitted.successfully"/></h3>
	<!--p style="margin:20px"><spring:message code="service.submitted.view"/><a href="<c:url value="/portal/secure/reservations.htm?idm=${service.userHomeDomain}#${serviceId}"/>"><spring:message code="service.submitted.goto"/></a></p-->
	<p style="margin:20px"><spring:message code="service.submitted.view"/><a href="<c:url value="/portal/secure/reservations_select.htm?idm=${serviceModel.service.userHomeDomain}"/>"><spring:message code="service.submitted.goto"/></a></p>
</c:if>

<c:if test="${serviceId==null}">
<form:form commandName="serviceModel">
<br>
<table width="100%">
	<tr>
		<td class="label">
		    <spring:message code="service.userHomeDomain"/> 
		    <img src="<c:url value="/images/help_icon.png"/>" 
		        alt="Select the domain where request will be submitted" 
		        title="Select the domain where request will be submitted" />
		    <br/>
		    <span class="error">
		        <form:errors path="service.userHomeDomain"/>
		    </span>
		</td>
		<td class="value">
            <c:if test="${home!=null}">
                <c:if test="${fn:length(serviceModel.service.reservations) != 0}">
                    <form:select path="service.userHomeDomain">
                        <form:option value="${home}"/>
                    </form:select>
                </c:if>
                <c:if test="${fn:length(serviceModel.service.reservations) == 0}">
                    <form:select path="service.userHomeDomain">
                        <form:options items="${idms}"/>
                    </form:select>
                </c:if>
            </c:if>
            <c:if test="${home==null}">
                <form:select path="service.userHomeDomain">
                    <form:options items="${idms}"/>
                </form:select>
            </c:if>
		</td>
		<!--td class="error"><form:errors path="service.userHomeDomain"/></td-->
	</tr>
    <tr>
        <td class="label">
            <spring:message code="service.destinationDomain"/>
            <img src="<c:url value="/images/help_icon.png"/>" 
                alt="Select the domain that contains the destination port" 
                title="Select the domain that contains the destination port" />
            <br/>
            <span class="error">
                <form:errors path="destinationDomain"/>
            </span>
        </td>
        <td class="value">
            <form:select path="destinationDomain">
                <form:options items="${idms}"/>
                <option value="IDCP">IDCP</option>
            </form:select>
        </td>
	</tr>
	<tr>
		<td class="label">
		    <spring:message code="service.userName"/>
            <img src="<c:url value="/images/help_icon.png"/>" 
                alt="Your username will be submitted with the request" 
                title="Your username will be submitted with the request" />
		    <br/>
		    <span class="error">
		        <form:errors path="service.userName"/>
		    </span>
		</td>
		<td class="value">
			<form:input path="service.userName" disabled="true"/>
		</td>
		<!--td class="error"><form:errors path="service.userName"/></td-->
	</tr>
	<tr>
		<td class="label">
		    <spring:message code="service.justification"/>
            <img src="<c:url value="/images/help_icon.png"/>" 
                alt="Enter some free text here to explain or identify the purpose of this request" 
                title="Enter some free text here to explain or identify the purpose of this request" />
		    <br/>
		    <span class="error">
		        <form:errors path="service.justification"/>
		    </span>
		</td>
		<td class="value">
			<form:textarea rows="8" cols="65" path="service.justification"/>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
			<td class="error">&nbsp;
			<c:if test="${error!=null}">${error}</c:if>
			</td>
		<td>&nbsp;</td>
	</tr>
</table>

<div id="collection">
<h2>Service Reservations <input type="submit" name="_eventId_add" value="<spring:message code="reservation.add"/>"/></h2>


<c:if test="${fn:length(serviceModel.service.reservations) != 0}">

<br/>

		<table width="100%">
		<tr>
			<th><spring:message code="service.reservations.action" text="Action" /></th>
			<th><spring:message code="reservation.startTime" text="Start time"/></th>
			<th><spring:message code="reservation.endTime" text="End time"/></th>
			<th><spring:message code="reservation.startPort" text="Start port"/></th>
			<th><spring:message code="reservation.startMode" text="Start mode"/></th>
			<th><spring:message code="reservation.startVlan" text="Start vlan"/></th>
			<th><spring:message code="reservation.endPort" text="End port"/></th>
			<th><spring:message code="reservation.endMode" text="End mode"/></th>
			<th><spring:message code="reservation.endVlan" text="End vlan"/></th>
			<th><spring:message code="reservation.maxDelay" text="Delay [ms]"/></th>
			<th><spring:message code="reservation.capacity" text="Capacity [Mbits/s]"/></th>
			<th><spring:message code="reservation.mtu" text="Mtu"/></th>
	
		</tr>  
		<c:forEach items="${serviceModel.service.reservations}" var="item" varStatus="loop">
			<spring:bind path="serviceModel.service.reservations">
				<tr>
					<td>
						<a href="${flowExecutionUrl}&_eventId=remove&id=${loop.count-1}">Remove</a>
        			</td>
		            <c:if test="${item.processNow == true}">
		                <td>Starts immediately</td>
		            </c:if>
		            <c:if test="${item.processNow == false}">
                        <td>${item.startTime.time}</td>
		            </c:if>
					<td>${item.endTime.time}</td>
					<td>${item.startPortFriendlyName}</td>
					<td>VLAN</td>
					<td>${item.startPort.vlan}</td>
					<td>${item.endPortFriendlyName}</td>
					<td>VLAN</td>
					<td>${item.endPort.vlan}</td>
					<td>${item.maxDelay}</td>
					<td>${item.capacity/1000000}</td>
					<td>${item.mtu}</td>
				</tr>
			</spring:bind>
		</c:forEach>
	</table>
	
<br>	
</c:if>	
	
	
	
	</div>
	  <br>
	<table class="pos1">

	<tr>
	<td>
		<input type="submit" name="_eventId_cancel" value="<spring:message code="service.cancel"/>"/>
	</td>
	<td>
		<input type="submit" name="_eventId_submit" value="<spring:message code="service.submit"/>"/>
	</td>
	</tr>
	</table>
	</form:form>
</c:if>
	</div>

<script type="text/javascript">
    var myselect=document.getElementById("service.userHomeDomain")
    for (var i=0; i<myselect.options.length; i++){
        if (myselect.options[i].value=="${authParameters.organization}") {
            myselect.options[i].selected=true
            break
            }
        }

    myselect=document.getElementById("destinationDomain")
    for (var i=0; i<myselect.options.length; i++){
        if (myselect.options[i].value=="${authParameters.organization}") {
            myselect.options[i].selected=true
            break
            }
        }
</script>

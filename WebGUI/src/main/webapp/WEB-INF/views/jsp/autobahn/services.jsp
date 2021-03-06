<%@ include file="../common/includes.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style>
	.cancelButton:hover input {
		margin-left: 2px;
		margin-top: 2px;
	}
</style>

<script type="text/javascript" src="<c:url value="/js/jquery/jquery.validate.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/scrollable.css"/>"/>

<c:set var="size" value="${fn:length(services.services)}"></c:set>
<c:choose>
	<c:when test="${size %2 == 0}">
		<c:set var="flag" value="true"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="flag" value="false"></c:set>
	</c:otherwise>
</c:choose>
		
<c:set var="end" value="2"></c:set>
<c:set var="k" value="0"></c:set>
<c:set var="counter" value="1"></c:set>

<div id="actions" style="padding-left:15px; text-align:center"> 
<table>
<tr>
	<td style="width: 10%">
		<a class="prev" style="position: relative; float: left;" >&laquo;BACK</a>
	</td>
	<td class="navi" style="width: 90%">
	
	 <c:forEach begin="1" end="${size}" step="2" var="i">
   	    <c:set var="class" value=""></c:set>
   	    <c:if test="${i == 1}">
   	    	<c:set var="class" value="active"></c:set>
   	    </c:if>
   	    <c:if test="${i % 50 == 0}">
   	    	<br/>
   	    </c:if>
   	    <c:choose>
   	      <c:when test="${i == size}">
   	        <a href="#" class="${class}"></a>
   	      </c:when>
   	      <c:otherwise>
	   	    <a href="#" class="${class}"></a>
   	      </c:otherwise>
   	    </c:choose>
   	  </c:forEach>
	
	
	</td>
	<td style="width: 10%">
		<a class="next" style="position: relative; float: right;">NEXT&raquo;</a>
	</td>
<tr>
</table>
</div>

<div class="scrollable vertical" style="height:590px; overflow: hidden; ">
  <div class="items">
	<c:if test="${services.services==null}">
        <h3>No circuits available.</h3>
    </c:if>
 
 
 
<c:forEach items="${services.services}"  begin="${k}" varStatus="x">

<c:choose>
   <c:when test="${flag == 'true'}"> 
   					
   	<c:if test="${x.count %2 == 0}">
   				
  <div class="item" style="height:570px; overflow: auto;">
   	<c:forEach items="${services.services}" var="element" begin="${k}" end="${end-1}" varStatus="y">
   								
   	<h4 align="right" style="padding-bottom:10px;"><b> Service: ${element.bodID}</b></h4>
	<div id="coolection_service">
	<table width="300px">
		<tr>
			<td class=""><spring:message code="service.user.homeDoamin" text="Home Domain" /></td>
			<td class="" style="margin-left: 10px;">${element.user.homeDomain.name}</td>
		</tr>
		<tr>
			<td class=""><spring:message code="service.user.name" text="User" /></td>
			<td class="" style="margin-left: 10px;">${element.user.name} ${element.user.email}</td>
		</tr>
		<tr>
			<td class=""><spring:message code="service.jastification" text="Justification" /></td>
			<td class="" style="margin-left: 10px;">${element.justification}</td>
		</tr>
	</table>
	</div>
	<div id="collection">
	<table width="100%">
		<tr>
			<th><spring:message code="reservation.state" text="State"/></th>
			<th><spring:message code="reservation.startTime" text="Start time"/></th>
			<th><spring:message code="reservation.endTime" text="End time"/></th>
			<th><spring:message code="reservation.startPort" text="Start port"/></th>
			<th><spring:message code="reservation.startMode" text="Start mode"/></th>
			<th><spring:message code="reservation.startVlan" text="Start vlan"/></th>
			<th><spring:message code="reservation.endPort" text="End port"/></th>
			<th><spring:message code="reservation.endMode" text="End mode"/></th>
			<th><spring:message code="reservation.endVlan" text="End vlan"/></th>
			<th><spring:message code="reservation.capacity" text="Capacity"/></th>
			<th><spring:message code="reservation.mtu" text="Mtu"/></th>
		</tr>
        <c:set var="hideCancel" value="true"/>
		<c:forEach items="${element.reservations}" var="item" varStatus="loopStatus">
				<tr>
					<td title="${reservationDescriptions[item.state]}">
					   ${reservationStates[item.state]} (${item.state})
					</td>
					<td>${item.startTime.time}</td>
					<td>${item.endTime.time}</td>
					<td>${item.startPort.friendlyName}</td>
					<td> VLAN </td>
					<td>${item.startPort.vlan} </td>
					<td>${item.endPort.friendlyName}</td>
					<td> VLAN </td>
					<td>${item.endPort.vlan} </td>
					<td>${item.capacity/1000000}</td>
					<td>${item.mtu}</td>
				</tr>
				<c:if test="${item.state == 23}">
				    <tr><td colspan="11">Failure reason: ${item.failureCause}</td></tr>
				</c:if>
                <c:if test="${item.state == 9 || item.state == 10 || item.state == 4 || item.state == 5}">
                    <c:set var="hideCancel" value="false"/>
                </c:if>
		</c:forEach>
	</table>
<!--div style="position:relative;float:left;padding-top:20px"><a style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;" href="${flowExecutionUrl}&_eventId=cancel&id=${element.bodID}"><input id="cancel" name="Cancel" value="Cancel" type="submit" style="width:100px;" onclick="window.top.location='${flowExecutionUrl}&_eventId=cancel&id=${element.bodID}'" /></a-->
<script>
//alert($("#currentIdm").val());
</script>
    <c:if test="${hideCancel != true}">
<!--div style="position:relative;float:left;padding-top:20px"><a style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;" href="#" onclick="window.top.location=location.href + '&_eventId=cancel&id=${element.bodID}&currentIdm2=' + $('#currentIdm').val()"><input id="cancel" name="Cancel" value="Cancel" type="submit" style="width:100px;" onclick="window.top.location=location.href + '&_eventId=cancel&id=${element.bodID}&currentIdm2=' + $('#currentIdm').val()" /></a-->
<div class="cancelButton" style="position:relative;float:left;padding-top:20px"><span style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;"><input id="cancel" name="Cancel" value="Cancel" type="submit" style="width:100px;" onclick="jQuery.post(location.href + '&_eventId=cancel&id=${element.bodID}&currentIdm2=' + $('#currentIdm').val() )" /></span>

<!--				<a style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;"  href="<c:url value="/portal/secure/services-map.htm"/>?service=${element.bodID}&domain=${element.user.homeDomain.bodID}">-->
<!--<input id="view" name="view" value="View map" type="submit" style="width:100px" onclick="window.top.location='<c:url value="/portal/secure/services-map.htm"/>?service=${element.bodID}&domain=${element.user.homeDomain.bodID}'" /></a>-->
	</div>
    </c:if>
	<br><br><br>
 	</div>
   								
   	</c:forEach>
   </div>
   					
   	<c:set var="k" value="${x.count}"></c:set>
   	<c:set var="end" value="${x.count + 2}"></c:set>
   	<c:set var="counter" value="${x.count +1}"></c:set>	
   				
   </c:if>
   		
   	</c:when>
   			
   	<c:otherwise>
   	<c:choose>
   			
   	<c:when test="${x.count + 1 > size}">
   				
   		<div class="item"  style="height:570px; overflow: auto;">
   			<c:forEach items="${services.services}" var="element" begin="${k}" end="${end-1}" varStatus="y">
   	<h4 align="right" style="padding-bottom:10px;"><b> Service: ${element.bodID}</b></h4>
<div id="coolection_service">
	<table width="300px">
		<tr>
			<td class=""><spring:message code="service.user.homeDoamin" text="Home Domain" /></td>
			<td class="" style="margin-left: 10px;">${element.user.homeDomain.name}</td>
		</tr>
		<tr>
			<td class=""><spring:message code="service.user.name" text="User" /></td>
			<td class="" style="margin-left: 10px;">${element.user.name} ${element.user.email}</td>
		</tr>
		<tr>
			<td class=""><spring:message code="service.jastification" text="Justification" /></td>
			<td class="" style="margin-left: 10px;">${element.justification}</td>
		</tr>
	</table>
	</div>
	<div id="collection">
	<table width="100%">
		<tr>
			<th><spring:message code="reservation.state" text="State"/></th>
			<th><spring:message code="reservation.startTime" text="Start time"/></th>
			<th><spring:message code="reservation.endTime" text="End time"/></th>
			<th><spring:message code="reservation.startPort" text="Start port"/></th>
			<th><spring:message code="reservation.startMode" text="Start mode"/></th>
			<th><spring:message code="reservation.startVlan" text="Start vlan"/></th>
			<th><spring:message code="reservation.endPort" text="End port"/></th>
			<th><spring:message code="reservation.endMode" text="End mode"/></th>
			<th><spring:message code="reservation.endVlan" text="End vlan"/></th>
			<th><spring:message code="reservation.capacity" text="Capacity"/></th>
			<th><spring:message code="reservation.mtu" text="Mtu"/></th>
		</tr>
        <c:set var="hideCancel" value="true"/>
		<c:forEach items="${element.reservations}" var="item" varStatus="loopStatus">
				<tr>
					<td title="${reservationDescriptions[item.state]}">
					   ${reservationStates[item.state]} (${item.state})
					</td>
					<td>${item.startTime.time}</td>
					<td>${item.endTime.time}</td>
					<td>${item.startPort.friendlyName}</td>
					<td> VLAN </td>
					<td>${item.startPort.vlan} </td>
					<td>${item.endPort.friendlyName}</td>
					<td> VLAN </td>
					<td>${item.endPort.vlan} </td>
					<td>${item.capacity/1000000}</td>
					<td>${item.mtu}</td>
				</tr>
                <c:if test="${item.state == 23}">
                    <tr><td colspan="11">Failure reason: ${item.failureCause}</td></tr>
                </c:if>
                <c:if test="${item.state == 9 || item.state == 10 || item.state == 4 || item.state == 5}">
                    <c:set var="hideCancel" value="false"/>
                </c:if>
		</c:forEach>
	</table>

    <c:if test="${hideCancel != true}">
<!--div style="position:relative;float:left;padding-top:20px"><a style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;" href="${flowExecutionUrl}&_eventId=cancel&id=${element.bodID}"><input id="cancel" name="Cancel" value="Cancel" type="submit" style="width:100px;" onclick="window.top.location='${flowExecutionUrl}&_eventId=cancel&id=${element.bodID}'" /></a-->
<div class="cancelButton" style="position:relative;float:left;padding-top:20px"><span style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;"><input id="cancel" name="Cancel" value="Cancel" type="submit" style="width:100px;" onclick="jQuery.post(location.href + '&_eventId=cancel&id=${element.bodID}&currentIdm2=' + $('#currentIdm').val() )" /></span>

<!--				<a style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;"  href="<c:url value="/portal/secure/services-map.htm"/>?service=${element.bodID}&domain=${element.user.homeDomain.bodID}">-->
<!--<input id="view" name="view" value="View map" type="submit" style="width:100px" onclick="window.top.location='<c:url value="/portal/secure/services-map.htm"/>?service=${element.bodID}&domain=${element.user.homeDomain.bodID}'" /></a>-->
	</div>
    </c:if>
	<br><br><br>
 	</div>				
   								
   								
   </c:forEach>
   		</div>
   				
   	</c:when>
   				
   	<c:otherwise>
   				
   	<c:if test="${x.count %2 == 0}">
   				
   	<div class="item" style="height:570px; overflow: auto;">
   			<c:forEach items="${services.services}" var="element" begin="${k}" end="${end-1}" varStatus="z">
   	<h4 align="right" style="padding-bottom:10px;"><b> Service: ${element.bodID}</b></h4>
<div id="coolection_service">
	<table width="300px">
		<tr>
			<td class=""><spring:message code="service.user.homeDoamin" text="Home Domain" /></td>
			<td class="" style="margin-left: 10px;">${element.user.homeDomain.name}</td>
		</tr>
		<tr>
			<td class=""><spring:message code="service.user.name" text="User" /></td>
			<td class="" style="margin-left: 10px;">${element.user.name} ${element.user.email}</td>
		</tr>
		<tr>
			<td class=""><spring:message code="service.jastification" text="Justification" /></td>
			<td class="" style="margin-left: 10px;">${element.justification}</td>
		</tr>

	</table>
	</div>
	<div id="collection">
	<table width="100%">
		<tr>
			<th><spring:message code="reservation.state" text="State"/></th>
			<th><spring:message code="reservation.startTime" text="Start time"/></th>
			<th><spring:message code="reservation.endTime" text="End time"/></th>
			<th><spring:message code="reservation.startPort" text="Start port"/></th>
			<th><spring:message code="reservation.startMode" text="Start mode"/></th>
			<th><spring:message code="reservation.startVlan" text="Start vlan"/></th>
			<th><spring:message code="reservation.endPort" text="End port"/></th>
			<th><spring:message code="reservation.endMode" text="End mode"/></th>
			<th><spring:message code="reservation.endVlan" text="End vlan"/></th>
			<th><spring:message code="reservation.capacity" text="Capacity"/></th>
			<th><spring:message code="reservation.mtu" text="Mtu"/></th>
		</tr>
        <c:set var="hideCancel" value="true"/>
		<c:forEach items="${element.reservations}" var="item" varStatus="loopStatus">
				<tr>
					<td title="${reservationDescriptions[item.state]}">
					   ${reservationStates[item.state]} (${item.state})
					</td>
					<td>${item.startTime.time}</td>
					<td>${item.endTime.time}</td>
					<td>${item.startPort.friendlyName}</td>
					<td> VLAN </td>
					<td>${item.startPort.vlan} </td>
					<td>${item.endPort.friendlyName}</td>
					<td> VLAN </td>
					<td>${item.endPort.vlan} </td>
					<td>${item.capacity/1000000}</td>
					<td>${item.mtu}</td>
				</tr>
                <c:if test="${item.state == 23}">
                    <tr><td colspan="11">Failure reason: ${item.failureCause}</td></tr>
                </c:if>
                <c:if test="${item.state == 9 || item.state == 10 || item.state == 4 || item.state == 5}">
                    <c:set var="hideCancel" value="false"/>
                </c:if>
		</c:forEach>
	</table>

    <c:if test="${hideCancel != true}">
    <div class="cancelButton" style="position:relative;float:left;padding-top:20px"><span style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;"><input id="cancel" name="Cancel" value="Cancel" type="submit" style="width:100px;" onclick="jQuery.post(location.href + '&_eventId=cancel&id=${element.bodID}&currentIdm2=' + $('#currentIdm').val() )" /></span>
<!--	<a style="text-decoration:none;padding:0px;color:#000000;background:#ffffff;border:none;"  href="<c:url value="/portal/secure/services-map.htm"/>?service=${element.bodID}&domain=${element.user.homeDomain.bodID}">-->
<!--<input id="view" name="view" value="View map" type="submit" style="width:100px" onclick="window.top.location='<c:url value="/portal/secure/services-map.htm"/>?service=${element.bodID}&domain=${element.user.homeDomain.bodID}'" /></a>-->
    </div>
    </c:if>
	<br><br><br>
 	</div>			
   			</c:forEach>
   	</div>
   					
   		<c:set var="k" value="${x.count}"></c:set>
   		<c:set var="end" value="${x.count + 2}"></c:set>
   		<c:set var="counter" value="${x.count +1}"></c:set>		
   </c:if>
   				
   						
   				
   	</c:otherwise>
   			
   </c:choose>
   </c:otherwise>
   			
  </c:choose>
  		
  		
  </c:forEach>
   	</div>
  </div>	
<script>

jQuery(document).ready(function() {	 
	 $(function() {		
		
			$(".scrollable").scrollable({ 
				vertical: true,
				mousewheel: false,
				
				onSeek: function()  {  

				      //index = this.getIndex();
				      //alert("koniec =" + index);
				     // alert("input =" + document.getElementById("index").value);
				    } 	
			
			}).navigator();
			
			var api = $(".scrollable").data("scrollable");
			//api.seekTo(document.getElementById("index").getAttribute("value"));
			
		}); 	 
});
</script>



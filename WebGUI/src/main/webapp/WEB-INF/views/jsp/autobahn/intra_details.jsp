<%@ include file="../common/includes.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>
<script type="text/javascript" src="<c:url value="/scripts/jscalendar/js/jscal2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/jscalendar/js/lang/en.js"/>"></script>
<script src="http://cdn.jquerytools.org/1.2.3/full/jquery.tools.min.js"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.validate.min.js"/>"></script>
<script type="text/javascript">


String.prototype.startsWith = function(prefix) {
    return (this.substr(0, prefix.length) === prefix);
}

 
jQuery(document).ready(function() 
{   

    logs=$("#logs_hiden").html();	

    $("#logs_div").html("");
    var lines=logs.split("\n");    

    lines.each(function(index) { 			    
	if(index.startsWith("[ERROR]") )
	{
	    $('#logs_div').append("<font color=\"red\">"+index+"</font>");
	}
	else
	{
	    $('#logs_div').append(index);
	    $('#logs_div').append("</br>");
	}
    } );
              
    var wizard = $("#wizard");

    
    $("ul.tabs", wizard).tabs("div.panes > div", function(event, index) {   
	vv = $("#intra_detailsform").valid();

	if (!vv)
	{
	    return false;
	}
    });
});


</script>

<form:form commandName="intra_details" id="intra_detailsform">
<div id="form">

<h2><spring:message code="intra_details.htitle" text="Intradomain Reservation details"/></h2>

<div id="wizard">

    <!-- tabs -->

    <ul class="tabs">
        <li><a href="#" class="w2">Intra-domain view</a></li>
        <li><a href="#" class="w2">Logs</a></li>
        <li><a href="#" class="w2">Monitoring data</a></li>
    </ul>
    <div class="panes">

        <div id="collection">
            
      <table width="100%" class="class_intras">
        <tr>
            <td class="label" style="width: 30%;">Reservation ID: </td>
            <td class="value" style="width: 70%;">${interRes.bodID}</td>
        </tr>
        <tr>
            <td class="label" style="width: 30%;">State: </td>
	    
	    <c:if test="${interRes.state == 0 || interRes.state == null}">
            	<td class="value" style="width: 70%;">UNKNOWN</td>
            </c:if>
            
            <c:if test="${interRes.state == 5}">
            	<td class="value" style="width: 70%;">SCHEDULED</td>
            </c:if>
            
            <c:if test="${interRes.state == 10}">
            	<td class="value" style="width: 70%;">ACTIVE</td>
            </c:if>
            
            <c:if test="${interRes.state == 21}">
            	<td class="value" style="width: 70%;">FINISHED</td>
            </c:if>
            
            <c:if test="${interRes.state == 22}">
            	<td class="value" style="width: 70%;">CANCELLED</td>
            </c:if>
            
            <c:if test="${interRes.state == 23}">
            	<td class="value" style="width: 70%;">FAILED</td>
            </c:if>

	</tr>
        <tr>
            <td class="label" style="width: 30%;">Start time: </td>
            <td class="value" style="width: 70%;"> <fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${interRes.startTime.time}" /></td>
        </tr>
        <tr>
            <td class="label" style="width: 30%;">End time: </td>
            <td class="value" style="width: 70%;"> <fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${interRes.endTime.time}" /></td>
           
        </tr>
        <tr>
            <td class="label" style="width: 30%;">Capacity (Mbit/s): </td>
            <td class="value" style="width: 70%;">${interRes.capacity/1000000}</td>
        </tr>
        <tr>
            <td class="label" style="width: 30%;">Domains: </td>
            <td class="value" style="width: 70%;">
                <c:forEach items="${interRes.path.domains}" var="item">
                     <span>${item}</span>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td class="label" style="width: 30%;">User: </td>
            <td class="value" style="width: 70%;">${intraRes.params.authParameters.identifier}</td>
        </tr>
    </table>
      
     <br/>       
     <table width="100%" class="class_intras">
                <tr>
                    <td class="label" style="width: 30%;">Ingress interface: </td>
                    <c:choose>
                    	<c:when test="${intraRes.reservedPath.firstLink == null}">
                    		<td class="value" style="width: 70%;">No data</td>
                    	</c:when>
                    	<c:otherwise>
                    		<td class="value" style="width: 70%;">${intraRes.reservedPath.firstLink}</td>
                    	</c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <td class="label" style="width: 30%;">Egress interface: </td>
                    <c:choose>
                    	<c:when test="${intraRes.reservedPath.lastLink == null}">
                    		<td class="value" style="width: 70%;">No data</td>
                    	</c:when>
                    	<c:otherwise>
                    		 <td class="value" style="width: 70%;">${intraRes.reservedPath.lastLink}</td>
                    	</c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <td class="label" style="width: 30%;">Ingress VLAN: </td>
                    <c:choose>
                    	<c:when test="${intraRes.params.pathConstraintsIngress.firstVlan == null}">
                    		<td class="value" style="width: 70%;">No data</td>
                    	</c:when>
                    	<c:otherwise>
                    		 <td class="value" style="width: 70%;">${intraRes.params.pathConstraintsIngress.firstVlan}</td>
                    	</c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <td class="label" style="width: 30%;">Egress VLAN: </td>
                    <c:choose>
                    	<c:when test="${intraRes.params.pathConstraintsEgress.firstVlan == null}">
                    		<td class="value" style="width: 70%;">No data</td>
                    	</c:when>
                    	<c:otherwise>
                    		<td class="value" style="width: 70%;">${intraRes.params.pathConstraintsEgress.firstVlan}</td>
                    	</c:otherwise>
                    </c:choose>
                    
                </tr>
                <tr>
                    <td class="label" style="width: 30%;">Path: </td>
                    <c:choose>
                    	<c:when test="${intraRes.reservedPath == null}">
                    		<td class="value" style="width: 70%;">No data</td>
                    	</c:when>
                    	<c:otherwise>
                    		<td class="value" style="width: 70%;">
                    		
                    		<table style="width: 100%">
                    		
                    		<c:forEach items="${intraRes.reservedPath.orderedInterfaces}" var="item">
                    			<tr>
                    				<td style="width: 50%">${item.startInterface.name}@${item.startInterface.node.name}</td>
                    				<td>${item.endInterface.name}@${item.endInterface.node.name}</td>
                    			</tr>
                			</c:forEach>
                    		</table>
                    		
                    		</td>
                    	</c:otherwise>
                    </c:choose>               
                </tr>
        </table>
        </div>
	<div id="logs_div" >loading</div>
	<div></div>
    </div>
</div>

</div>
</form:form>
<div id="logs_hiden" style="display: none" >${logs}</div>


<style type="text/css">

#logs_div
{
    text-align: left;
    height:500px;
    overflow:scroll;
    overflow-x: hidden;
    padding: 10px; 
    font-size:11px;
}

</style>
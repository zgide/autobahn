<%@ include file="../common/includes.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- tab styling -->
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>
<script type="text/javascript" src="<c:url value="/scripts/jscalendar/js/jscal2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/jscalendar/js/lang/en.js"/>"></script>
<script src="http://cdn.jquerytools.org/1.2.3/full/jquery.tools.min.js"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.validate.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/date.js"/>"></script>

<script language="javascript">

function passPort(){
    setStartFriendlyName(document.getElementById('request.startPort.address').options[document.getElementById('request.startPort.address').options.selectedIndex].text);
    var now = new Date();

    var startTime = document.getElementById('startTime').value;
    var endTime = document.getElementById('endTime').value;
    
    if (isNaN(startTime.charAt(0)) == true) {
	    document.getElementById('startTime').value = now.addMinutes(10).toString("yyyy-MM-dd HH:mm:ss");
    	document.getElementById('endTime').value = now.addMinutes(60).toString("yyyy-MM-dd HH:mm:ss");    	
    } else {
	    document.getElementById('startTime').value = startTime.replace("T"," ");
    	document.getElementById('endTime').value = endTime.replace("T"," ");    	
    }
    
}

function alerta(){
    var splittedString = document.getElementById('startTime').value;
    var splittar=splittedString.split(" ");
    document.getElementById('startTime').value = splittar[0]+"T"+splittar[1];
    var splittedString2 = document.getElementById('endTime').value;
    var splittar2=splittedString2.split(" ");
    document.getElementById('endTime').value = splittar2[0]+"T"+splittar2[1];

    var includeLinks = document.getElementById('includeLinks').value;
    var excludeLinks = document.getElementById('excludeLinks').value;

    if(includeLinks.length != 0) {
        var replacedIncludeLinks = includeLinks.substring(includeLinks.indexOf('[')+1,includeLinks.indexOf(']'));
        document.getElementById('includeLinks').value = replacedIncludeLinks;
    }
    if(excludeLinks.length != 0) {
        var replacedExcludeLinks = excludeLinks.substring(excludeLinks.indexOf('[')+1,excludeLinks.indexOf(']'));
        document.getElementById('excludeLinks').value = replacedExcludeLinks;
    }
}

function setStartFriendlyName(path){
    document.getElementById('request.startPortFriendlyName').value = path;
    startPortValue = $(document.getElementById('request.startPort.address')).val();
    var endPortSelect = document.getElementById('request.endPort.address');
	var startPortSelect = document.getElementById('request.startPort.address');
    for (var i=0; i < startPortSelect.length; i++) {
        $(startPortSelect.options[i]).removeAttr("disabled");
    }

    for (var i=0; i < endPortSelect.length; i++) {
        if (endPortSelect.options[i].value != "IDCP") { 
            $(endPortSelect.options[i]).removeAttr("disabled");
        }
        if (endPortSelect.options[i].value == startPortValue) {
            $(endPortSelect.options[i]).attr("disabled", "true");
            if (i == 0){
                endPortSelect.selectedIndex= i+1;
            }
            else {
                endPortSelect.selectedIndex = i-1;
	     }
         
        }
        document.getElementById('request.endPortFriendlyName').value = document.getElementById('request.endPort.address').options[endPortSelect.selectedIndex].text;
    }
    
}

function setEndFriendlyName(path){
document.getElementById('request.endPortFriendlyName').value = path;
}

function checkMinus(field,id){

    var testing = document.forms[0][id];
    var val = testing.value;
    var final = testing.value;

    var count = "un"; 
    for (var i=0;i<val.length;i++){
        if ((!isDigit(val.charAt(i))) && (val.charAt(i)!=".")) {
            final = val.replace(/[^0-9.]/gi,'');
            if(count!="lo") {
                alert("Positive " + field + " value is only accepted. We assume " + field + " is: " + final);
                count="lo";
            }
        }
    }
    if (final=='' || final==undefined) {
        final = 0;
        alert("Positive " + field + " value is only accepted. We assume " + field + " is: " + final);
        document.forms[0][id].value= final;
    } else {
        document.forms[0][id].value= final;
    }
}

function isDigit(num) {
    if (num.length>1) {
        return false;
    }
    var string="1234567890";
    if (string.indexOf(num)!=-1) {
        return true;
    }
    return false;
}

function checkMinusCapacityVlan(field,id){
    var testing = document.forms[0][id];
    var val = testing.value;
    var final = testing.value;

    if(document.forms[0][id].value==0 && field=="capacity"){
        alert("Capacity cannot be zero. We assume " + field + " is: " + 1000);
        final = 1000;
        document.forms[0][id].value= final;
    }

    var count = "un"; 
    for (var i=0;i<val.length;i++) {
        if(!isDigit(val.charAt(i))){
            final = val.replace(/[^0-9]/gi,'');
            if (count!="lo") {
                alert("Positive " + field + " value is only accepted. We assume " + field + " is: " + final);
                count="lo";
            }
        }
    }
    if ((final=='' || final==undefined) && field=="capacity") {
        final = 1000;
        alert("Positive " + field + " value is only accepted. We assume " + field + " is: " + final);		
        document.forms[0][id].value= final;
    } else if(final=='' || final==undefined && field!="capacity") {
        final = 0;
        alert("Positive " + field + " value is only accepted. We assume " + field + " is: " + final);		
        document.forms[0][id].value= final;
    } else {
        document.forms[0][id].value= final;
    }
}

//id must be unique
function checkIfVlanSelected1(selected){

    if (selected == "VLAN") {
        document.getElementById('hide1').style.display="";
        document.getElementById('hide2').style.display="";
    } else {
        document.getElementById('hide1').style.display='none';
        document.getElementById('hide2').style.display='none';
    }
}

//id must be unique
function checkIfVlanSelected2(selected){

    if(selected == "VLAN"){
        document.getElementById('hide3').style.display="";
        document.getElementById('hide4').style.display="";
    } else {
        document.getElementById('hide3').style.display="none";
        document.getElementById('hide4').style.display="none";
    }
}

function blockInputStartTime(checked) {
    if (checked) {
        document.getElementById('startTime').disabled = true;
        document.getElementById("startTime").className = "disableStartTimeCss";
    } else {
        document.getElementById('startTime').disabled = false;
        document.getElementById("startTime").className = "enableStartTimeCss";	
    }
}
</script>

<body onload="passPort()">
<form:form commandName="reservation" id="reservationform" onsubmit="alerta()">
<div id="form">

<h2><spring:message code="reservation.htitle" text="Reservation form"/></h2>

<c:if test="${friendlyports_domain != null && fn:length(friendlyports_domain) > 0 && friendlyports_destinationDomain != null && fn:length(friendlyports_destinationDomain) > 0}">
<div id="wizard">

<!-- tabs -->

<ul class="tabs">
    <li><a href="#" class="w2">Basic parameters </a></li>
    <li><a href="#" class="w2">Optional parameters</a></li>
    <li><a href="#" class="w2">Path constraints</a></li>
</ul>
<div class="panes">

<div>
    <table>
        <tr>
            <td class="label">
                <spring:message code="reservation.startPort"/>
                <br/>
                <span class="error">
                    <form:errors path="request.startPort.address"/>
                </span>
            </td>
            <td class="value">
                <form:select path="request.startPort.address" onchange="setStartFriendlyName(this.options[this.options.selectedIndex].text)">
                    <form:options items="${friendlyports_domain}" itemValue="address" itemLabel="friendlyName"/>	
                </form:select>
            </td>
            <form:hidden path="request.startPortFriendlyName" />
            
        </tr>

        <tr>
            <td>&nbsp;</td>
            <td>
                <table>
                    <tr>
                        <td class="label" style="width: 20px;">
                            <spring:message code="reservation.mode"/>
                        </td>
                        <td class="value">
                            <form:select path="request.startPort.mode" id="mode-start" onchange="checkIfVlanSelected1(this.options[this.options.selectedIndex].text)">
                                <form:options items="${modes}"/>
                            </form:select>
                        </td>
                        <td class="label" style="width: 50px;" id="hide1">
                            <spring:message code="reservation.vlan"/>
			                <img src="<c:url value="/images/help_icon.png"/>" 
			                    alt="Select the desired start port VLAN. Leave 0 if you want the VLAN to be automatically selected" 
			                    title="Select the desired start port VLAN. Leave 0 if you want the VLAN to be automatically selected" />
                        </td>
                        <td id="hide2" >
                            <form:input path="request.startPort.vlan" maxlength="4" cssStyle="width:30px; height:20px; margin-right:0px;"/> 
                        </td>
                        <td class="error"><form:errors path="request.startPort.vlan"/></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr><td>&nbsp;</td></tr>

        <tr>
            <td class="label">
                <spring:message code="reservation.endPort"/>
                <br/>
                <span class="error">
                    <form:errors path="request.endPort.address"/>
                </span>
            </td>
            <td class="value">
                <form:select path="request.endPort.address" onchange="setEndFriendlyName(this.options[this.options.selectedIndex].text)">
                    <form:options items="${friendlyports_destinationDomain}" itemValue="address" itemLabel="friendlyName"/>
                </form:select>
            </td>
            <form:hidden path="request.endPortFriendlyName" />
        </tr>

        <tr>
            <td>&nbsp;</td>
            <td>
                <table>
                    <tr>
                        <td class="label" style="width: 20px;">
                            <spring:message code="reservation.mode"/>
                        </td>
                        <td class="value">	
                            <form:select path="request.endPort.mode" id="mode-end" onchange="checkIfVlanSelected2(this.options[this.options.selectedIndex].text)" >
                                <form:options items="${modes}"/>
                            </form:select>
                        </td>
                        <td class="label" style="width: 50px;" id="hide3">
                            <spring:message code="reservation.vlan"/>
                            <img src="<c:url value="/images/help_icon.png"/>" 
                                alt="Select the desired end port VLAN. Leave 0 if you want the VLAN to be automatically selected" 
                                title="Select the desired end port VLAN. Leave 0 if you want the VLAN to be automatically selected" />
                        </td>
                        <td id="hide4">
                            <form:input path="request.endPort.vlan" maxlength="4" cssStyle="width:30px; height:20px; margin-right:0px;"/> 
                        </td>
                        <td class="error"><form:errors path="request.endPort.vlan"/></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr><td>&nbsp;</td></tr>

        <tr>
            <td class="label">
                <spring:message code="reservation.timezone"/>
                <img src="<c:url value="/images/help_icon.png"/>" 
                    alt="Select the timezone for the start / end times below" 
                    title="Select the timezone for the start / end times below" />
            </td>
            <td class="value">
                <form:select path="timezone">
                    <form:options items="${timezones}"/>
                </form:select>
            </td>
            <td class="error"><form:errors path="timezone"/></td>
        </tr>

        <tr>
            <td class="label" style="min-width:150px"><spring:message code="reservation.startTime"/><br /><span class="error"><form:errors path="request.startTime"/></span></td>
            <td class="value">
                <table style="margin:0px;border:0px; text-align:left">
                    <tr>
                        <td id="holder">&nbsp;</td>
                        <td>
                            <form:input path="request.startTime" id="startTime" cssStyle="width:150px;margin-right:0px;" cssClass="enableStartTimeCss"/> 
                        </td>
                        <td class="label" style="width:60px;">
                            <spring:message code="reservation.processNow"/>
                        </td>
                        <td class="value" style="width:50px;"> 
                            <form:checkbox cssClass="check" path="request.processNow" id="processNow" onchange="blockInputStartTime(this.checked)"/>
                        </td>
                        <td class="error">
                            <form:errors path="request.processNow"/>
                        </td>
                    </tr>
                </table>
            </td>
            <td class="error"></td>
        </tr>

        <tr>
            <td class="label" style="min-width:150px"><spring:message code="reservation.endTime"/><br /><span class="error"><form:errors path="request.endTime"/></span></td>
            <td class="value">
                <table style="margin:0px;border:0px; text-align:left">
                    <tr>
                        <td id="holder">&nbsp;</td>
                        <td>
                            <form:input path="request.endTime" id="endTime" cssStyle="width:150px;margin-right:0px;"/> 
                        </td>
                    </tr>
                </table>
            </td>
            <td class="error"></td>
        </tr>

        <tr>
            <td class="label"><spring:message code="reservation.capacity"/></td>
            <td class="value">
                <form:input path="request.capacity" id="rcapacity"
                 onblur="checkMinusCapacityVlan('capacity','request.capacity')"/>
            </td>
            <td class="error"><form:errors path="request.capacity"/></td>
        </tr>

        <tr>
            <td class="label" style="min-width:150px">
                <spring:message code="reservation.description"/>
                <img src="<c:url value="/images/help_icon.png"/>" 
                    alt="Free text description of the specific circuit" 
                    title="Free text description of the specific circuit" />
                <br/>
                <span class="error">
                    <form:errors path="request.description"/>
                </span>
            </td>
            <td class="value">
                <form:textarea rows="4" cols="45" path="request.description" id="rdescription"/>
            </td>
            <td class="error"></td>
        </tr>
    </table>
    <br>

    <table class="pos">
        <tr>
           <td>
                <input type="submit" name="_eventId_submit" onmouseover="this.style='background-color: #FFF;'"  
                 style="height: 25px; width: 220px" value="<spring:message code="reservation.add"/>"/>
           </td>
           <td>
                <input type="submit" name="_eventId_test"
                	style="height: 25px; width: 220px"  value="<spring:message code="reservation.test"/>"/>
            </td>
            <td>
                <input type="submit" name="_eventId_cancel" 
                	style="height: 25px; width: 220px"    value="<spring:message code="reservation.cancel"/>"/>
            </td>
         </tr>
    </table>

</div>
<div>
    <table>
        <tr>
            <td class="label" style="min-width:150px"><spring:message code="reservation.maxDelay"/></td>
            <td class="value">
                <form:input path="request.maxDelay" id="rdelay"/>
            </td>
            <td class="error"><form:errors path="request.maxDelay" id="rdelay"/></td>
        </tr>
        <tr>
            <td class="label" style="min-width:150px"><spring:message code="reservation.mtu"/></td>
            <td class="value">
                <form:input path="request.mtu" onblur="checkMinusCapacityVlan('Mtu size','request.mtu')" />
            </td>
            <td class="error"><form:errors path="request.mtu"/></td>
        </tr>
    </table>

    <table class="pos">
        <tr>
           <td>
                <input type="submit" name="_eventId_submit" 
                 style="height: 25px; width: 220px"   value="<spring:message code="reservation.add"/>"/>
           </td>
           <td>
                <input type="submit" name="_eventId_test" 
                	style="height: 25px; width: 220px"  value="<spring:message code="reservation.test"/>"/>
            </td>
            <td>
                <input type="submit" name="_eventId_cancel"  
                	style="height: 25px; width: 220px"    value="<spring:message code="reservation.cancel"/>"/>
            </td>
         </tr>
    </table>

</div>

<div>
    <table>
        <tr>
            <td class="label" valign="top">
                <spring:message code="reservation.userInclude"/>
                 <img src="<c:url value="/images/help_icon.png"/>" 
                     alt="Select the domains and/or links that you want to force the pathfinder to include in the path. Paths without these domains and/or links will be ignored" 
                     title="Select the domains and/or links that you want to force the pathfinder to include in the path. Paths without these domains and/or links will be ignored" />
            </td>
            <td>
                <table>
                    <tr>
                        <td class="label">
                            <spring:message code="reservation.userIncludeDomains"/>
                        </td>
                        <td class="label">
                            <spring:message code="reservation.userIncludeLinks"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="value">
                            <form:select size="3" path="request.userInclude.domains" >
                                <form:options items="${domains_all}"/>
                            </form:select>
                        </td>
                        <td class="value">
                            <form:select id="includeLinks" size="3" path="request.userInclude.links" >
                                <form:options items="${links_all}" itemValue="identifier" itemLabel="friendlyName"/>
                            </form:select>
                        </td>
                    </tr>
                </table>
            </td>
            <td class="error">
                <form:errors path="request.userInclude.domains"/><br>
                <form:errors path="request.userInclude.links"/>
            </td>
        </tr>
        <tr>
            <td class="label" valign="top">
                <spring:message code="reservation.userExclude"/>
                 <img src="<c:url value="/images/help_icon.png"/>" 
                     alt="Select the domains and/or links that you want to force the pathfinder to exclude from the path. Paths with these domains and/or links will be ignored" 
                     title="Select the domains and/or links that you want to force the pathfinder to exclude from the path. Paths with these domains and/or links will be ignored" />
            </td>
            <td>
                <table>
                    <tr>
                        <td class="label">
                            <spring:message code="reservation.userExcludeDomains"/>
                        </td>
                        <td class="label">
                            <spring:message code="reservation.userExcludeLinks"/>
                        </td>
                        
                    </tr>
                    <tr>
                        <td class="value">
                            <form:select size="3" path="request.userExclude.domains" >
                                <form:options items="${domains_all}"/>
                            </form:select>
                        </td>	
                        <td class="value">
                            <form:select id="excludeLinks" size="3" path="request.userExclude.links" >
                                <form:options items="${links_all}" itemValue="identifier" itemLabel="friendlyName"/>
                            </form:select>
                        </td>

                    </tr>
                </table>
            </td>
            <td class="error">
                <form:errors path="request.userExclude.domains"/><br>
                <form:errors path="request.userExclude.links"/>
            </td>
        </tr>

    </table>

    <table class="pos">
        <tr>
           <td>
                <input type="submit" name="_eventId_submit" 
                 style="height: 25px; width: 220px"   value="<spring:message code="reservation.add"/>"/>
           </td>
           <td>
                <input type="submit" name="_eventId_test" 
                	style="height: 25px; width: 220px"  value="<spring:message code="reservation.test"/>"/>
            </td>
            <td>
                <input type="submit" name="_eventId_cancel"  
                	style="height: 25px; width: 220px"    value="<spring:message code="reservation.cancel"/>"/>
            </td>
         </tr>   
    </table>

</div>

    <c:if test="${test!= null}">
        <c:if test="${test.status==true}">
            <h3 style="color:green">Reservation test succeeded</h3>
        </c:if> 
        <c:if test="${test.status==false}">
            <h3 style="color:red">Reservation test failed</h3>
        </c:if>
    </c:if>

</div>
</div>

</c:if>
<c:if test="${friendlyports_domain == null || friendlyports_destinationDomain == null}">
    Cannot retrieve ports. Cannot connect to ${home} IDM.
</c:if>
<c:if test="${fn:length(friendlyports_domain) <= 0}">
    The Home domain (${home}) has no clients attached.
</c:if>
<c:if test="${fn:length(friendlyports_destinationDomain) <= 0}">
    The Destination domain (${dest}) has no clients attached.
</c:if>
</div>


<spring:hasBindErrors name="reservation">
<script>

if(document.getElementById('processNow').checked){
    document.getElementById('startTime').disabled = true;
    document.getElementById("startTime").className = "disableStartTimeCss";
}

var x = document.getElementById('mode-start');
var start_mode = x.options[x.options.selectedIndex].text;

if(start_mode != "VLAN"){
    document.getElementById('hide1').style.display='none';
    document.getElementById('hide2').style.display='none';
}

var y = document.getElementById('mode-end');
var end_mode = y.options[y.options.selectedIndex].text;

if(end_mode != "VLAN"){
    document.getElementById('hide3').style.display='none';
    document.getElementById('hide4').style.display='none';
}

</script>
</spring:hasBindErrors>

</form:form>

<script>
jQuery(document).ready(function() {
    $(function() {
        blockInputStartTime(document.getElementById('processNow').checked);
        var wizard = $("#wizard");
        $("ul.tabs", wizard).tabs("div.panes > div", function(event, index) {
            vv = $("#reservationform").valid();
<%--  blockInputStartTime(getElementById.processNow.checked);  --%>
            if (!vv) {
                return false;
            }
        });
    });
});
</script>
<script src="<c:url value="/js/jquery/datetimepicker/jquery_ui_datepicker/jquery_ui_datepicker.js"/>" type="text/javascript"></script>

<script type="text/javascript">
/* <![CDATA[ */
    $(function() {
        $('#startTime').datetime({
            userLang	: 'en',
            americanMode: false
        });
        $('#endTime').datetime({
            userLang	: 'en',
            americanMode: false
        });
    });
/* ]]> */
</script>

<script src="<c:url value="/js/jquery/datetimepicker/jquery_ui_datepicker/i18n/ui.datepicker-de.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/jquery/datetimepicker/jquery_ui_datepicker/timepicker_plug/timepicker.js"/>" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/datetimepicker/jquery_ui_datepicker/timepicker_plug/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/datetimepicker/jquery_ui_datepicker/smothness/jquery_ui_datepicker.css"/>">

</body>
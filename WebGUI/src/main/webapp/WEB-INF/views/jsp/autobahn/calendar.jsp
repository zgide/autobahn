<%@ include file="../common/includes.jsp"%>

<br/>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/jquery.autocomplete.css"/>" />

<script type="text/javascript" src="<c:url value="/js/jquery/jquery.json-1.3.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.bgiframe.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.quicksearch.js"/>"></script>
<script type="text/javascript">
$("#ajaxerror").ajaxError(function() {
  $(this).text('An error occured..please try again.');
});

$.fn.serializeObject = function()
{
    var o = {};
    var a = $(":input").serializeArray();
    
    jQuery.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || "");
        } else {
            o[this.name] = this.value || "";
        }
    });
    return o;
};

</script>

<div align="center">
	<div id="ajaxerror" style="color:red">
	</div>
	
	<div id="ajaxsuccess" style="color:green">
	</div>
	
	<form:form id="calendarform" action="#" >
	
        <c:if test="${calendar != null}" >
            <br/><br/>
    
            <div id="collection">
                <table width="600px" id="calendarview">
                    <tbody>
                        <tr><th class="value">Resources Utilization Calendar</th></tr>
                        <tr><td class="value">${calendar}</td></tr>
                    </tbody>
                </table>
            </div>
        </c:if>
        
        <c:if test="${calendar == null}" >
            Cannot retrieve calendar information. Cannot connect to IDM.
        </c:if>
    
	</form:form>
</div>

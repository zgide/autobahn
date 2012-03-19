<%@ include file="../common/includes.jsp"%>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.form.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>

<h2><spring:message code="calendar.htitle" text="Resources Utilization Calendar" /></h2>

 <!-- generally javascript should go in a separate file -->
    <script type="text/javascript">

var options = {
    target:     "#calendarform",
    url:        "calendar.htm",
    success:    function(data) {
    }
};

	   jQuery(document).ready(function() {
            // bind 'myForm' and provide a simple callback function

            $("#calendarsearchform").ajaxForm(options);

             $("#currentIdm").change(function(){
              $("#calendarsearchform").submit();
             });
            $("#calendarsearchform").submit();
        });



	</script>

<div align="center" class="logs_image">
<form:form commandName="calendar" action="" id="calendarsearchform">
<table>
	<tr><td><h3>Please select an IDM to view reservation info</h3></td></tr>
	<tr>
	<td>
		<center><form:select path="currentIdm">
			<form:options items="${calendar.idms}"/>
		</form:select></center>
	</td>
	<td style="display:none">
		<input type="submit"  value="Change IDM" style="width:100px"/>
	</td>
	</tr>
</table>
</form:form>

<hr/>
 <div id="calendarform" style="text-align:center" class="emptydiv">
 </div>
 </div>
<%@ include file="../common/includes.jsp"%>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.min.js"/>"></script>
<script src="http://cdn.jquerytools.org/1.2.3/full/jquery.tools.min.js"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.form.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>

<h2><spring:message code="intras.htitle" text="Intradomain Reservations" /></h2>

<!-- generally javascript should go in a separate file -->
<script type="text/javascript">

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

var options = {
    target:     "#settingsform",
    url:        "intras.htm",
    success:    function(data) {
    }
};

	   jQuery(document).ready(function() {
            // bind 'myForm' and provide a simple callback function

            $("#intrassearchform").ajaxForm(options);

             $("#currentIdm").change(function(){
             
            	 $("#intrassearchform").submit();
             });
            
             $("#intrassearchform").submit();
        });


	</script>

<div align="center" class="logs_image">
<form:form commandName="intras" action="" id="intrassearchform">
<table>
	<tr><td><h3>Please select an IDM to view reservation info</h3></td></tr>
	<tr>
	<td>
		<center><form:select path="currentIdm">
			<form:options items="${intras.idms}"/>
		</form:select></center>
	</td>
	<td style="display:none">
		<input type="submit"  value="Change IDM" style="width:100px"/>
	</td>
	</tr>
</table>
</form:form>

<hr/>
 <div id="settingsform" style="text-align:center" class="emptydiv">
 </div>
 </div>
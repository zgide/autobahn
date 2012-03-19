<%@ include file="../common/includes.jsp" %>


<h2><spring:message code="logs.htitle" text="Domains Logs"/></h2>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.json-1.3.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.dateFormat-1.0.js"/>"></script>
<script type="text/javascript">

String.prototype.startsWith = function(prefix) {
  return (this.substr(0, prefix.length) === prefix);
} 

var logs="";

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
 
function updateLogs()
{
     $("#logssearchform").submit();
}

var counter=true;
jQuery(document).ready(function() 
{
        // bind 'myForm' and provide a simple callback function
     $("#ajaxerror").ajaxError(function() 
     {
	  $(this).text('Cached data are being provided..please try again or wait for real time update.');
     });

     $("#logssearchform").submit(function() 
     {            
	  var aa = $.toJSON($("values").serializeObject());
          jQuery.getJSON("logs_request.htm", aa,
                    function(data) 
		    {			 
			 $("#ajaxsuccess").html("Showing logs for <font color='blue'>"+$("#currentIdm").val()+"</font><br/>"+(new Date()).format("r")+"<br>The contents will update every 5 seconds");
			 if(logs!=data.result)
			 {			
				logs=data.result;

				$("#logs_div").html("</br>");
				var lines=data.result.split("\n");

				lines.each(function(index) { 			    
				    if(index.startsWith("[ERROR]") )
				    {
					$('#logs_div').append("<div id='error'>"+index+"</div>");
				    }
				    else
				    {
					$('#logs_div').append(index);
					$('#logs_div').append("</br>");
				    }
				} );			     
			 } 

                    }, "json");
	  if(counter)
	  {
	       counter=false;
	  }
          return false;
     });     
   
     $("#currentIdm").change(function() 
     {
           $("#logssearchform").submit();
     });
     
     $("#logssearchform").submit();
     setInterval( "updateLogs()", 5000 );
});  


</script>





<div class="logs_image">    

    <form:form commandName="logs"  action="" id="logssearchform">
    <input type="hidden" name="action" value="change"/>
    <div>
        <h3>Please select an IDM to view the log file</h3>
        <form:select path="currentIdm" id="currentIdm">
                    <form:options items="${logs.idms}"/>
        </form:select>
    <c:if test="${logs.error!= null}">
        <h3>${logs.error}</h3>
    </c:if>
    <hr/>
    <div id="ajaxerror" style="color:red">
    </div>
    <div id="ajaxsuccess" style="color:green">
    </div>
    <div id="settingsform" style="text-align:center">
        <div id="logs_div">
	    loading    
	</div>
    </div>
    <div></br></div>    
   </form:form>
</div>


<style type="text/css">
#error
{
    color:red;
} 

#logs_div
{   
    text-align: left;
    height:500px;
    overflow:scroll;
    overflow-x: hidden;
    border-style:ridge;
    border-width:2px;
}

</style>

<script type="text/javascript">
    var myselect=document.getElementById("currentIdm")
    for (var i=0; i<myselect.options.length; i++){
        if (myselect.options[i].value=="${authParameters.organization}") {
            myselect.options[i].selected=true
            break
            }
        }
</script>

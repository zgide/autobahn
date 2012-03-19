<%@ include file="../common/includes.jsp"%>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.form.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>

<h2><spring:message code="dmacl.htitle" text="Domain Access Policy" /></h2>

<script type="text/javascript">

var flowExecutionUrl = '${flowExecutionUrl}';

var options = {
    target:     "#dmaclform",
    url:        "dmacl.htm",
    success:    function(data) {
    }
};

    jQuery(document).ready(function() {
    // bind 'myForm' and provide a simple callback function

        $("#dmaclsearchform").ajaxForm(options);

        $("#currentIdm").change(function(){
            $("#dmaclsearchform").submit();
        });

        $("#dmaclsearchform").submit();
    });
</script>

<div align="center" class="logs_image">
    <form:form commandName="dmacl" action="" id="dmaclsearchform">
    <table>
        <tr><td><h3>Please select a domain to view and edit its Access Policy</h3></td></tr>
        <tr>
            <td><center>
                <form:select path="currentIdm">
                            <form:options items="${dmacl.idms}"/>
                </form:select>
            </center></td>
            <td style="display:none">
                <input type="submit"  value="Change IDM" style="width:100px"/>
            </td>
        </tr>
    </table>
    </form:form>

    <hr/>
    
    <div id="dmaclform" style="text-align:center" class="emptydiv"></div>

</div>
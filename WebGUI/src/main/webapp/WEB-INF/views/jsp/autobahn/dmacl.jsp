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

$('input#id_search').quicksearch('table#dmaclview tbody tr');

</script>

<div align="center">
    <div id="ajaxerror" style="color:red">
    </div>

    <div id="ajaxsuccess" style="color:green">
    </div>

    <form:form id="dmaclform" action="#">

        <c:if test="${dmacp != null}" >
        <p>
        When authorizing a user, each rule is checked sequentially, until a match is found.<br/>
        In order to match a rule, all user attributes must equal all non-empty fields of the rule.
        </p>
            <div id="collection">
                <table width="600px" id="dmaclview">

                    <tr>
                        <th><spring:message code="dmaclview.projectRole" text="Role"/></th>
                        <th><spring:message code="dmaclview.email" text="E-mail"/></th>
                        <th><spring:message code="dmaclview.projectMembership" text="Project Membership"/></th>
                        <th><spring:message code="dmaclview.organization" text="Organization"/></th>
                        <th></th>
                    </tr>

                <c:forEach items="${dmacp.policyRules}" var="item" varStatus="loopStatus">
                    <tr>
                        <td>${item.authp.projectRole}</td>
                        <td>${item.authp.email}</td>
                        <td>${item.authp.projectMembership}</td>
                        <td>${item.authp.organization}</td>
                        <td>
                            <a href="javascript:window.location=flowExecutionUrl + '&_eventId=remove&role=${item.authp.projectRole}&email=${item.authp.email}&membership=${item.authp.projectMembership}&organization=${item.authp.organization}&selectedIdm=${selectedIdm}&currentIdm=${selectedIdm}'">Remove</a>
                        </td>
                    </tr>
                </c:forEach>

                <form:form commandName="dmaclAddForm" >
                    <input type="hidden" name="_eventId" value="add" />
                    <input type="hidden" name="selectedIdm" value="${selectedIdm}" />
                    <input type="hidden" name="currentIdm" value="${selectedIdm}" />
	                <tr>
                        <td>
                            <select name="role" style="width:auto">
                                <option value="ADMINISTRATOR">ADMINISTRATOR</option>
                                <option value="NETWORKADMIN">NETWORKADMIN</option>
                                <option value="USER">USER</option>
                            </select>
                        </td>
                        <td><input type="text" name="email" style="width:auto" size="10" /></td>
                        <td><input type="text" name="membership" style="width:auto" size="10" /></td>
                        <td><input type="text" name="organization" style="width:auto" size="10" /></td>
                        <td align="center"><input type="submit" value="Add Rule" style="width: auto" /></td>
                     </tr>
                </form:form>
                
                </table>
            </div>
        </c:if>

        <c:if test="${dmacp == null}" >
            Cannot retrieve Access Policy. Cannot connect to IDM.
        </c:if>

    </form:form>
</div>
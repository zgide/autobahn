<%@ include file="../common/includes.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" type="text/css"
    href="<c:url value="/js/jquery/tabs.css"/>" />
<h2><spring:message code="home.usersadmin" text="User Rights: ${userProfile.username}" /></h2>

<script type="text/javascript" src="<c:url value="/js/jquery/jquery.js"/>"></script>

<SCRIPT TYPE="text/javascript">

function init() 
{
    if(isBlank("${userProfile.portsAllow}") && !isBlank( "${userProfile.portsDeny}" ) )
    {
        var sel=document.getElementById("portType");
        sel.value="portsDeny";
        changedPort(sel);
    }

    if(isBlank("${userProfile.vlanAllow}") && !isBlank("${userProfile.vlanDeny}" ) )
    {
        var sel=document.getElementById("vlanType").value="vlanDeny";
        changedVlan();
    }
}

window.onload = init;

function isBlank(str) 
{
    return (!str || /^\s*$/.test(str));
} 

function changedPort(sel)
{
    if(document.getElementById("portType").options[0].selected) 
    {
        document.getElementById("portDivAllow").style.display = "block";
        document.getElementById("portDivDeny").style.display = "none";
    }
    else
    {
        document.getElementById("portDivAllow").style.display = "none";
        document.getElementById("portDivDeny").style.display = "block";        
    }
}

function changedVlan()
{
    if(document.getElementById("vlanType").options[0].selected) 
    {
        document.getElementById("vlanDivAllow").style.display = "block";
        document.getElementById("vlanDivDeny").style.display = "none";
    }
    else
    {
        document.getElementById("vlanDivAllow").style.display = "none";
        document.getElementById("vlanDivDeny").style.display = "block";        
    }
}

function add_port()
{
    var sel=document.getElementById("allPorts");
    <c:forEach var="c" items="${ports_all}">
        var newOpt = new Option("${c.friendlyName}" ,"${c.address}" );
        sel.add(newOpt,null);
    </c:forEach>
    $("#portList").slideToggle("fast");     
}

function ports_cancel()
{
    $("#portList").slideToggle("fast");
}

function ports_ok()
{
    var port_text;
    
    if(document.getElementById("portType").options[0].selected) 
    {
        port_text=document.getElementById("portTextAllow");
    }
    else
    {
        port_text=document.getElementById("portTextDeny");
    }


    var sel=document.getElementById("allPorts");
    var val=port_text.value;
    var i = 0;
    
    if(isBlank(val) )
    {
        for (; i < sel.options.length; i++)
        {
            if(sel.options[i].selected)
            {
                val=sel.options[i].value;
                break ;
            }            
        }
        i++;
    }
    
    
    for (; i < sel.options.length; i++)
    {
        if(sel.options[i].selected)
        {
            val=val+' , '+sel.options[i].value;
        }
    }
    port_text.value=val;
    $("#portList").slideToggle("fast");
}

function submit_editing()
{   
    if(document.getElementById("portType").options[0].selected) 
    {
<%--         document.getElementsByName("portsAllow")[0].value=port_text.value; --%>
        document.getElementsByName("portsDeny")[0].value="";
    }
    else
    {
        document.getElementsByName("portsAllow")[0].value="";
<%--          document.getElementsByName("portsDeny")[0].value=port_text.value;  --%>
    }

    if(document.getElementById("vlanType").options[0].selected) 
    {
<%--         document.getElementsByName("portsAllow")[0].value=port_text.value; --%>
        document.getElementsByName("vlanDeny")[0].value="";
    }
    else
    {
        document.getElementsByName("vlanAllow")[0].value="";
<%--          document.getElementsByName("portsDeny")[0].value=port_text.value;  --%>
    }
}

</SCRIPT>


<div style="text-align: left;" class="home_image">
<table width="100%">
    <tr></tr>
    <tr>
        <th><spring:message code="useredit.key" text="Key" /></th>
        <th><spring:message code="userview.value" text="Value" /></th>
    </tr>
    
    <tr>
        <form:form commandName="userProfileManager" name="userProfileManager" >
            <div style="display:none;"> 
                <input type="text" name="username" value="${userProfile.username}" />
<%--                  <input type="text" name="portsAllow" value="${userProfile.portsAllow}" />  --%>
<%--                  <input type="text" name="portsDeny" value="${userProfile.portsDeny}"/>  --%>
<%--                  <input type="text" name="vlanAllow" value="${userProfile.vlanAllow}" />  --%>
<%--                  <input type="text" name="vlanDeny"  value="${userProfile.vlanDeny}" />              --%>
            </div>
        <tr>
            <td> Ports </td>
            <td>
                    <select id="portType" onchange="changedPort(this)">
                        <option selected="selected" value="portsAllow">Allowed ports</option>
                        <option value="portsDeny">Denied ports</option>
                    </select>
            </td>
        </tr> 
        <tr>
            <td></td>
            <td>
                <div id="portDivAllow" >
                    <input type="text" id="portTextAllow" name="portsAllow" style="width: auto" size="80" 
                    value="${userProfile.portsAllow}" />
                </div>
                <div id="portDivDeny"  style="display:none;">
                    <input type="text" id="portTextDeny" name="portsDeny" style="width: auto" size="80" 
                    value="${userProfile.portsDeny}" />
                </div>
            <td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="button" value="add port" style="width: 20%" onClick="add_port()" align="top" name="_eventId_getPorts"/> 
                <div id="portList" style="display:none;border-width: 1px; border-style: solid; border-color: grey; " >
                    <select id="allPorts" multiple="multiple" size="7" style="width: 100%"> </select>                       
                    <input type="button" onClick="ports_ok()" value="ok" style="width: 20%" align="top"/>
                    <input type="button" onClick="ports_cancel()" value="cancel" style="width: 20%" align="top"/>  
                </div>
            </td>
        </tr>
        
        <tr></tr>
        <tr>
            <td> vlan </td>
            <td>
                    <select id="vlanType" onchange="changedVlan(this)">
                        <option selected="selected" value="vlanAllow">Allowed vlan</option>
                        <option value="vlanDeny">Denied vlan</option>
                    </select>
            </td>
        </tr> 
        <tr>
            <td></td>
            <td>
                <div id="vlanDivAllow">
                    <input type="text" id="vlanTextAllow" name="vlanAllow" style="width: auto" size="80" 
                    value="${userProfile.vlanAllow}" />
                </div>
                <div id="vlanDivDeny"  style="display:none;">
                    <input type="text" id="vlanTextDeny" name="vlanDeny" style="width: auto" size="80" 
                    value="${userProfile.vlanDeny}" />
                </div>
            <td>
        </tr>
        <tr>
            <td></td>
        </tr>
        <tr>
            <td></td>
        </tr>
        <tr>
            <td>Allowed maximum capacity per request</td>
            <td>
                <input type="text" name="maxCapacity" style="width: auto" size="10" 
                value="${userProfile.maxCapacity}" />
            </td>
        </tr>
        <tr>
            <td colspan="5" align="center" style="padding-top: 20px">
<%--                  <input type="button" value="Save" style="width: auto" onClick="submit_editing()"/>  --%>
            <input type="submit" name="_eventId_save" value="Save" style="width: auto" onClick="submit_editing()" />
            </td>
        </tr>
    </form:form>
</table>
</div>


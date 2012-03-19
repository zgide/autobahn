<%@ include file="../common/includes.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/themes/style/table-css/demo_table_jui.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/jquery/tabs.css"/>"/>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.dataTables.js"/>"></script>


<style>

.boxSearch{
	
	margin-top: 15px;
	margin-left: auto;
	margin-right: auto;
	height: 80px;
	width: 400px;
	
  	 border:1px solid #ccc;
	-moz-border-radius:10px;
	-webkit-border-radius:10px;
	-moz-box-shadow:0 0 15px #666;
	-webkit-box-shadow:0 0 15px #666;
	
}

.boxSearch button {
	width: 60px;
}

.itras_box input {
	width: 150px;
}

</style>

<script type="text/javascript">

var url = "dataTable.htm"+'?resState=ALL';

var prev_button_id = null;

function futureFilter(){
	buttonClearFilter();
	
	var date = new Date();
	console.log(date);

	var aData = $('#tabid').dataTable().fnGetData( this.parentNode );
	console.log(aData[0]);
}

function buttonFilter(i){	
	buttonClearFilter();
	$('#tabid').dataTable().fnFilter(i);
}

function buttonClearFilter(){
	
	$('#tabid').dataTable().fnFilter("");
}

function fnFilterColumn ( i )
{	
	buttonClearFilter();
	$('#tabid').dataTable().fnFilter( 
		$("#col"+(i+1)+"_filter").val(),
		i
	);
}

function fnFilterGlobal ()
{
	$('#tabid').dataTable().fnFilter( 
		$("#global_filter").val(),
		null 
	);
}

function setButtonColor(id){
	if(prev_button_id != id){
		if(prev_button_id != null)
			document.getElementById(prev_button_id).style.border='1px solid #999999';
		prev_button_id = id;
	}
	document.getElementById(id).style.border='2px solid #25648D';
}

var oTable;

jQuery(document).ready(function() {	 
		 $(function() {		
			
	oTable = $('#tabid').dataTable( {
	                "bServerSide": true,
	                "sAjaxSource": url,
	                "bProcessing": true,
	                "sPaginationType": "full_numbers",
				    "iDisplayLength": 8,
			   		'fnRowCallback': function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
					if ( aData[1] == "ACTIVE" ){	
						nRow.className="class_active";								
					}
					if ( aData[1] == "SCHEDULED" ){	
						nRow.className="class_scheduled";								
					}
					if ( aData[1] == "FAILED" ){	
						nRow.className="class_failed";								
					}
					if ( aData[1] == "FINISHED" ){	
						nRow.className="class_finished";								
					}
					if ( aData[1] == "CANCELLED" ){	
						nRow.className="class_cancelled";								
					}


					return nRow;
				  },
				  "aoColumns": [
				                null,
				                { "bSortable": true, "bVisible": true }, //disable column sorting
				                null,
				                null,
				                null,
				                null,
				                null
				            ]
				  				  
			   } );
		
				$('#button_active').click( function () { 
					var data = oTable.fnSettings();
					data.sAjaxSource = "dataTable.htm"+'?resState=ACTIVE';
					oTable.fnDraw(data);
					setButtonColor("button_active");
				} );
				
				$('#button_future').click( function () { 
					var data = oTable.fnSettings();
					data.sAjaxSource = "dataTable.htm"+'?resState=SCHEDULED';
					oTable.fnDraw(data);
					setButtonColor("button_future");
				} );
				
				$('#button_failed').click( function () { 
					var data = oTable.fnSettings();
					data.sAjaxSource = "dataTable.htm"+'?resState=FAILED';
					oTable.fnDraw(data);
					setButtonColor("button_failed");
				} );
				
				$('#button_all').click( function () { 
					var data = oTable.fnSettings();
					data.sAjaxSource = "dataTable.htm"+'?resState=ALL';
					oTable.fnDraw(data);
					setButtonColor("button_all");
				} );
				
				$("#global_filter").keyup( fnFilterGlobal );
		});
		 

	});	 


</script>

<h2><spring:message code="intra_domain_reservations.htitle" text="Intradomain Reservations"/></h2>
<form:form commandName="services" action="" id="intrasform">
<div align="center" class="logs_image">
	<div class="itras_box" align="center">
		<table cellspacing="5" cellpadding="3">
			<tbody>
				<tr>									
					<td><input type="button" value="Active" id="button_active"></td>  				
    				<td><input type="button" value="Future" id="button_future"></td>
    				<td></td>
					<td>Search all columns:</td>
				</tr>
				<tr>
					<td><input type="button" value="Failed" id="button_failed"></td>
					<td><input type="button" value="All" id="button_all"></td>
					<td></td>
					<td><input type="text" name="global_filter" id="global_filter" style="width: 150px;"></td>
				</tr>
			</tbody>	
		</table>		
	</div>	
		
		<c:if test="${intras != null}" >
            
            <div id="collection">
				<table id="tabid" width="100%">
					<thead>
	                    <tr>
	                        <th class="label">Reservation</th>
	                        <th class="label">State</th>
	                        <th class="label" style="width: 100px;">Capacity [Mbits/s]</th>
                            <th class="label">Start time</th>
                            <th class="label">End time</th>
                            <th class="label" style="width: 80px;">Prev domain</th>
                            <th class="label" style="width: 80px;">Next domain</th>
	                    </tr>
					</thead>
					
					<tbody>
				
					</tbody>
				</table>
            </div>
		</c:if>
		
        <c:if test="${intras == null}" >
            Cannot retrieve intradomain reservation information. Cannot connect to IDM.
        </c:if>
 </div>
</form:form>
<script type="text/javascript">


</script>
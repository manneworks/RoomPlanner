<g:applyLayout name="twoblocks">
<head>
<title><g:message code="title.superuser.index" /></title>
<asset:stylesheet src="datatables.css"/>
<asset:javascript src="datatables.js"/>
</head>

<content tag="top"> 
</content>

<content tag="main">
<div class="row show-grid">
<div class="col-lg-10">
    <legend><i class="fa fa-bolt"></i> Requests</legend>

    <table id="allrequests" class="table table-striped table-condensed">
    <thead>
    	<tr>
    		<th>#</th>
    		<th>License</th>
    		<th>Timestamp</th>
    		<th>Duration</th>
    	</tr>
    </thead>
    <tbody>
	<g:each in="${requestInstanceList}" status="i" var="item">
    	<!-- render items here -->
    	<tr>
    		<td><g:link action="showRequestDetail" id="${item.id}"><code>${i+1}</code></g:link></td>
    		<td>${item.licenseKey}</td>
    		<td>${new Date((long)(item.timestamp))}</td>
    		<td>${(long)(item.requestDuration)} ms</td>
	</g:each>    
	</tbody>	
	</table>
</div>
<div class="col-lg-2">
    <legend><i class="fa fa-tasks"></i> Actions</legend>
    <g:link action="archivate" class="btn btn-primary btn-block">Archivate data</g:link>
</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
    $('#allrequests').dataTable(
    {
    })
});
</script>
</content>

</g:applyLayout>

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
    <legend><i class="fa fa-cogs"></i> Partners</legend>
   
    <table id="allpartners" class="table table-striped table-condensed">
    <thead>
    	<tr>
    		<th>#</th>
    		<th>Partner ID</th>
    		<th>Enabled</th>
    	</tr>
    </thead>
    <tbody>
	<g:each var="item" in="${partnerInstanceList}" status="i">
    	<!-- render items here -->
    	<tr>
    		<td>${i+1}</td>
    		<td><g:link action="showPartnerDetail" id="${item.id}"><code>${item.username}</code></g:link></td>
    		<td>${item.enabled}</td>
	</g:each>    
	</tbody>	
	</table>
</div>
<div class="col-lg-2">
    <legend><i class="fa fa-tasks"></i> Actions</legend>
    <g:link action="createNewPartner" class="btn btn-primary btn-block">Create new partner</g:link>
</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
    $('#allpartners').dataTable(
    {
        "paging":   false,
        "ordering": false,
        "info":     false,
        "filter":   false 
    })
});
</script>
</content>

</g:applyLayout>

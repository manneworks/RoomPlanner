<g:applyLayout name="twoblocks">
<head>
<title><g:message code="title.superuser.index" /></title>
<asset:javascript src="chart.js"/>
</head>

<content tag="top"> 
</content>

<content tag="main">
<div class="row show-grid">
<div class="col-lg-8">
    <legend><i class="fa fa-cogs"></i> Partners</legend>
   
    <table class="table table-condensed">
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
    <div class="row" id="pagination">
    	<div class="col-lg-12">
    		<g:paginate next="Forward" prev="Back"
            		maxsteps="0" controller="admin"
            		action="index" total="${partnerInstanceCount}" />
        </div>
    </div>

</div>
<div class="col-lg-4">
    <legend><i class="fa fa-tasks"></i> Actions</legend>
    <g:link action="createNewPartner" class="btn btn-primary">Create new partner</g:link>
</div>
</div>

</content>

</g:applyLayout>

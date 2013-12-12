<g:applyLayout name="twoblocks">
<head>
<title><g:message code="title.superuser.index" /></title>
</head>

<content tag="top"> 
</content>

<content tag="main">
<div class="row show-grid">
<div class="col-lg-8">
    <legend><i class="fa fa-bolt"></i> Requests</legend>

    <table class="table table-condensed">
    <thead>
    	<tr>
    		<th>#</th>
    		<th>License</th>
    		<th>Timestamp</th>
    		<th>Duration</th>
    	</tr>
    </thead>
    <tbody>
	<g:each var="item" in="${requestInstanceList}" status="i">
    	<!-- render items here -->
    	<tr>
    		<td><g:link action="showRequestDetail" id="${item.id}"><code>${i+1}</code></g:link></td>
    		<td>${item.licenseKey}</td>
    		<td>${new Date((long)(item.timestamp))}</td>
    		<td>${(long)(item.requestDuration)} ms</td>
	</g:each>    
	</tbody>	
	</table>
    <div class="row" id="pagination">
    	<div class="col-lg-12">
    		<g:paginate next="Forward" prev="Back"
            		maxsteps="0" controller="request"
            		action="index" total="${requestInstanceCount}" />
        </div>
    </div>
</div>
<div class="col-lg-4">
    <legend><i class="fa fa-tasks"></i> Actions</legend>
    <g:link action="archivate" class="btn btn-primary">Archivate data</g:link>
</div>
</div>

</content>

</g:applyLayout>

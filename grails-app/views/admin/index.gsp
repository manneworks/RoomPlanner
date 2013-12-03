<%@ page import="utils.MillisToSpanConverter" %>

<g:applyLayout name="twoblocks">
<head>
<title><g:message code="title.superuser.index" /></title>
</head>

<content tag="top"> 
</content>

<content tag="main">
<div class="row show-grid">
<div class="col-lg-9">
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
    		<td>${i+1}</td>
    		<td>${item.licenseKey}</td>
    		<td>${new Date((long)(item.timestamp))}</td>
    		<td>${(long)(item.requestDuration)} ms</td>
	</g:each>    
	</tbody>	
	</table>
    <div class="row" id="pagination">
    	<div class="col-lg-12">
    		<g:paginate next="Forward" prev="Back"
            		maxsteps="0" controller="admin"
            		action="index" total="${requestInstanceCount}" />
        </div>
    </div>
</div>
<div class="col-lg-3">
    <legend><i class="fa fa-bar-chart-o"></i> Status</legend>
    <ul class="list-unstyled">
        <li>Uptime: ${MillisToSpanConverter.getDurationBreakdown(new Long(status.uptime))}</li> 
        <li>Request served: ${status.requestsServed}</li> 
        <li>Request served total: ${status.requestsServedTotal}</li> 
    </ul>
    <h4>Versions</h4>
    <ul class="list-unstyled">
        <li>Application version: ${status.applicationVersion}</li> 
        <li>roomplannerApi version: ${status.roomplannerApiVersion}</li> 
        <li>roombixUi version: ${status.roombixUiVersion}</li> 
        <li>Optaplanner version: ${status.optaplannerVersion}</li> 
        <li>Hibernate version: ${status.hibernateVersion}</li> 
        <li>MySQL Connector version: ${status.mysqlConnectorVersion}</li> 
        <li>Java version: ${status.javaVersion}</li> 
    </ul>
</div>
</div>

</content>

</g:applyLayout>

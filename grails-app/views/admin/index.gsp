<%@ page import="utils.MillisToSpanConverter" %>

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
    		<td><g:link controller="request" action="showRequestDetail" id="${item.id}"><code>${i+1}</code></g:link></td>
    		<td>${item.licenseKey}</td>
    		<td>${new Date((long)(item.timestamp))}</td>
    		<td>${(long)(item.requestDuration)} ms</td>
	</g:each>    
	</tbody>	
	</table>
    <div class="row">
    	<div class="col-lg-12">
            <g:link controller="request">All requests</g:link>
        </div>
    </div>
</div>
<div class="col-lg-4">
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
    <h4>Partners</h4>
    <ul class="list-unstyled">
        <li>Count: ${status.partnerCount}</li> 
    </ul>
</div>
</div>

</content>

</g:applyLayout>

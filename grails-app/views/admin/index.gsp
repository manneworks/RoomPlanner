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
</div>
<div class="col-lg-3">
    <legend><i class="fa fa-bar-chart-o"></i> Status</legend>
    <ul class="list-unstyled">
        <li>Application version: ${status.applicationVersion}</li> 
        <li>Optaplanner version: ${status.optaplannerVersion}</li> 
        <li>Java version: ${status.javaVersion}</li> 
        <li>Uptime: ${MillisToSpanConverter.getDurationBreakdown(new Long(status.uptime))}</li> 
        <li>Request served: ${status.requestsServed}</li> 
        <li>Request served total: ${status.requestsServedTotal}</li> 
    </ul>
</div>
</div>

</content>

</g:applyLayout>

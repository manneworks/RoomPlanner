<g:applyLayout name="twoblocks">
<head>
<title><g:message code="title.superuser.index" /></title>
</head>

<content tag="top"> 
</content>

<content tag="main">
<div class="row show-grid">
<div class="col-lg-12">
    <legend><i class="fa fa-bolt"></i> Request</legend>

<g:form class="form-horizontal" action="#" method="POST">

        <div class="form-group">
            <label class="col-lg-3 control-label">
                <g:message code="user.name.label" default="License key" />
            </label>
            <div class="col-lg-9">
                <p class="form-control-static">${requestInstance.licenseKey}</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">
                <g:message code="user.name.label" default="Timestamp" />
            </label>
            <div class="col-lg-9">
                <p class="form-control-static">${new Date((long)(requestInstance.timestamp))}</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">
                <g:message code="user.name.label" default="Duration" />
            </label>
            <div class="col-lg-9">
                <p class="form-control-static">${requestInstance.requestDuration} ms</p>
            </div>
        </div>

    </g:form>    

</div>
</div>

</content>

</g:applyLayout>

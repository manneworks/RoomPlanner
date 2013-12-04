<g:applyLayout name="twoblocks">
<head>
<title><g:message code="title.superuser.index" /></title>
</head>

<content tag="top"> 
</content>

<content tag="main">
<div class="row show-grid">
<div class="col-lg-12">
    <legend><i class="fa fa-cogs"></i> Partner</legend>

    <g:form class="form-horizontal" action="#" method="POST">

        <div class="form-group">
            <label class="col-lg-3 control-label">
                <g:message code="user.name.label" default="Partner ID" />
            </label>
            <div class="col-lg-9">
                <p class="form-control-static">${partnerInstance.username}</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">
                <g:message code="user.name.label" default="Password" />
            </label>
            <div class="col-lg-9">
                <p class="form-control-static">${partnerInstance.password}</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">
                <g:message code="user.name.label" default="Status" />
            </label>
            <div class="col-lg-9">
                <p class="form-control-static">
                    <g:if test="${partnerInstance.enabled}">
                        Enabled
                    </g:if>
                    <g:else>
                        Disabled
                    </g:else>
                </p>
            </div>
        </div>
    </g:form>    

    <g:form action="disablePartner" class="form-horizontal">
        <div class="form-group">
            <div class="col-lg-4 col-lg-offset-8">
                <input type="hidden" name="id" value="${partnerInstance.id}"/>
                <g:submitButton class="btn btn-warning" name="disable" value="Disable partner" />
            </div>
        </div>
    </g:form>

    <g:form action="deletePartner" class="form-horizontal">
        <div class="form-group">
            <div class="col-lg-4 col-lg-offset-8">
                <input type="hidden" name="id" value="${partnerInstance.id}"/>
                <g:submitButton class="btn btn-danger" name="delete" value="Delete partner" />
            </div>
        </div>
    </g:form>

</div>
</div>

</content>

</g:applyLayout>

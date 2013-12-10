<g:applyLayout name="twoblocks">
<head>
<title><g:message code="title.superuser.index" /></title>
<asset:javascript src="chart.js"/>
<asset:stylesheet src="daterangepicker/daterangepicker-bs3.css"/>
<asset:javascript src="daterangepicker.js"/>
</head>

<content tag="top"> 
</content>

<content tag="main">
<div class="row show-grid">
<div class="col-lg-6">
    <legend id="chart1Legend"><i class="fa fa-clock-o"></i> Request duration</legend>
    <h5>Min/Avg/Max: ${chart1MinValue}/${chart1AvgValue}/${chart1MaxValue} ms</h5>
    <canvas id="requestDurationChart" width="500" height="250"></canvas>
</div>
<div class="col-lg-6">
    <legend id="chart2Legend"><i class="fa fa-tachometer"></i> Request count</legend>
    <h5>Min/Avg/Max: ${chart2MinValue}/${chart2AvgValue}/${chart2MaxValue}
    <div class="btn-group btn-group-xs pull-right">
        <button type="button" class="btn btn-default">Week</button>
        <button type="button" class="btn btn-default">Month</button>
        <button type="button" class="btn btn-default">Year</button>
    </div>
    </h5>
    <canvas id="requestCountChart" width="500" height="250"></canvas>
</div>
</div>
<!--
<div class="row show-grid">
<div class="col-lg-6">
    <legend><i class="fa fa-clock-o"></i> Request duration</legend>

</div>
<div class="col-lg-6">
    <legend><i class="fa fa-tachometer"></i> Request count</legend>

</div>
</div>
-->
<script type='text/javascript'>
$(document).ready(function() {

    var chart1Width = $("#chart1Legend")[0].clientWidth;
    $("canvas#requestDurationChart")[0].width = chart1Width;

    var ctx1 = $("canvas#requestDurationChart").get(0).getContext("2d");
    var data1 = {
            labels : ${chart1Labels},
            datasets : [
                {
                    fillColor : "rgba(151,187,205,0.5)",
                    strokeColor : "rgba(151,187,205,1)",
                    pointColor : "rgba(151,187,205,1)",
                    pointStrokeColor : "#fff",
                    data : ${chart1Values}
                }
            ]
        };
    var options1 = {
        bezierCurve:true
    };
    new Chart(ctx1).Line(data1,options1);

    var chart2Width = $("#chart2Legend")[0].clientWidth;
    $("canvas#requestCountChart")[0].width = chart2Width;

    var ctx2 = $("#requestCountChart").get(0).getContext("2d");
    var data2 = {
            labels : ${chart2Labels},
            datasets : [
                {
                    fillColor : "rgba(151,187,205,0.5)",
                    strokeColor : "rgba(151,187,205,1)",
                    data : ${chart2Values}
                }
            ]
            
        };
    var options2 = {
        bezierCurve:true
    };
    new Chart(ctx2).Line(data2,options2);

});
</script>
</content>

</g:applyLayout>

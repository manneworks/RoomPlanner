<g:applyLayout name="twoblocks">
<head>
<title><g:message code="title.superuser.index" /></title>
<asset:javascript src="chart.js"/>
</head>

<content tag="top"> 
</content>

<content tag="main">
<div class="row show-grid">
<div class="col-lg-6">
    <legend><i class="fa fa-clock-o"></i> Request duration</legend>
    <canvas id="requestDurationChart" width="500" height="250"></canvas>
    <h5 class="text-right">Min/Avg/Max: ${chart1MinValue}/${chart1AvgValue}/${chart1MaxValue} ms</h5>
</div>
<div class="col-lg-6">
    <legend><i class="fa fa-tachometer"></i> Request count</legend>
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
    var ctx1 = $("#requestDurationChart").get(0).getContext("2d");
    var data1 = {
            labels : ${chart1Labels},
            datasets : [
                // {
                //     fillColor : "rgba(220,220,220,0.5)",
                //     strokeColor : "rgba(220,220,220,1)",
                //     pointColor : "rgba(220,220,220,1)",
                //     pointStrokeColor : "#fff",
                //     data : [65,59,90,81,56,55,40]
                // },
                {
                    fillColor : "rgba(151,187,205,0.5)",
                    strokeColor : "rgba(151,187,205,1)",
                    pointColor : "rgba(151,187,205,1)",
                    pointStrokeColor : "#fff",
                    data : ${chart1Values}
                }
            ]
        };
    var options1 = {};
    new Chart(ctx1).Line(data1,options1);

    var ctx2 = $("#requestCountChart").get(0).getContext("2d");
    var data2 = {
            labels : ["January","February","March","April","May","June","July"],
            datasets : [
                {
                    fillColor : "rgba(220,220,220,0.5)",
                    strokeColor : "rgba(220,220,220,1)",
                    data : [65,59,90,81,56,55,40]
                },
                {
                    fillColor : "rgba(151,187,205,0.5)",
                    strokeColor : "rgba(151,187,205,1)",
                    data : [28,48,40,19,96,27,100]
                }
            ]
            
        };
    var options2 = {};
    new Chart(ctx2).Bar(data2,options2);
});
</script>
</content>

</g:applyLayout>

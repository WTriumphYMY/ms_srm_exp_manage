$('#smooth').click(function(){
    var pkId=$('#pkId').val();
    $.ajax({
        url:"/smoothData",
        type:"post",
        dataType:"json",
        data:"pkId="+pkId,//传到后端
        async:false,
        cache:false,
        success:function(data){
            alert("drawChart执行");
            $('#exampleModal').modal('toggle');
            drawChart(data.t, data.p, "p");
        },
        error:function () {
            document.write(pkId);
            alert("获取数据失败");
        }

    });
})

function drawChart(xdata, ydata, yname){
    var chart = Highcharts.chart('container', {
        title: {
            text: '内弹道'
        },
        boost: {
            useGPUTranslations: true
        },
        subtitle: {
            text: '数据来源：我算出来的'
        },
        xAxis: {
            categories: xdata
        },
        yAxis: {
            title: {
                text: yname
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },

        series: [{
            name: yname,
            data: ydata
        }],
        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                chartOptions: {
                    legend: {
                        layout: 'horizontal',
                        align: 'center',
                        verticalAlign: 'bottom'
                    }
                }
            }]
        }
    });
}

Highcharts.chart('container', {
    chart: {
        zoomType: 'x'
    },
    boost: {
        useGPUTranslations: true
    },
    title: {
        text: 'Highcharts drawing ' + n + ' points'
    },
    subtitle: {
        text: 'Using the Boost module'
    },
    tooltip: {
        valueDecimals: 2
    },
    series: [{
        data: data,
        lineWidth: 0.5
    }]
});
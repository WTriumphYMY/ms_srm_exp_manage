$(function () {
    var t = $('#timedata').val();
    var p = $('#pdata').val();
    var f = $('#fdata').val();
    drawChart(t.split(',').map(Number), p.split(',').map(Number), '压强P', 'pchart');
    drawChart(t.split(',').map(Number), f.split(',').map(Number), '推力F', 'fchart');
})

function drawChart(xdata, ydata, yname, panel){
    var chart = Highcharts.chart(panel, {
        title: {
            text: '内弹道'
        },
        boost: {
            useGPUTranslations: true
        },
        subtitle: {
            text: '原始数据'
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
$(function () {
    $('#pChart').click(function () {

    })
})

function showChart(t, y, type) {
    $('#figureModal').modal('toggle');
    var yname;
    if (type == 'p')
        yname = "压强P(MPa)";
    if (type == 'f')
        yname = "推力F(kN)";
    drawChart(t.split(',').map(Number),y.split(',').map(Number),yname);
}

function addNew() {
    $('#srmNameModal').val('');
    $('#addModal').modal('toggle');
}

function detailSingleRec(id) {
    window.open("/getSrmExpById/"+id);
}

function delSingleRec(id) {
    $.ajax({
        url:"/deleteById/"+id,
        type:"get",
        async: false,
        cache: false,
        processData: false,
        contentType: false,
        success:function(){
            window.location.reload();
        },
        error:function(){
            alert("删除失败");
        }
    });
}

function editSingleRec(srmName) {
    $('#srmNameModal').val(srmName);
    $('#addModal').modal('toggle');
}

function drawChart(xdata, ydata, yname){
    var chart = Highcharts.chart('figcontainer', {
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


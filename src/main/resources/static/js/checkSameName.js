$('#submitName').click(function(){
     alert("按钮按下");
     var expFile = $('#expFile').val();
     var srmName = $('#srmName').val();
    // $('#exampleModal').modal('toggle');
    $.ajax({
        url:"/srmExperiment",
        type:"post",
        dataType:"json",
        data:{
            "expFile": expFile,
            "srmName":srmName
        },//传到后端
        async:false,
        success:function(data){
            alert("drawChart执行");
            // $('#exampleModal').modal('toggle');
            if(data==true){
                window.open("/listAll");
            }
            else {
                alert("已有同名数据")
            }
        }
    });
})
function cancel() {
    layer.close(parent.data.addInd);
}

function addAppApi() {
    console.log(parent.data.apiId);
    console.log(parent.data.appId);
    console.log($('input').val());

    var entity={};
    entity.appId = parent.data.appId;
    entity.apiId = parent.data.apiId;
    entity.selectField = $('input').val();

    $.ajax({
        url:'/appapi/save',
        type:'POST',
        dataType:'json',
        contentType:'application/json',
        data:JSON.stringify(entity),
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success:function (data) {
            debugger;
            console.log(data)
            if ('0' == data.code) {
                $('#appapiTable').bootstrapTable('refresh');
            }
            parent.layer.msg(data.msg)
            layer.close();
        }
    })
}
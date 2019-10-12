$(function () {
    loadApis();
    var appid = parent.data.appId;
    loadAppApi(appid);
});

function loadApis() {
    $('#apiTable').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: "api/list", // 服务器数据的加载地址
        //	showRefresh : true,
        //	showToggle : true,
        //	showColumns : true,
        iconSize: 'outline',
        toolbar: '#apiTableToolbar',
        striped: true, // 设置为true会有隔行变色效果
        dataType: "json", // 服务器返回的数据类型
        pagination: true, // 设置为true会在底部显示分页条
        // queryParamsType : "limit",
        // //设置为limit则会发送符合RESTFull格式的参数
        singleSelect: false, // 设置为true将禁止多选
        // contentType : "application/x-www-form-urlencoded",
        // //发送到服务器的数据编码类型
        pageSize: 10, // 如果设置了分页，每页数据条数
        pageNumber: 1, // 如果设置了分布，首页页码
        //search : true, // 是否显示搜索框
        showColumns: false, // 是否显示内容下拉框（选择显示的列）
        sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
        queryParams: function (params) {
            // debugger
            return {
                //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                // limit: params.limit,
                // offset: params.offset
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                // name:$('#searchName').val(),
                // username:$('#searchName').val()
            };
        },
        // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
        // queryParamsType = 'limit' ,返回参数必须包含
        // limit, offset, search, sort, order 否则, 需要包含:
        // pageSize, pageNumber, searchText, sortName,
        // sortOrder.
        // 返回false将会终止请求
        columns: [
            {
                checkbox: true
            },
            {
                field: 'name',
                title: 'api名称'
            },
            {
                field: 'path',
                title: '路径'
            },{
                field: 'method',
                title: '方法'
            }
            // ,{
            //     title:  '操作',
            //     field:  'id',
            //     align:  'center',
            //     formatter: function (value, row, index) {
            //         var a = '<a class="btn btn-info btn-sm " href="#" title="添加"  mce_href="#" onclick="addToApp(\''
            //             + row.id
            //             + '\')"><i class="fa fa-check"></i></a> ';
            //
            //         return a;
            //     }
            // }
        ]
    });
}

function loadAppApi(appid) {
    $('#appapiTable').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: "appapi/"+appid, // 服务器数据的加载地址
        //	showRefresh : true,
        //	showToggle : true,
        //	showColumns : true,
        iconSize: 'outline',
        toolbar: '#appapiTableToolbar',
        striped: true, // 设置为true会有隔行变色效果
        dataType: "json", // 服务器返回的数据类型
        pagination: false, // 设置为true会在底部显示分页条
        // queryParamsType : "limit",
        // //设置为limit则会发送符合RESTFull格式的参数
        singleSelect: false, // 设置为true将禁止多选
        // contentType : "application/x-www-form-urlencoded",
        // //发送到服务器的数据编码类型
        // pageSize: 10, // 如果设置了分页，每页数据条数
        // pageNumber: 1, // 如果设置了分布，首页页码
        //search : true, // 是否显示搜索框
        showColumns: false, // 是否显示内容下拉框（选择显示的列）
        // sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
        queryParams: function (params) {
            // debugger
            return {
                //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                // limit: params.limit,
                // offset: params.offset
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                // name:$('#searchName').val(),
                // username:$('#searchName').val()
            };
        },
        // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
        // queryParamsType = 'limit' ,返回参数必须包含
        // limit, offset, search, sort, order 否则, 需要包含:
        // pageSize, pageNumber, searchText, sortName,
        // sortOrder.
        // 返回false将会终止请求
        columns: [
            {
                checkbox: true
            },
            {
                field: 'apiName',
                title: 'api名称'
            },
            {
                field: 'selectedFields',
                title: '选择字段',
                formatter:function (value,row,index) {
                    if ('' == value){
                        return '全部';
                    }
                    return value;
                }
            },{
                title:  '操作',
                field:  'id',
                align:  'center',
                formatter: function (value, row, index) {
                    var a = '<a class="btn btn-danger btn-sm " href="#" title="删除"  mce_href="#" onclick="remove(\''
                        + row.id
                        + '\')"><i class="fa fa-remove"></i></a> ';

                    var b = '<a class="btn btn-info btn-sm " href="#" title="编辑"  mce_href="#" onclick="edit(\''
                    + row.id
                    + '\',\''
                    + row.selectedFields
                    + '\')"><i class="fa fa-edit"></i></a> ';

                    return  b;
                }
            }]
    });
}

function edit(id,field) {
    var inputStr = '';
    if ('' == field){
        inputStr = "<input type=\"text\" id=\"selectedField\" class=\"form-control\" placeholder=\"用','隔开，不填则返回所有字段\">";
    }else {
        inputStr = "<input type=\"text\" id=\"selectedField\" value="+field+" class=\"form-control\" placeholder=\"用','隔开，不填则返回所有字段\">";
    }

    layer.confirm(inputStr,{
        btn: ['确定', '取消']
    },function () {
        var entity={};
        entity.id = id;
        entity.selectField = $('#selectedField').val();

        $.ajax({
            url:'/appapi/update',
            type:'PATCH',
            dataType:'json',
            contentType:'application/json',
            data:JSON.stringify(entity),
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success:function (data) {
                if ('0' == data.code) {
                    $('#appapiTable').bootstrapTable('refresh');
                }
                layer.msg(data.msg);
            }
        })
    })
}

function addToApp(id) {

    var inputStr = "<input type=\"text\" id=\"selectedField\" class=\"form-control\" placeholder=\"用','隔开，不填则返回所有字段\">";

    layer.confirm(inputStr,{
        btn: ['确定', '取消']
    },function () {
        var entity={};
        entity.appId = parent.data.appId;
        entity.apiId = id;
        entity.selectField = $('#selectedField').val();

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
                if ('0' == data.code) {
                    $('#appapiTable').bootstrapTable('refresh');
                }
                layer.msg(data.msg);
            }
        })
    })
}

function addToAppBatch() {

    var entity={};
    entity.appId = parent.data.appId;

    var rows = $('#apiTable').bootstrapTable('getSelections');
    var ids = '';
    $.each(rows,function(index,row){
        ids += ','+row.id;
    });

    ids = ids.substring(1);

    entity.apiIds = ids;

    if ('' == ids){
        layer.msg('请选择API');
        return;
    }

    $.ajax({
        url:'/appapi/batchSave',
        type:'POST',
        dataType:'json',
        contentType:'application/json',
        data:JSON.stringify(entity),
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success:function (data) {
            if ('0' == data.code) {
                $('#appapiTable').bootstrapTable('refresh');
            }
            layer.msg(data.msg);
        }
    })
}

function revoveBatch() {
    var rows = $('#appapiTable').bootstrapTable('getSelections');
    var ids = '';
    $.each(rows,function(index,row){
        ids += ','+row.id;
    });

    ids = ids.substring(1);

    if ('' == ids){
        layer.msg('请选择要删除的选项');
        return;
    }

    layer.confirm('确定删除？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: '/appapi/batch/'+ids,
            type: 'DELETE',
            dataType: 'json',
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success: function (data) {
                if ('0' == data.code) {
                    $('#appapiTable').bootstrapTable('refresh');
                }
                layer.msg(data.msg);
            }
        });
    });
}

function remove(id) {
    layer.confirm('确定删除？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: '/appapi/'+id,
            type: 'DELETE',
            dataType: 'json',
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success: function (data) {
                if ('0' == data.code) {
                    $('#appapiTable').bootstrapTable('refresh');
                }
                layer.msg(data.msg);
            }
        });
    });
}

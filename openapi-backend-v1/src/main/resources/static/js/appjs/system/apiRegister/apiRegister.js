var prefix = "/openApi"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                showRefresh : true,
                showToggle : true,
                showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
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
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
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
                        field: 'id',
                        title: 'id'
                    },
                    {
                        field: 'service',
                        title: 'service'
                    },
                    {
                        field: 'group',
                        title: '分组'
                    },
                    {
                        field: 'createTime',
                        title: '创建时间'
                    },
                    {
                        field: 'createUser',
                        title: '创建者'
                    },
                   /* {
                        field: 'available',
                        title: '可用性'
                    },*/
                    {
                        title: 'API注册',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var u = '<a class="btn btn-primary btn-sm " href="#" mce_href="#" title="更新" onclick="register(\''
                                + row.id
                                + '\')"><i class="fa fa-repeat" aria-hidden="true"></i></a> ';
                            var i = '<a class="btn btn-warning btn-sm" href="#" title="新增"  mce_href="#" onclick="register(\''
                                + row.id
                                + '\')"><i class="fa fa-plus" aria-hidden="true"></i></a> ';
                            if (row.available == '0') {
                                return i;
                            } else {
                                return u;
                            }
                        }
                    }]
            });
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function register(id) {
    layer.confirm('确定要注册该服务吗？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/register",
            type: "post",
            data: {
                'serviceId': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg("注册失败");
                }
            },
            error : function (r) {
                layer.msg("注册失败");
            }
        });
    });
}
lyfdefine(['addApiPowerApiEdit'],function(addApiPowerApiEdit){
    return ({
        data: function data() {
            return {
                visible: false,
                available:true,
                apiId:'',
                dataForm: {
                    path:""
                },
                dataList1:[],
                dataList2:[],
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                dataListLoading1: false,
                dataListLoading2: false,
                apiEditVisible:true,
            };
        },
        components: {
            addApiPowerApiEdit : addApiPowerApiEdit,
        },
        methods: {
            init: function init(id) {
                this.visible = true;
                this.apiId = id;
                this.getDataList2();
                if (id){
                    this.getDataList1()
                }
            },
            // 获取数据列表
            getDataList1: function getDataList1() {
                var _this1 = this;
                _this1.dataListLoading1 = true;
                this.$http({
                    url: '/openapi-backend/appapi/'+_this1.apiId,
                    method: 'get'
                }).then(function (_ref) {
                    var data = _ref.data;
                    if (data) {
                        _this1.dataList1 = data;
                    } else {
                        _this1.dataList1 = [];
                    }
                    _this1.dataListLoading1 = false;
                });
            },
            getDataList2: function getDataList2() {
                var _this2 = this;
                _this2.dataListLoading2 = true;
                this.$http({
                    url: '/openapi-backend/api/list',
                    method: 'post',
                    data: _this2.$http.adornData({
                        'pageNum': this.pageIndex,
                        'pageSize': this.pageSize
                    })
                }).then(function (_ref) {
                    var data = _ref.data;
                    if (data.rows) {
                        _this2.dataList2 = data.rows;
                        _this2.totalPage = data.total;
                    } else {
                        _this2.dataList2 = [];
                        _this2.totalPage = 0;
                    }
                    _this2.dataListLoading2 = false;
                });
            },
            //路径查询列表
            getData:function getData(){
                var _this3 = this;
                _this3.dataListLoading2 = true;
                this.$http({
                    url: '/openapi-backend/api/list',
                    method: 'post',
                    data: _this3.$http.adornData({
                        'pageNum': this.pageIndex,
                        'pageSize': this.pageSize,
                        'path':_this3.dataForm.path
                    })
                }).then(function (_ref) {
                    var data = _ref.data;
                    console.log(data)
                    if (data.rows) {
                        _this3.dataList2 = data.rows;
                        _this3.totalPage = data.total;
                    } else {
                        _this3.dataList2 = [];
                        _this3.totalPage = 0;
                    }
                    _this3.dataListLoading2 = false;
                });
            },
            // 每页数
            sizeChangeHandle: function sizeChangeHandle(val) {
                this.pageSize = val;
                this.pageIndex = 1;
                this.getDataList2();
            },

            // 当前页
            currentChangeHandle: function currentChangeHandle(val) {
                this.pageIndex = val;
                this.getDataList2();
            },
            // // 每页数
            // sizeChangeHandle: function sizeChangeHandle(val) {
            //     this.pageSize = val;
            //     this.pageIndex = 1;
            //     this.getData();
            // },
            //
            // // 当前页
            // currentChangeHandle: function currentChangeHandle(val) {
            //     this.pageIndex = val;
            //     this.getData();
            // },
            // 多选
            selectionChangeHandle: function selectionChangeHandle(val) {
                this.dataListSelections = val;
            },
            selectionChangeHandle1: function selectionChangeHandle1(val) {
                this.dataListSelections1 = val;
            },
            //选择字段
            formatIsAvailable(row, column, cellValue) {
                if (cellValue == "") {
                    return '全部';
                } else {
                    return cellValue;
                }
            },
            //添加api
            addApi:function addApi() {
                var _this3 = this;
                var ids = '';
                if(undefined == _this3.dataListSelections1){
                    _this3.$message({
                        message: '请选择api',
                        type: 'warning',
                        duration: 1000,
                    });
                }else {
                    for (var i=0;i<_this3.dataListSelections1.length;i++) {
                        if(i < _this3.dataListSelections1.length-1){
                            ids += _this3.dataListSelections1[i].id + ",";
                        }else{
                            ids += _this3.dataListSelections1[i].id;
                        }
                    }
                    this.$http({
                        url: '/openapi-backend/appapi/batchSave',
                        method: 'post',
                        data: _this3.$http.adornData({
                            'appId': this.apiId,
                            'apiIds': ids
                        })
                    }).then(function (_ref2) {
                        var data = _ref2.data;
                        if (data.code == 0) {
                            _this3.$message({
                                message: '操作成功',
                                type: 'success',
                                duration: 1000,
                            });
                            _this3.getDataList1(_this3.apiId);
                        }else {
                            _this3.$message({
                                message: data.msg,
                                type: 'repeat',
                                duration: 1000,
                            });
                        }
                    })
                }
            },
            //删除api
            deleteHandles: function deleteHandles() {
                var _this4 = this;
                var appApiIds = '';
                if(_this4.dataListSelections==undefined){
                    _this4.$message({
                        message: '请选择api',
                        type: 'warning',
                        duration: 1000,
                    });
                }else{
                    for (var i=0;i<_this4.dataListSelections.length;i++) {
                        if(i<_this4.dataListSelections.length-1){
                            appApiIds += _this4.dataListSelections[i].id += ",";
                        }else{
                            appApiIds += this.dataListSelections[i].id;
                        }
                    }
                    _this4.$confirm('确定要删除选中的记录?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        _this4.$http({
                            url: '/openapi-backend/appapi/batch/'+appApiIds,
                            method: 'delete'
                        }).then(function (_ref4) {
                            var data = _ref4.data;
                            if (data && data.code === 0) {
                                _this4.$message({
                                    message: '操作成功',
                                    type: 'success',
                                    duration: 1500,
                                    onClose:function onClose(){
                                        _this4.getDataList1(_this4.apiId);
                                    }
                                });
                            }else {
                                _this4.$message({
                                    message: data.msg,
                                    type: 'repeat',
                                    duration: 1000,
                                });
                            }
                        });
                    });
                }
            },
            //api编辑
            UpdateApiHandle:function UpdateApiHandle(row) {
                var _this5 = this;
                _this5.apiEditVisible = true;
                this.$nextTick( function () {
                    _this5.$refs.addApiPowerApiEdit.init(row);
                });
            }
        },
        template: `
  <el-dialog
    :title="'api权限'"
    :close-on-click-modal="false"
    :visible.sync="visible" width="70%">
    <div style="overflow: hidden;">
         <el-table  
          :data="dataList1"
          height="500"
          border
          size="mini"
          v-loading="dataListLoading1"
          @selection-change="selectionChangeHandle"
          style="width: 40%;float: left;margin-top: 63px;">
          <el-table-column
            type="selection"
            header-align="center"
            align="center"
            width="50">
          </el-table-column>
          <el-table-column
            prop="apiName"
            header-align="center"
            align="center"
            label="api名称">
          </el-table-column>
          <el-table-column
            prop="selectedFields"
            header-align="center"
            :formatter="formatIsAvailable"
            align="center"
            label="选择字段">
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="150"
            label="操作">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="UpdateApiHandle(scope.row)">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div style="float: left;margin: 150px 10px 0 10px;width: 10%;text-align: center">
            <el-button type="primary" size="mini" @click="addApi">添加</el-button>
            <br>
            <br>
            <el-button type="primary" type="danger" size="mini" @click="deleteHandles">删除</el-button>
        </div> 
        <div style="width: 45%;float: left;">
             <el-form :inline="true" :model="dataForm">
                 <el-form-item>
                    <el-input v-model="dataForm.path" placeholder="请输入路径" clearable></el-input>
                 </el-form-item>
                 <el-form-item>
                    <el-button @click="getData()">查询</el-button>
                 </el-form-item>
            </el-form>
            <el-table
              :data="dataList2"
              height="500"
              border
              size="mini"
              v-loading="dataListLoading2"
              @selection-change="selectionChangeHandle1">
              <el-table-column
                type="selection"
                header-align="center"
                align="center"
                width="50">
              </el-table-column>
              <el-table-column
                prop="name"
                header-align="center"
                align="center"
                label="api名称">
              </el-table-column>
              <el-table-column
                prop="path"
                header-align="center"
                align="center"
                label="路径">
              </el-table-column>
              <el-table-column
                prop="method"
                header-align="center"
                align="center"
                label="方法">
              </el-table-column>
            </el-table>
            <el-pagination
                @size-change="sizeChangeHandle"
                @current-change="currentChangeHandle"
                :current-page="pageIndex"
                :page-sizes="[10, 20, 50, 100]"
                :page-size="pageSize"
                :total="totalPage"
                small
                layout="total, sizes, prev, pager, next, jumper"
                style="text-align: right;margin-top:10px">
            </el-pagination>
        </div>
    </div>
    <add-api-power-api-edit v-if="apiEditVisible" ref="addApiPowerApiEdit" @refreshDataList="getDataList1"></add-api-power-api-edit>
  </el-dialog>
`
    });
});
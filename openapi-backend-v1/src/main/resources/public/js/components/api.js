lyfdefine(['updateApi'],function (updateApi) {
    return ({
        data: function data() {
            return {
                dataForm: {
                    path:""
                },
                dataList1: [],
                pageIndex1: 1,
                pageSize1: 10,
                totalPage1: 0,
                dataList2: [],
                pageIndex2: 1,
                pageSize2: 10,
                totalPage2: 0,
                dataListLoading1: false,
                dataListLoading2: false,
                dataListSelections: [],
                UpdateApiVisible: true,
                currentGroup:"所有分组",
                apiId:"",
            };
        },
        components: {
            updateApi : updateApi
        },
        mounted(){
            this.getDataList1();
            this.getDataList2();
        },
        methods: {
            // 获取数据列表
            getDataList1: function getDataList1() {
                let _this = this;
                _this.dataListLoading1 = true;
                this.$http({
                    url: '/openapi-backend/openApi/list',
                    method: 'post',
                    data: _this.$http.adornData({
                        'pageNum': this.pageIndex1,
                        'pageSize': this.pageSize1
                    })
                }).then(function (_ref) {
                    let data = _ref.data;
                    if (data.rows) {
                        _this.dataList1 = data.rows;
                        _this.totalPage1 = data.total;
                    } else {
                        _this.dataList1 = [];
                        _this.totalPage1 = 0;
                    }
                    _this.dataListLoading1 = false;
                });
            },

            getDataList2: function getDataList2() {
                let _this2 = this;
                _this2.dataListLoading2 = true;
                this.$http({
                    url: '/openapi-backend/api/list',
                    method: 'post',
                    data: _this2.$http.adornData({
                        'pageNum': this.pageIndex2,
                        'pageSize': this.pageSize2,
                        'serviceId':_this2.apiId
                    })
                }).then(function (_ref) {
                    let data = _ref.data;
                    if (data.rows) {
                        _this2.dataList2 = data.rows;
                        _this2.totalPage2 = data.total;
                    } else {
                        _this2.dataList2 = [];
                        _this2.totalPage2 = 0;
                    }
                    _this2.dataListLoading2 = false;
                });
            },
            //路径查询列表
            getData:function getData(){
                let _this3 = this;
                _this3.dataListLoading2 = true;
                this.$http({
                    url: '/openapi-backend/api/list',
                    method: 'post',
                    data: _this3.$http.adornData({
                        'pageNum': this.pageIndex2,
                        'pageSize': this.pageSize2,
                        'path':_this3.dataForm.path
                    })
                }).then(function (_ref) {
                    let data = _ref.data;
                    if (data.rows) {
                        _this3.dataList2 = data.rows;
                        _this3.totalPage2 = data.total;
                    } else {
                        _this3.dataList2 = [];
                        _this3.totalPage2 = 0;
                    }
                    _this3.dataListLoading2 = false;
                });
            },
            //状态
            formatIsAvailable(row, column, cellValue) {
                if (cellValue === false) {
                    return '否';
                } else {
                    return '是';
                }
            },

            // 左一每页数
            sizeChangeHandle1: function sizeChangeHandle(val) {
                this.pageSize1 = val;
                this.pageIndex1 = 1;
                this.getDataList1();
            },
            // 左一当前页
            currentChangeHandle1: function currentChangeHandle1(val) {
                this.pageIndex1 = val;
                this.getDataList1();
            },

            // 左2每页数
            sizeChangeHandle2: function sizeChangeHandle2(val) {
                this.pageSize2 = val;
                this.pageIndex2 = 1;
                this.getDataList2();
            },
            // 左2当前页
            currentChangeHandle2: function currentChangeHandle2(val) {
                this.pageIndex2 = val;
                this.getDataList2();
            },

            // 多选
            selectionChangeHandle: function selectionChangeHandle(val) {
                this.dataListSelections = val;
            },
            getZf(num){
                if(parseInt(num) < 10){
                    num = '0'+num;
                }
                return num;
            },
            // 修改
            updateApi: function updateApi(row) {
                let _this3 = this;
                _this3.UpdateApiVisible = true;
                this.$nextTick( function () {
                    _this3.$refs.updateApi.init(row);
                });
            },
            // 批量删除
            deleteHandles2: function deleteHandles2() {
                let _this4 = this;
                let ids = this.dataListSelections.map(function (item) {
                    return item.id;
                });
                let jsonData = JSON.stringify(ids);
                if (ids.length>0){
                    this.$confirm('确定对选中进行的批量删除操作?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        _this4.$http.post('/openapi-backend/api/batchRemove',jsonData).then(function (_ref2) {
                            let data = _ref2.data;
                            if (data && data.code === 0) {
                                _this4.$message({
                                    message: '操作成功',
                                    type: 'success',
                                    duration: 1500,
                                    onClose:function onClose(){
                                        _this4.getDataList2();
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
            //單個删除
            deleteHandle2: function deleteHandle2(id) {
                let _this5 = this;
                this.$confirm('确定对选中的进行删除操作?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    _this5.$http({
                        url: '/openapi-backend/api/remove',
                        method: 'post',
                        params: _this5.$http.adornParams({'id': id})
                    }).then(function (_ref2) {
                        let data = _ref2.data;
                        if (data && data.code ===0) {
                            _this5.$message({
                                message: '操作成功',
                                type: 'success',
                                duration: 1500,
                                onClose:function onClose(){
                                    _this5.getDataList2();
                                }
                            });
                        }else {
                            _this5.$message({
                                message: data.msg,
                                type: 'repeat',
                                duration: 1000,
                            });
                        }
                    });
                });
            },
            //api注册
            addApi:function addApi(row) {
                let _this6 = this;
                _this6.currentGroup=row.service
                _this6.apiId = row.id
                this.$confirm('确定要注册该服务吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    _this6.$http({
                        url: '/openapi-backend/openApi/register/',
                        method: 'post',
                        params: _this6.$http.adornParams({'serviceId': _this6.apiId})
                    }).then(function (_ref2) {
                        let data = _ref2.data;
                        if (data.code === 0) {
                            _this6.$message({
                                message: '操作成功',
                                type: 'success',
                                duration: 1500,
                                onClose:function onClose(){
                                    _this6.getDataList2();
                                    _this6.getDataList1();
                                }
                            });
                        }else {
                            _this6.$message({
                                message: data.msg,
                                type: 'repeat',
                                duration: 1000,
                            });
                        }
                    });
                });
            },
            //删除api服务
            deleteServiceHandle: function deleteServiceHandle(id) {
                let _this7 = this;
                this.$confirm('确定对选中的进行删除操作?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    _this7.$http({
                        url: '/openapi-backend/openApi/serviceRemove',
                        method: 'post',
                        params: _this7.$http.adornParams({'serviceId': id})
                    }).then(function (_ref2) {
                        let data = _ref2.data;
                        if (data && data.code ===0) {
                            _this7.apiId = "",
                            _this7.$message({
                                message: '操作成功',
                                type: 'success',
                                duration: 1500,
                                onClose:function onClose(){
                                    _this7.getDataList1();
                                    _this7.getDataList2();
                                }
                            });
                        }else {
                            _this7.$message({
                                message: data.msg,
                                type: 'repeat',
                                duration: 1000,
                            });
                        }
                    });
                });
            },
            //点击左表格某一行方法
            dataList:function dataList(row){
                this.apiId = row.id
                this.currentGroup=row.service
                this.getDataList2()
            },
            currentGroupShow:function currentGroupShow() {
                this.currentGroup="所有分组";
                this.apiId="";
                this.getDataList1();
                this.getDataList2();
            }
        },
        template: /*html*/`
 <div class="mod-config" style="overflow: hidden;width: 100%;">
    <div style="float: left;width: 32%;margin-top: 63px;">
        <el-table
          :data="dataList1"
          border
          size="mini"
          @row-click="dataList"
          v-loading="dataListLoading1"
          style="width: 100%;">
          <el-table-column
            prop="id"
            header-align="center"
            min-width="15%"
            align="center"
            label="id">
          </el-table-column>
          <el-table-column
            prop="service"
            header-align="center"
            min-width="45%"
            align="center"
            label="service">
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            min-width="25%"
            align="center"
            label="API注册">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="addApi(scope.row)">
                    <span v-if="scope.row.available">更新</span>
                    <span v-else>新增</span>
              </el-button>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            min-width="15%"
            align="center"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="deleteServiceHandle(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="sizeChangeHandle1"
          @current-change="currentChangeHandle1"
          :current-page="pageIndex1"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize1"
          small
          :total="totalPage1"
          layout="total, sizes, prev, pager, next, jumper"
          :pager-count="3"
          style="text-align: center;margin: 20px 0 10px 0;">
        </el-pagination>
    </div>
    
  <div style="float: right; width: 67%;">
        <div style="margin-bottom: 5px" :inline="true">
            <el-button type="danger" @click="deleteHandles2()" :disabled="dataListSelections.length <= 0">删除</el-button>
            <el-button type="primary" @click="currentGroupShow()">当前服务分组：{{currentGroup}}</el-button>
            <el-form :inline="true" :model="dataForm" style="float:right;">
                 <el-form-item>
                    <el-input v-model="dataForm.path" placeholder="请输入路径" clearable></el-input>
                 </el-form-item>
                 <el-form-item>
                    <el-button @click="getData()">查询</el-button>
                 </el-form-item>
            </el-form>
        </div>
        <el-table
          :data="dataList2"
          border
          size="mini"
          v-loading="dataListLoading2"
          @selection-change="selectionChangeHandle"
          style="width: 100%;">
          <el-table-column
            type="selection"
            header-align="center"
            align="center"
            min-width="5%">
          </el-table-column>
          <el-table-column
            prop="id"
            header-align="center"
            min-width="9%"
            align="center"
            label="id">
          </el-table-column>
          <el-table-column
            prop="name"
            header-align="center"
            min-width="20%"
            align="center"
            label="api名称">
          </el-table-column>
          <el-table-column
            prop="path"
            header-align="center"
            min-width="25%"
            align="center"
            label="路径">
          </el-table-column>
          <el-table-column
            prop="groupId"
            header-align="center"
            min-width="7%"
            align="center"
            label="组">
          </el-table-column>
          <el-table-column
            prop="method"
            header-align="center"
            min-width="9%"
            align="center"
            label="方法">
          </el-table-column>
          <el-table-column
            prop="version"
            header-align="center"
            min-width="7%"
            align="center"
            label="版本">
          </el-table-column>
          <el-table-column
            prop="available"
            :resizable="false"
            header-align="center"
            min-width="7%"
            align="center"
            :formatter="formatIsAvailable"
            label="可用性">
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            min-width="13%"
            align="center"
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="updateApi(scope.row)">编辑</el-button>
              <el-button type="text" size="small" @click="deleteHandle2(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="sizeChangeHandle2"
          @current-change="currentChangeHandle2"
          :current-page="pageIndex2"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize2"
          :total="totalPage2"
          layout="total, sizes, prev, pager, next, jumper"
          style="text-align: center;margin-top: 20px">
        </el-pagination>
  </div>
    <!-- 弹窗, 新增 / 修改 -->
    <update-api v-if="UpdateApiVisible" ref="updateApi" @refreshDataList="getDataList2"></update-api>
  </div>
`
    });
});
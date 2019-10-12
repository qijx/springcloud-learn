lyfdefine(['addOrUpdateApp','addApiPower'],function (addOrUpdateApp,addApiPower) {
    return ({
        data: function data() {
            return {
                dataForm: {
                    key: ''
                },
                dataList: [],
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                addOrUpdateVisible: true,
                addApiPowerVisible: true,
            };
        },
        components: {
            addOrUpdateApp : addOrUpdateApp,
            addApiPower:addApiPower,
        },
        mounted(){
            this.getDataList();
        },
        methods: {
            // 获取数据列表
            getDataList: function getDataList() {
                let _this = this;
                _this.dataListLoading = true;
                this.$http({
                    url: '/openapi-backend/openApp/list',
                    method: 'post',
                    data: _this.$http.adornData({
                        'pageNum': this.pageIndex,
                        'pageSize': this.pageSize
                    })
                }).then(function (_ref) {
                    let data = _ref.data;
                    if (data.rows) {
                        _this.dataList = data.rows;
                        _this.totalPage = data.total;
                    } else {
                        _this.dataList = [];
                        _this.totalPage = 0;
                    }
                    _this.dataListLoading = false;
                });
            },
            //是否白名单
            formatIsHasWhiteList(row, column, cellValue) {
                if (cellValue === true) {
                    return '是';
                } else {
                    return '否';
                }
            },
            //商户类型
            formatIssecretType(row, column, cellValue) {
                if (cellValue === false) {
                    return '内部';
                } else {
                    return '外部';
                }
            },
            //可用性
            formatIsAvailable(row, column, cellValue) {
                if (cellValue === false) {
                    return '否';
                } else {
                    return '是';
                }
            },

            // 每页数
            sizeChangeHandle: function sizeChangeHandle(val) {
                this.pageSize = val;
                this.getDataList();
            },

            // 当前页
            currentChangeHandle: function currentChangeHandle(val) {
                this.pageIndex = val;
                this.getDataList();
            },

            // 多选
            selectionChangeHandle: function selectionChangeHandle(val) {
                this.dataListSelections = val;
            },

            // 新增或修改
            addOrUpdateAppHandle: function addOrUpdateAppHandle(row) {
                let _this1 = this;
                _this1.addOrUpdateVisible = true;
                this.$nextTick( function () {
                    _this1.$refs.addOrUpdateApp.init(row);
                });
            },
            //api权限
            addApiPower: function addApiPower(id) {
                let _this2 = this;
                _this2.addApiPowerVisible = true;
                this.$nextTick( function () {
                    _this2.$refs.addApiPower.init(id);
                });
            },
            // 批量删除
            deleteHandles: function deleteHandles() {
                let _this3 = this;
                let ids = this.dataListSelections.map(function (item) {
                    return item.appId;
                });
                let jsonData = JSON.stringify(ids);
                if (ids.length>0){
                    this.$confirm('确定要删除选中的记录?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        _this3.$http.post('/openapi-backend/openApp/batchRemove',jsonData).then(function (_ref2) {
                            let data = _ref2.data;
                            if (data && data.code === 0) {
                                _this3.$message({
                                    message: '操作成功',
                                    type: 'success',
                                    duration: 1500,
                                    onClose:function onClose(){
                                        _this3.getDataList();
                                    }
                                });
                            }else {
                                _this3.$message({
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
            deleteHandle: function deleteHandle(id) {
                let _this3 = this;
                this.$confirm('确定要删除选中的记录?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    _this3.$http({
                        url: '/openapi-backend/openApp/remove',
                        method: 'POST',
                        params: _this3.$http.adornParams({'appId': id})
                    }).then(function (_ref2) {
                        let data = _ref2.data;
                        if (data && data.code === 0) {
                            _this3.$message({
                                message: '操作成功',
                                type: 'success',
                                duration: 1500,
                                onClose:function onClose(){
                                    _this3.getDataList();
                                }
                            });
                        }else {
                            _this3.$message({
                                message: data.msg,
                                type: 'repeat',
                                duration: 1000,
                            });
                        }
                    });
                });
            }
        },
        template: /*html*/`
 <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-button type="primary" @click="addOrUpdateAppHandle()">添加</el-button>
        <el-button type="danger" @click="deleteHandles()" :disabled="dataListSelections.length <= 0">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      size="mini"
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="appId"
        header-align="center"
        align="center"
        label="商户号">
      </el-table-column>
      <el-table-column
        prop="appName"
        header-align="center"
        align="center"
        label="商户名称">
      </el-table-column>
      <el-table-column
        prop="appType"
        header-align="center"
        align="center"
        :formatter="formatIssecretType"
        label="商户类型">
      </el-table-column>
      <el-table-column
        prop="secretKey"
        header-align="center"
        align="center"
        label="密钥">
      </el-table-column>
      <el-table-column
        prop="secretType"
        :resizable="false"
        header-align="center"
        align="center"
        label="密钥类型">
      </el-table-column>
      <el-table-column
        prop="hasWhiteList"
        :resizable="false"
        header-align="center"
        align="center"
        :formatter="formatIsHasWhiteList"
        label="是否有白名单">
      </el-table-column>
      <el-table-column
        prop="signVersion"
        :resizable="false"
        header-align="center"
        align="center"
        label="签名版本">
      </el-table-column>
      <el-table-column
        prop="available"
        :resizable="false"
        header-align="center"
        align="center"
        :formatter="formatIsAvailable"
        label="可用性">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateAppHandle(scope.row)">编辑</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.appId)">删除</el-button>
          <el-button type="text" size="small" @click="addApiPower(scope.row.appId)">api权限</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper"
      style="text-align: center;margin-top: 20px">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update-app v-if="addOrUpdateVisible" ref="addOrUpdateApp" @refreshDataList="getDataList"></add-or-update-app>
    <add-api-power v-if="addApiPowerVisible" ref="addApiPower" @refreshDataList="getDataList"></add-api-power>
  </div>
`
    });
});
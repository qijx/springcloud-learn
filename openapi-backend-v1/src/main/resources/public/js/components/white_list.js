lyfdefine(['addOrUpdateWhiteList'],function (addOrUpdateWhiteList) {
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
                addOrUpdateVisible: true
            };
        },
        components: {
            addOrUpdateWhiteList : addOrUpdateWhiteList
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
                    url: '/openapi-backend/whiteList/list',
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
            //状态
            formatIsAvailable(row, column, cellValue) {
                if (cellValue === false) {
                    return '禁用';
                } else {
                    return '启用';
                }
            },
            // 每页条数
            sizeChangeHandle: function sizeChangeHandle(val) {
                this.pageSize = val;
                this.pageIndex = 1;
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
            getZf(num){
                if(parseInt(num) < 10){
                    num = '0'+num;
                }
                return num;
            },
            //时间转换
            formatDate (row, column, cellValue) {
                let date = new Date(Date.parse(cellValue));
                let year = date.getFullYear();
                let month = date.getMonth()+1;
                let day = date.getDate();
                let hours = date.getHours();
                let minutes = date.getMinutes();
                let seconds = date.getSeconds();
                return year + "-" + this.getZf(month) + "-" + this.getZf(day) + " " + this.getZf(hours) + ":" + this.getZf(minutes) + ":" + this.getZf(seconds);
            },

            // 新增或修改
            addOrUpdateWhiteListHandle: function addOrUpdateWhiteListHandle(row) {
                let _this1 = this;
                this.$nextTick( function () {
                    _this1.$refs.addOrUpdateWhiteList.init(row);
                });
            },
            // 批量删除
            deleteHandles: function deleteHandles() {
                let _this3 = this;
                let ids = this.dataListSelections.map(function (item) {
                    return item.id;
                });
                let jsonData = JSON.stringify(ids);
                if (ids.length>0){
                    this.$confirm('确定对[id=' + ids.join(',') + ']进行批量删除操作?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        _this3.$http.post('/openapi-backend/whiteList/batchRemove',jsonData).then(function (_ref2) {
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
                this.$confirm('确定对[id=' + id + ']进行批量删除操作?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    _this3.$http({
                        url: '/openapi-backend/whiteList/remove',
                        method: 'delete',
                        params: _this3.$http.adornParams({'id': id})
                    }).then(function (_ref2) {
                        let data = _ref2.data;
                        if (data && data.code ===0) {
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
        <el-button type="primary" @click="addOrUpdateWhiteListHandle()">添加</el-button>
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
        prop="id"
        header-align="center"
        align="center"
        label="ID">
      </el-table-column>
      <el-table-column
        prop="uri"
        header-align="center"
        align="center"
        label="URL路径">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        :formatter="formatDate"
        label="创建时间">
      </el-table-column>
      <el-table-column
        prop="createUser"
        header-align="center"
        align="center"
        label="创建者">
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
          <el-button type="text" size="small" @click="addOrUpdateWhiteListHandle(scope.row)">编辑</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
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
    <add-or-update-white-list v-if="addOrUpdateVisible" ref="addOrUpdateWhiteList" @refreshDataList="getDataList"></add-or-update-white-list>
  </div>
`
    });
});
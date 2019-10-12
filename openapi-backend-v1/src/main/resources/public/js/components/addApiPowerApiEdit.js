lyfdefine(function(){
    return ({
        data: function data() {
            return {
                visible: false,
                available:true,
                dataForm: {
                    selectedFields:""
                },
                dataList:[],
                dataRule: {
                    selectedFields: [{ required: true, message: '请填写内容', trigger: 'blur' }],
                },
            };
        },
        methods: {
            init: function init(row) {
                var _this = this;
                _this.visible = true;
                this.$nextTick(function () {
                    if (row.id) {
                        _this.dataForm=row;
                    }
                });
            },

            // 字段修改提交
            dataFormSubmit: function dataFormSubmit() {
                var _this2 = this;
                console.log("字段名" ,_this2.dataForm.selectedFields)
                _this2.$http({
                    url: '/openapi-backend/appapi/update',
                    method: 'PATCH',
                    data: _this2.$http.adornData({
                        "id": _this2.dataForm.id,
                        "selectField": _this2.dataForm.selectedFields,
                    })
                }).then(function (_ref2) {
                    var data = _ref2.data;
                    if (data.code == 0) {
                        _this2.$message({
                            message: '操作成功',
                            type: 'success',
                            duration: 1000,
                            onClose: function onClose() {
                                _this2.visible = false;
                                _this2.$emit('refreshDataList');
                            }
                        });
                    } else {
                        _this2.$message({
                            message: data.msg,
                            type: 'repeat',
                            duration: 1000,
                        });
                    }
                });
            }
        },
        template: `
  <el-dialog
    :title="'信息'"
    :close-on-click-modal="false"
    :visible.sync="visible" width="30%"
    append-to-body>
      <el-form :model="dataForm">
            <el-form-item label="" prop="selectedFields">
                <el-input v-model="dataForm.selectedFields" autocomplete="off" placeholder="用','隔开，不填则返回所有字段"></el-input>
            </el-form-item>
       </el-form>
      <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dataFormSubmit()">确 定</el-button>
            <el-button @click="visible = false">取 消</el-button>   
      </div>
  </el-dialog>
`
    });
});
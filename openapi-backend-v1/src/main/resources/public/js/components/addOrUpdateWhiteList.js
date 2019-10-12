lyfdefine(function(){
    return ({
        data: function data() {
            return {
                visible: false,
                available:true,
                dataForm: {
                    id: 0,
                    uri:"",
                    available: true,
                },
                dataRule: {
                    uri: [{ required: true, message: '请填写url', trigger: 'blur' }],
                },
                isDisabled:false
            };
        },
        methods: {
            init: function init(row) {
                    var _this = this;
                    if (row == undefined){
                        this.dataForm.id = 0;
                    } else {
                        this.dataForm.id = row.id;
                    }
                    _this.visible = true;
                    this.$nextTick(function () {
                        _this.$refs['dataForm'].resetFields();
                        if (_this.dataForm.id) {
                            _this.dataForm.uri=row.uri;
                            _this.dataForm.available=row.available;
                        }
                    });
                },
            // 表单提交
            dataFormSubmit: function dataFormSubmit() {
                var _this2 = this;
                _this2.isDisabled=true;
                this.$refs['dataForm'].validate(function (valid) {
                    if (valid) {
                        _this2.$http({
                            url: '/openapi-backend/whiteList/' + (!_this2.dataForm.id ? 'save' : 'update'),
                            method: 'POST',
                            data: _this2.$http.adornData({
                                "id": _this2.dataForm.id || undefined,
                                "uri":_this2.dataForm.uri,
                                "available":_this2.dataForm.available
                            })
                        }).then(function (_ref2) {
                            var data = _ref2.data;
                            if (data.code == '0') {
                                _this2.$message({
                                    message: '操作成功',
                                    type: 'success',
                                    duration: 1000,
                                    onClose : function onClose() {
                                        _this2.visible = false;
                                        _this2.isDisabled = false;
                                        _this2.$emit('refreshDataList');
                                    }
                                });
                            }else {
                                _this2.isDisabled=false;
                                _this2.$message({
                                    message: data.msg,
                                    type: 'repeat',
                                    duration: 1000,
                                });
                            }
                        });
                    } else {
                        _this2.isDisabled=false;
                    }
                });
            }
        },
    template: `
      <el-dialog
        :title="!dataForm.id ? '新增' : '编辑'"
        :close-on-click-modal="false"
        :visible.sync="visible" width="40%">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="100px">
            <el-form-item label="url路径:" prop="uri">
              <el-input v-model="dataForm.uri" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item style="margin-top: -5px">          
                <lable style="font-size: 12px;font-weight: bold">PathVariable动态参数用'\\w+'代替，如：/user-info/user/v1/test/\\w+</lable>
            </el-form-item>
            <el-form-item label="可用性:" prop="describe">
                <el-switch
                  v-model="dataForm.available"
                  active-color="#13ce66"
                  inactive-color="#ff4949">
                </el-switch>
            </el-form-item>
        </el-form>
            <el-button type="primary" @click="dataFormSubmit()" :disabled="isDisabled" style="margin-left: 45%">提交</el-button>
      </el-dialog>
`
    });
});
lyfdefine(function(){
    return ({
        data: function data() {
            return {
                visible: false,
                available:true,
                dataForm: {
                    available: true,
                    createTime: "",
                    createUser: "",
                    group: "",
                    groupId:"",
                    id:"",
                    method: "",
                    name: "",
                    path: "",
                    service: "",
                    serviceId:"",
                    updateTime: "",
                    updateUser: "",
                    version: "",
                },
                dataRule: {
                    name: [{ required: true, message: '请填写api名称', trigger: 'blur' }],
                },
                isDisabled:false
            };
        },
        methods: {
            init: function init(row) {
                var _this = this;
                _this.visible = true;
                this.$nextTick(function () {
                    _this.$refs['dataForm'].resetFields();
                    if (row.id) {
                        _this.dataForm.groupId= row.groupId,
                        _this.dataForm.id= row.id,
                        _this.dataForm.method= row.method,
                        _this.dataForm.name= row.name,
                        _this.dataForm.path= row.path,
                        _this.dataForm.version= row.version
                    }
                });
            },
            // 编辑提交
            updataSubmit: function updataSubmit() {
                var _this2 = this;
                _this2.isDisabled=true;
                this.$refs['dataForm'].validate(function (valid) {
                    if (valid) {
                        _this2.$http({
                            url: '/openapi-backend/api/update',
                            method: 'POST',
                            data: _this2.$http.adornData({
                                id:_this2.dataForm.id,
                                name:_this2.dataForm.name,
                                path:_this2.dataForm.path,
                                groupId:_this2.dataForm.groupId,
                                method:_this2.dataForm.method,
                                version:_this2.dataForm.version,
                            })
                        }).then(function (_ref2) {
                            var data = _ref2.data;
                            if (data.code == 0) {
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
                                _this2.$message({
                                    message: data.msg,
                                    type: 'repeat',
                                    duration: 1000,
                                });
                            }
                        });
                    }else {
                        _this2.isDisabled=false;
                    }
                });
            }
        },
        template: `
      <el-dialog
        :title="'编辑'"
        :close-on-click-modal="false"
        :visible.sync="visible" width="40%">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="100px">
            <!--api验证-->
            <el-form-item label="api名称:" prop="name">
              <el-input v-model="dataForm.name" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="路径:" prop="path">
              <el-input v-model="dataForm.path" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="组:" prop="groupId">
              <el-input v-model="dataForm.groupId" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="方法:" prop="method">
              <el-input v-model="dataForm.method" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="版本:" prop="version">
              <el-input v-model="dataForm.version" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="可用性:" prop="available">
                <el-switch
                  v-model="dataForm.available"
                  active-color="#13ce66"
                  inactive-color="#ff4949">
                </el-switch>
            </el-form-item>
        </el-form>
                <el-button type="primary" @click="updataSubmit()" :disabled="isDisabled" style="margin-left: 45%">提交</el-button>
      </el-dialog>

`
    });
});
lyfdefine(function(){
    return ({
        data: function data() {
            var validatePass3 = (rule, value, callback) => {
                if(this.dataForm.hasWhiteList){
                    if(value == null || value == ''){
                        callback(new Error('请用英文字符“，”隔开'));
                    }else{
                        callback()
                    }
                }else{
                    callback()
                }
            };
            return {
                visible: false,
                available:true,
                dataForm: {
                    appId:"",
                    appName:"",
                    appType:false,
                    //密钥类型是select
                    secretType:"DEFAULT",
                    secretKey:"",
                    hasWhiteList:false,
                    iptables:"",
                    available: true,
                    signVersion:"",
                },
                dataRule:{
                    secretKey: [
                        { min: 32, max: 256, message: '长度在 32 到 256 个字符', trigger: 'blur' }
                    ],
                    appName: [{ required: true, message: ' 请输入商户名称', trigger: 'blur' }],
                    iptables:[{ validator:validatePass3 }],
                },
                isDisabled:false
            };
        },

        methods: {
            init: function init(row) {
                let _this1 = this;
                if (row == undefined){
                    this.dataForm.appId = 0;
                } else {
                    this.dataForm.appId = row.appId;
                    _this1.ipWhiteList()
                }
                _this1.visible = true;
                this.$nextTick(function () {
                    _this1.$refs['dataForm'].resetFields();
                    if (_this1.dataForm.appId) {
                        _this1.dataForm.appId=row.appId,
                        _this1.dataForm.appName=row.appName,
                         _this1.dataForm.appType=row.appType,
                         _this1.dataForm.secretType=row.secretType,
                         _this1.dataForm.secretKey=row.secretKey,
                         _this1.dataForm.hasWhiteList=row.hasWhiteList,
                         _this1.dataForm.available=row.available,
                         _this1.dataForm.signVersion=row.signVersion+"",
                         _this1.dataForm.available=row.available
                    }
                });
            },
            //ip白名单
            ipWhiteList:function ipWhiteList(){
                let _this = this;
                _this.dataListLoading1 = true;
                this.$http({
                    url: '/openapi-backend/openApp/edit/'+_this.dataForm.appId,
                    method: 'get'
                }).then(function (_ref) {
                    let data = _ref.data;
                    if (data.code == 0) {
                        _this.dataForm.iptables = data.data.ip;
                    } else {
                        _this.dataForm.iptables = [];
                    }
                    _this.dataListLoading1 = false;
                });

            },
            // 表单提交
            dataFormSubmit: function dataFormSubmit() {
                var _this2 = this;
                _this2.isDisabled=true;
                _this2.$refs['dataForm'].validate(function (valid) {
                    if (valid) {
                        _this2.$http({
                            url: '/openapi-backend/openApp/' + (!_this2.dataForm.appId ? 'save' : 'update'),
                            method: 'POST',
                            data: _this2.$http.adornData({
                                "appId": _this2.dataForm.appId || undefined,
                                "appName":_this2.dataForm.appName,
                                "appType":_this2.dataForm.appType,
                                "secretType":_this2.dataForm.secretType,
                                "secretKey":_this2.dataForm.secretKey,
                                "hasWhiteList":_this2.dataForm.hasWhiteList,
                                "iptables":_this2.dataForm.iptables,
                                "available":_this2.dataForm.available,
                                "signVersion":_this2.dataForm.signVersion,
                                "available":_this2.dataForm.available
                            })
                        }).then(function (_ref2) {
                            let data = _ref2.data;
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
    :title="!dataForm.appId ? '新增' : '编辑'"
    :close-on-click-modal="false"
    :visible.sync="visible" width="40%">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="115px">
        <el-form-item label="商户名称:" prop="appName">
          <el-input v-model="dataForm.appName" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="是否外部商户:" prop="appType">
            <el-switch
              v-model="dataForm.appType"
              active-color="#13ce66"
              inactive-color="#ff4949">
            </el-switch>
        </el-form-item>
         <el-form-item label="密钥类型" prop="secretType">
              <el-select v-model="dataForm.secretType" value-key="dataForm.secretType">
                <el-option value="RSA">RSA</el-option>
                <el-option value="DES">DES</el-option>
                <el-option value="DEFAULT">DEFAULT</el-option>
              </el-select> 
        </el-form-item>
        <div v-show="!dataForm.appId">
            <el-form-item label="密钥:" v-show="dataForm.secretType=='RSA' || dataForm.secretType=='DES'" prop="secretKey">
                <el-input v-model="dataForm.secretKey" style="width: 80%"></el-input>
            </el-form-item>
        </div>
        <div v-show="dataForm.appId">
            <el-form-item label="密钥:" v-show="dataForm.secretType=='RSA' || dataForm.secretType=='DES'" prop="secretKey">
                <el-input v-model="dataForm.secretKey" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="密钥:" v-show="dataForm.secretType=='DEFAULT'" prop="secretKey">
                <el-input v-model="dataForm.secretKey" :disabled="true" style="width: 80%"></el-input>
            </el-form-item>
        </div>
        <el-form-item label="签名版本:"  prop="signVersion">
            <el-radio v-model="dataForm.signVersion" label="1">1.0</el-radio>
            <el-radio v-model="dataForm.signVersion" label="2">2.0</el-radio>
        </el-form-item>
        <el-form-item label="是否有白名单:" prop="hasWhiteList" >
            <el-switch
              v-model="dataForm.hasWhiteList"
              active-color="#13ce66"
              validate-event
              inactive-color="#ff4949">
            </el-switch>
        </el-form-item>
        <el-form-item label="IP白名单:" v-show="dataForm.hasWhiteList" prop="iptables" >
            <el-input v-model="dataForm.iptables" style="width: 80%" placeholder="用英文字符','隔开"></el-input>
        </el-form-item>
        <el-form-item label="可用性:" prop="available">
            <el-switch
              v-model="dataForm.available"
              active-color="#13ce66"
              inactive-color="#ff4949">
            </el-switch>
        </el-form-item>
    </el-form>
            <el-button type="primary" @click="dataFormSubmit()" :disabled="isDisabled" style="margin-left: 35%">提交</el-button>
  </el-dialog>

`
    });
});
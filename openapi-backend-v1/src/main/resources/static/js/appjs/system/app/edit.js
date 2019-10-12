$().ready(function() {
	validateRule();

    if ('DEFAULT' == $('#secretType').val()) {
        $('#secretKey').attr('readonly', 'readonly');
    }
    $('#secretType').change(function (e) {
        if ('DEFAULT' == this.value){
        	$('#secretKey').attr('readonly','readonly');
        } else {
            $('#secretKey').removeAttrs('readonly');
        }
    })


    if ($('#hasWhiteList').is(':checked')){
        $('#hasWhiteList').parent().parent().parent().next().show();
    } else{
        $('#hasWhiteList').parent().parent().parent().next().hide();
    }

    $('#hasWhiteList').change(function (e) {
        if ($(this).is(':checked')){
            $(this).parent().parent().parent().next().show(500);
        } else{
            $(this).parent().parent().parent().next().hide(500);
        }
    })

});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/openApp/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
        rules : {
            appName : {
                required : true,
                maxlength:16
            },
            secretKey : {
                rangelength : [32,256]
            },
            iptables : {
                required : true
            }

        },
        messages : {
            appName : {
                required : icon + "请输入商户名称",
                maxlength : icon+"名称长度少于{0}位"
            },
            secretKey : {
                rangelength : icon+"长度必须介于{0}和{1}之间"
            },
            iptables : {
                required : icon + "请输入IP，用‘,’隔开"
            }
        }
	})
}
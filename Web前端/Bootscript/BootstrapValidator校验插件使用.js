/**
 * bootstrap 校验框架 -- Bootstrap Validator
 * 基本使用
 */

// 初始化
initValidator("FormId", xxx.validateAddFields);

// 设置校验的表单项
var xxx = {
  validateAddFields: {
    contractName: {
        validators: {
            notEmpty: {
                message: '合同名称不能为空'
            }
        }
    },
    totalMoney: {
        validators: {
            notEmpty: {
                message: '含税总金额不能为空'
            },
            regexp: {
                regexp: /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/,
                message:'含税总金额仅支持最多两位小数的数字'
            }
        }
    }
  }
}

// 校验
if (!this.validate()) {
    // 返回顶部
    window.scrollTo(0,0);
    return;
}

/**
 * 验证数据是否为空
 */
xxx.validate = function () {
    $('#contractForm').data("bootstrapValidator").resetForm();
    $('#contractForm').bootstrapValidator('validate');
    return $("#contractForm").data('bootstrapValidator').isValid();
};
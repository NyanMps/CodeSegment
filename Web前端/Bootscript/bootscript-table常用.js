/**
 * 提交添加(提交到父表格)
 */
ContractMarksInfoDlg.addSubmit = function() {
    // 校验
    if (!this.validate()) {
        return;
    }

    this.clearData();
    this.collectData();

    //提交信息
    var len  = window.parent.ContractMarks.table.btInstance.bootstrapTable('getData').length; // 可以直接使用选择器选择，然后调用 getData 的
    var index  = parseInt(window.parent.ContractMarks.table.btInstance.bootstrapTable('getData')[len - 1].marksNum);
    // 填充序号
    this.contractMarksInfoData['marksNum'] = (index + 1).toString();
    window.parent.ContractMarks.table.btInstance.bootstrapTable('insertRow', {
        index: index,
        row: this.contractMarksInfoData
    });
    Savor.success("操作成功!");
    ContractMarksInfoDlg.close();
};

// 更新一行
window.parent.ContractMarks.table.btInstance.bootstrapTable('updateRow', {
    index: index - 1,
    row: this.contractMarksInfoData
});

// 删除一行
// 注意： id 的类型请保持一致，否则删不掉；也就是确认 id 当时填充是什么类型，是数字还是字符串
ContractMarks.table.btInstance.bootstrapTable('remove', {
    field: 'marksNum',
    values: [id]
});


ContractMarks.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '里程碑序号', field: 'marksNum', visible: true, align: 'center', valign: 'middle', formatter:function (value,row,index) {
                return index + 1;
            }},
        {title: '创建人', field: 'crtUserName', visible: false, align: 'center', valign: 'middle'},
        {title: '更新日期', field: 'updTime', visible: false, align: 'center', valign: 'middle'},
        {title: '更新人ID', field: 'updUserId', visible: false, align: 'center', valign: 'middle'},
        {title: '更新人', field: 'updUserName', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'operate', events: operateMarksEvents, align: 'center', class:'w60', formatter: operateFormatter}
    ];
};

function operateFormatter(value, row, index) {
    return [
        '<button type="button" class="RoleOfdelete btn btn-danger  btn-sm">删除</button>'
    ].join('');
}

window.operateMarksEvents = {
    'click .RoleOfdelete': function (e, value, row, index) {
        ContractMarks.delete(row.marksNum);
    }
};

/**
 * 检查是否选中
 */
ContractMarks.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Savor.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ContractMarks.seItem = selected[0];
        return true;
    }
};

/**
 * 删除
 */
ContractMarks.delete = function (id) {
    parent.layer.confirm('确认删除该条信息吗？', {
        btn: ['确定', '取消'],
        shade: false //不显示遮罩
    }, function () {
        ContractMarks.table.btInstance.bootstrapTable('remove', {
            field: 'marksNum',
            values: [id]
        });
    });
};

/**
 * 加载完毕的回调
 * 与 onPostBody 区分，onPostBody 会调用多次
 * 相关链接：https://github.com/wenzhixin/bootstrap-table/issues/2920
 *    http://bootstrap-table.wenzhixin.net.cn/documentation/#events
 */
$('#table').bootstrapTable({
  onLoadSuccess: function() {
    console.log("初始化完成");
  }
});
$('#BillingApplyDetailTable').on('onLoadSuccess', function () {
  console.log("初始化完成");
});















// -------------------------------------------------------------------------------

// 引用地址：
//    关于使用server端分页，如何保存用户所有的复选框选择问题  https://github.com/wenzhixin/bootstrap-table/issues/917
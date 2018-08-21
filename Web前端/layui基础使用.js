/**
 * layui 弹层之间的传值，通过回调
 */
 
openChooseUser = function () {
    var index = layer.open({
        type: 2,
        title: '选择用户',
        area: ['1000px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Savor.ctxPath + '/mgr/userpop',
        btn: ['确定', '关闭'],
        yes: function (index) {
            // 获取弹层返回的值
            var res = window["layui-layer-iframe" + index].callbackdata();
            $('#signUser').val(res.mgrUser.account);
            layer.close(index);
        },
        cancel: function () {
            // 右上角关闭回调
        }
    });
    this.layerIndex = index;
};

var callbackdata = function () {
    if (MgrUser.check()) {
        var data = {
            mgrUser: MgrUser.seItem
        };
        return data;
    }
}


/**
 * 弹层的基本使用
 */

// 打开一个弹出层
xx.openAddContract = function () {
    var index = layer.open({
        type: 2,
        title: '添加合同管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Savor.ctxPath + '/contract/contract_add'
    });
    layer.full(index); // 设置默认最大化
    this.layerIndex = index;
};

 // 提示框
xx.delete = function () {
    if (this.check()) {
        parent.layer.confirm('确认删除该条信息吗？', {
            btn: ['确定', '取消'],
            shade: false //不显示遮罩
        }, function () {
            var ajax = new $ax(Savor.ctxPath + "/contract/delete/" + Contract.seItem.contractId, function (data) {
                Savor.success("删除成功!");
                Contract.table.refresh();
            }, function (data) {
                Savor.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.start();
        });
    }
};

// 加载中效果，屏蔽用户操作
var loadIndex = parent.layer.load(1, {
  shade: [0.1,'#fff'] //0.1 透明度的白色背景
});
// 关闭遮罩
parent.layer.close(loadIndex);
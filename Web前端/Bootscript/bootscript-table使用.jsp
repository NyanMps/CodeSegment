<%--
  Created by IntelliJ IDEA.
  User: 冰封承諾Andy
  Date: 2018/5/19
  Time: 9:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>员工列表</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/bootstrap-table.css" rel="stylesheet"/>

</head>
<body>
    
    <table id="tb"></table>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-table.js"></script>
    <script src="js/bootstrap-table-zh-CN.js"></script>

    <script>
        $(function () {
            queryData();
        });

        // tb = $().bootstrapTable({});
        // tb.bootstrapTable("refresh");
        // 服务器返回的 json 根必须有 total 和 rows
        var start = 0;
        function queryData() {
            $("#tb").bootstrapTable({
                url: "queryEmp.action",
                pagination: true, // 显示页码
                sidePagination: "server", // 真分页
                pageNumber: 1,
                pageSize: 4,
                pageList: [4,5,6],
                queryParamsType: "undefined", // 不使用 limit 作为参数，从哪开始
                queryParams: function(params){
                  var new_params = {
                        'page.pageNumber': params.pageNumber,
                        'page.pageSize': params.pageSize
                    };
                    // 组装条件
                    var name = $("#name").val();
                    var sid = $("#sid").val();
                    if (name != null || name.length !== 0) {
                        new_params.name = name;
                    }
                    if (sid != null || sid !== 0) {
                        new_params.sid = sid;
                    }
                    // pageNumber 和  pageSize
                    // console.log(params);
                    start = (params.pageNumber - 1) * params.pageSize;
                    return params;
                },
                columns: [{
                    title: "编号",
                    formatter: function (value, row, index) {
                        // console.log(row);
                        return start + index + 1;
                    }
                },{
                    field: "empName",
                    title: "员工名字"
                },{
                    field: "empCode",
                    title: "员工编号"
                },{
                    field: "organizationByOrgaId.orgaName",
                    title: "所在部门"
                },{
                    field: "sex",
                    title: "性别"
                },{
                    field: "age",
                    title: "年龄"
                },{
                    title: "操作",
                    formatter: function (val, row, index) {
                        var str = "<a href='javascript:void(0)' id='update'>更新</a>";
                        var str1 = "<a href='javascript:void(0)' id='del'>删除</a>";
                        return str + " | " + str1;
                    },
                    events: evn
                }]
            });
        }

        window.evn = {
            'click #update': function (event, value, row, index) {
                console.log(row);
            }
        }

        function export2Excel() {
            window.location.href = "export2Excel?name=" + $("input#namer").val();
        }

        function loaderCategory() {
            $.ajax({
                type: "get",
                url: "${pageContext.request.contextPath}/CategoryServlet?method=getAll2Json",
                dataType: "json",
                success: function (data) {
                    var $sel = $("select#caid");
                    var $modsel = $("select#category");
                    $.each(data, function (index, e) {
                        var opt1 = $("<option></option>");
                        var opt2 = $("<option></option>");
                        opt1.val(e.id).text(e.name);
                        opt2.val(e.id).text(e.name);
                        $sel.append(opt1);
                        $modsel.append(opt2);
                    });
                }
            })
        }


        function init(table,url,params,titles,hasCheckbox,toolbar) {  
        $(table).bootstrapTable({  
            url: url,                           //请求后台的URL（*）  
            method: 'post',                     //请求方式（*）  
            toolbar: toolbar,                   //工具按钮用哪个容器  
            striped: true,                      //是否显示行间隔色  
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）  
            pagination: true,                   //是否显示分页（*）  
            sortable: false,                    //是否启用排序  
            sortOrder: "asc",                   //排序方式  
            queryParams: queryParams,           //传递参数（*），这里应该返回一个object，即形如{param1:val1,param2:val2}  
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）  
            pageNumber:1,                       //初始化加载第一页，默认第一页  
            pageSize: 20,                       //每页的记录行数（*）  
            pageList: [20, 50, 100],            //可供选择的每页的行数（*）  
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大  
            strictSearch: true,  
            showColumns: true,                  //是否显示所有的列  
            showRefresh: true,                  //是否显示刷新按钮  
            minimumCountColumns: 2,             //最少允许的列数  
            clickToSelect: true,                //是否启用点击选中行  
            //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度  
            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列  
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮  
            cardView: false,                    //是否显示详细视图  
            detailView: false,                  //是否显示父子表  
  
            columns: createCols(params,titles,hasCheckbox),  
            data: [{  
                id: 1,  
                name: 'Item 1',  
                price: '$1'  
            }, {  
                id: 2,  
                name: 'Item 2',  
                price: '$2'  
            }]  
        });  
    </script>
</body>
</html>

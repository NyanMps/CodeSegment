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
    <title>首页</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/bootstrap-treeview.min.css">

</head>
<body>
    <div class="page-header" style="margin: 0;">
        <%--<img src="image/head.gif" style="width: 100%;height: 100px;">--%>
        <div style="width: 100%;height: 100px;background-image: url('image/head.gif');background-repeat: repeat-x;"></div>
    </div>

    <%--导航--%>
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <!-- 第一部分，log 展示 -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <%-- 响应式的三道杠 --%>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">员工管理系统</a>
            </div>

            <!-- 第二部分，按钮菜单 -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="welcome.jsp" target="et">首页</a></li>
                </ul>

                <%--右侧菜单--%>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">更多 <span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#">注册</a></li>
                            <%--分割线--%>
                            <li class="divider"></li>
                            <li><a href="#">注销</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

    <%--主要内容部分, 容器-铺满全屏，否则居中显示--%>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <%--左侧菜单--%>
                <div id="tree"></div>
            </div>
            <div class="col-md-10">
                <iframe src="welcome.jsp" scrolling="no" frameborder="0" width="100%" height="1000px" name="et">

                </iframe>
            </div>
        </div>
    </div>


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-treeview.min.js"></script>

    <script>
        $(function () {

            $.post('queryMenu.action','',function (data) {
                $("#tree").treeview({
                    data: data,
                    enableLinks: true
                });
            },'json');

            /*var defultData = [{
                text: "员工管理系统",
                href: "javascript:void(0)",
                tags: ['1'],
                nodes: [{
                    text: "添加员工",
                    href: "addemp.jsp",
                    tags:['0']
                }]
            }];*/

        });

        function change(e) {
            $("div#left a").removeClass("active");
            $(e).addClass("active");
        }
    </script>
</body>
</html>

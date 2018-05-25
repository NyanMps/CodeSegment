// 拼接表格，进行分页
// 第一个参数第几页；第二个参数附加的条件查询的参数
function show(page, para) {
  $.ajax({
    type: "get",
    url: "${pageContext.request.contextPath}/BookServlet?method=getListPage&pageNumber=" + page + para,
    dataType: "json",
    success: function (data) {
      // 只保留表头
      $("table tr:not(:first)").remove();
      var table = $("table");
      $.each(data.rows, function (index, e) {
        var tr = $("<tr></tr>");
        // 计算编号，start 是此页的起始位置（limit 的第一个参数）
        var num = data.start + (index + 1);
        tr.append($("<td><input type=\"checkbox\" value='" + e.id + "'></td>"));
        tr.append($("<td>" + num + "</td>"));
        tr.append($("<td>" + e.name + "</td>"));
        tr.append($("<td>" + e.price + "</td>"));
        tr.append($("<td>" + e.author + "</td>"));
        tr.append($("<td>" + formatDate(e.publishdate.time) + "</td>"));
        tr.append($("<td>" + e.category.name + "</td>"));
        tr.append($("<td>" + (e.status == 0 ? '下架' : '上架') + "</td>"));
        btn = $("<td><button type='button' class='btn btn-primary' onclick=\"queryBookInfo(event, '" + e.id + "')\">显示详情</button></td>");
        tr.append(btn);
        tr.attr("onclick", "trFocus(this)");
        table.append(tr);
      });
      next = data.next;
      pre = data.pre;
      count = data.pageCount;
      currentPage = data.pageNumber;
      table.append($('<tr><td colspan="9">总记录：' + data.total + '条；共' + count
          + '页；当前第' + currentPage + '页</td></tr>'));
      flushBtn();
    }
  })
}

var next = 1;
var pre = 1;
var count = 1;
var currentPage = 1;
var para_g = "";

// 四个按钮的点击事件
function change(flag) {
  switch (flag) {
    case 'first':
      show(1, para_g);
      break;
    case 'pre':
      show(pre, para_g);
      break;
    case 'next':
      show(next, para_g);
      break;
    case 'last':
      show(count, para_g);
      break;
    default:
      show(1, para_g);
  }
}

// 刷新按钮的状态
function flushBtn() {
  var btns = $('body>button:contains("页")');
  btns.removeAttr("disabled");
  if (currentPage == 1) {
    btns[0].disabled = true;
    btns[1].disabled = true;
  } else if (currentPage == count) {
    btns[2].disabled = true;
    btns[3].disabled = true;
  }
}

// 多选框-全选（checkbox onclick="selectAll(this.checked)"）
function selectAll(flag) {
  if (flag) {
    // jq 1.x 这里使用 Attr 会有 bug
    $(':checkbox').prop('checked', 'checked');
  } else {
    $(':checkbox').removeAttr('checked');
  }
}

// ajax 删除选中的项
function delSelect() {
  var para = [];
  var secs = $('table td input:checked');
  if (secs.length === 0) {
    alert('您没选择任何项！');
    return;
  }

  $.each(secs, function (index, e) {
    para.push('id=' + e.value);
    para.push('&');
  });

  para.pop();
  para = para.join('').toString();

  $.ajax({
    type: "post",
    url: "${pageContext.request.contextPath}/BookServlet",
    data: "method=delSome&" + para,
    success: function (data) {
      show(currentPage, para_g);
      selectAll(false);
    }
  })
}

// 点击每行时选择这行的多选框，注意事件冒泡
function trFocus(e) {
  var c = $(e).find('input');
  if (c[0].checked) {
    c.removeAttr('checked');
  } else {
    c.prop('checked', 'checked');
  }
}


function queryBookInfo(e, bid) {
  // 取消事件冒泡
  if (e != null){
    e.stopPropagation();
  }
  
  $.ajax({
    type: "post",
    url: "BookServlet",
    data: "method=queryBookById&id=" + bid,
    dataType: "json",
    success: function (data) {
      bookid = data.id;
      $("#name").val(data.name);
      $("#author").val(data.author);
      $("#price").val(data.price);
      $("#category").val(data.categoryid);
      $("#status").val(data.status);
      $("#date").val(formatDate(data.publishdate.time));
      // onclick 字符串拼接的话，并且要传参的话，必须要使用这种形式："onclick=\"setFM('" + e.id + "')\"
      // onclick 后面的一定是双引号，因为传参是字符串必须用单引号，前面就只能用双引了，必须转义

      // ... 显示模态框
      $("#myModal").modal("show");
    }
  })
}

// 日期格式化
function formatDate(time) {
  var now = new Date(time);
  var year = now.getFullYear();
  var month = ("0" + (now.getMonth() + 1)).slice(-2);
  var date = ("0" + now.getDate()).slice(-2);
  return year + "-" + month + "-" + date;
}
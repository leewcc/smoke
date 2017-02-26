<%--
  Created by IntelliJ IDEA.
  User: CHEN
  Date: 2016/12/1
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>老师的接口测试</title>
</head>
<body>
<script src="https://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
<script>
  <!--增加老师测试-->
  function addTeacher() {
    var data = {"userName": "吴小聪"};
    $.ajax({
      type: 'post',
      url: '/smoke/teacher',
      dataType: 'json',
      contentType: 'application/json',
      data: JSON.stringify(data),
      success: function (data) {
        alert(data);
      }
    });
  };
  function deleteTeacher() {
    var data = {"userName": "吴小聪"};
    $.ajax({
      type: 'delete',
      url: '/smoke/teacher/11',
      dataType: 'json',
      contentType: 'application/json',
      success: function (data) {
        alert(data);
      }
    });
  };

  function updateTeacher() {
    var data = {"userName": "吴小聪","userId":"7"};
    $.ajax({
      type: 'put',
      url: '/smoke/teacher',
      dataType: 'json',
      contentType: 'application/json',
      data: JSON.stringify(data),
      success: function (data) {
        alert(data);
      }
    });
  };


</script>
<input type="button" value="增加" onclick="addTeacher()"/>
<input type="button" value="删除" onclick="deleteTeacher()"/>
<input type="button" value="更新" onclick="updateTeacher()"/>
</body>
</html>

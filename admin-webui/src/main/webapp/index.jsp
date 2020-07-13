<%--
  Created by IntelliJ IDEA.
  User: xiaowen
  Date: 2020/7/9
  Time: 23:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>首页</title>
        <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
        <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="layer/layer.js"></script>
        <script type="text/javascript" >
        $(function(){

            location.href="${pageContext.request.contextPath}/admin/to/login/page.html";

            $("#btn1").click(function () {

                $.ajax({
                    "url": "send/array.html",
                    "type": "post",
                    "data": {
                        "array": [5,8,12]
                    },
                    "dataType": "text",
                    "success": function (res) {
                        alert(res);
                    },
                    "error": function (res) {
                        alert(res);
                    }
                });
            });

            $("#btn2").click(function () {

                $.ajax({
                    "url": "send/arrayTwo.html",
                    "type": "post",
                    "data": {
                        "array[0]": 5,
                        "array[1]": 8,
                        "array[2]": 12,
                    },
                    "dataType": "text",
                    "success": function (res) {
                        alert(res);
                    },
                    "error": function (res) {
                        alert(res);
                    }
                });
            });

            $("#btn3").click(function () {

                var array = [5, 8, 12];

                //将JSON数组转换为JSON字符串
                var requestBody = JSON.stringify(array);

                $.ajax({
                    "url": "send/arrayThree.html",
                    "type": "post",
                    "data": requestBody,
                    "contentType": 'application/json;charset=utf-8',  //设置请求体的内容类型，告诉服务器本次请求数据的实体为JSON
                    "dataType": "text",                               //如何对待服务器返回的数据
                    "success": function (res) {
                        alert(res);
                    },
                    "error": function (res) {
                        alert(res);
                    }
                });
            });

            $("#btn4").click(function () {

                // 准备要发送的数据
                var student1 = {
                    "stuId": 1,
                    "stuName": "小文",
                    "address": {
                        "province": "四川",
                        "city": "南充",
                        "street": "后瑞"
                    },
                    "subjectList": [
                        {
                            "subjectName": "JavaSE",
                            "subjectScore": 100
                        }, {
                            "subjectName": "SSM",
                            "subjectScore": 99
                        }
                    ],
                    "map": {
                        "k1": "v1",
                        "k2": "v2"
                    }
                };
                var student2 = {
                    "stuId": 5,
                    "stuName": "欧欧",
                    "address": {
                        "province": "四川",
                        "city": "成都",
                        "street": "前瑞"
                    },
                    "subjectList": [
                        {
                            "subjectName": "Vue",
                            "subjectScore": 100
                        }, {
                            "subjectName": "JS",
                            "subjectScore": 99
                        }
                    ],
                    "map": {
                        "k1": "v1",
                        "k2": "v2"
                    }
                };

                var students = [student1, student2]
                // 将JSON对象转换为JSON字符串
                var requestBody = JSON.stringify(students);

                // 发送Ajax请求
                $.ajax({
                    "url": "send/compose/object.json",
                    "type": "post",
                    "data": requestBody,
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "json",  // 返回JSON
                    "success": function (response) {
                        console.log(response);
                    },
                    "error": function (response) {
                        console.log(response);
                    }
                });
            });

            // 准备要发送的数据
            var student1 = {
                "stuId": 1,
                "stuName": "小文",
                "address": {
                    "province": "四川",
                    "city": "南充",
                    "street": "后瑞"
                },
                "subjectList": [
                    {
                        "subjectName": "JavaSE",
                        "subjectScore": 100
                    }, {
                        "subjectName": "SSM",
                        "subjectScore": 99
                    }
                ],
                "map": {
                    "k1": "v1",
                    "k2": "v2"
                }
            };
            var student2 = {
                "stuId": 5,
                "stuName": "欧欧",
                "address": {
                    "province": "四川",
                    "city": "成都",
                    "street": "前瑞"
                },
                "subjectList": [
                    {
                        "subjectName": "Vue",
                        "subjectScore": 100
                    }, {
                        "subjectName": "JS",
                        "subjectScore": 99
                    }
                ],
                "map": {
                    "k1": "v1",
                    "k2": "v2"
                }
            };

            var students = [student1, student2]
            // 将JSON对象转换为JSON字符串
            var requestBody = JSON.stringify(students);

            $("#btn4").click(function () {

                // 发送Ajax请求
                $.ajax({
                    "url": "send/compose/object.json",
                    "type": "post",
                    "data": requestBody,
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "json",  // 返回JSON
                    "success": function (response) {
                        console.log(response);
                    },
                    "error": function (response) {
                        console.log(response);
                    }
                });
            });

            $("#btn5").click(function () {

                // 发送Ajax请求
                $.ajax({
                    "url": "send/compose/tongyi.json",
                    "type": "post",
                    "data": requestBody,
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "json",  // 返回JSON
                    "success": function (response) {
                        console.log(response);
                    },
                    "error": function (response) {
                        console.log(response);
                    }
                });
            });

            $("#btn6").click(function () {

               layer.msg("Layer的弹框");
            });


        });
        </script>
    </head>
    <body>
        <a href="test/ssm.html">测试整合SSM环境</a><br>
        <button id="btn1">Send [5,8,12] one</button>
        <br><br>
        <button id="btn2">Send [5,8,12] Two</button>
        <br><br>
        <button id="btn3">Send [5,8,12] Three</button>
        <br><br>
        <button id="btn4">Send Compose Object</button>
        <br><br>
        <button id="btn5">统一返回JSON数据</button>
        <br><br>
        <button id="btn6">layer弹框</button>
    </body>
</html>

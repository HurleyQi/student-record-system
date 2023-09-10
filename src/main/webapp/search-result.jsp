<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<html>
    <body>
        <div id="tableContainer">
            <table class="content_table">
                <!-- group header content in table -->
                <thead>
                    <tr>
                        <td>名字</td>
                        <td>身份证号</td>
                        <td>年龄</td>
                        <td>性别</td>
                        <td>出生日期</td>
                        <td>地址</td>
                        <td>所属班级</td>
                        <td>操作</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${studentList}" var="student">
                        <form action="student_delete_servlet" method="get">
                            <tr>
                                <td><input class="input-table" type="text" name="name" value="${student.name}" readonly></td>
                                <td><input class="input-table" type="password" name="id" value="${student.id}" readonly></td>
                                <td><input class="input-table" type="text" name="age" value="${student.age}" readonly></td>
                                <c:choose>
                                    <c:when test="${student.gender == 1}">
                                        <td><input class="input-table" type="text" name="gender" value="男" readonly></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><input class="input-table" type="text" name="gender" value="女" readonly></td>
                                    </c:otherwise>
                                </c:choose>
                                <td><input class="input-table" type="text" name="date" value="${student.date}" readonly></td>
                                <td><input class="input-table" type="text" name="address" value="${student.address}" readonly></td>
                                <td><input class="input-table" type="text" name="course" value="${student.course}" readonly></td>
                                <td>
                                    <input class="table_button" type="submit" onclick="return confirmDelete()" value="删除">
                                    <button class="table_button" formaction="edit_helper_servlet">
                                        修改
                                    </button>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <style>
            #tableContainer {
                height: 500px;
                overflow-y: scroll;
            }

            .content_table {
                border-collapse: collapse;
                margin: 25px 0;
                font-size: 0.9em;
                min-width: 400px;
                font-family: sans-serif;
                border-radius: 5px 5px 0 0;
                overflow: hidden;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
                margin-left: auto;
                margin-right: auto;
            }

            .content_table thead tr {
                background-color: #009879;
                color: white;
                text-align: left;
                font-weight: bold;
            }

            .content_table td {
                padding: 12px 15px;
            }

            .content_table tbody tr {
                border-bottom: 1px solid #dddddd;
            }

            .content_table tbody tr:nth-of-type(even) {
                background-color: #f3f3f3;
            }

            .input-table:nth-of-type(even) {
                background-color:#f3f3f3 ;
            }

            .content_table tbody tr:last-of-type {
                border-bottom: 3px solid #009879;
            }

            .table_button {
                display: flex;
                justify-content: center;
                align-items: center;
                font-size: 14px;
                margin-bottom: 5px;
                padding: 5px 10px;
                border-radius: 5px;
                border: none;
                background-color: #5ea1ff;
                color: white;
                cursor: pointer;
            }

            a {
                color: white;
                text-decoration: none;
            }

            .input-table {
                border: none;
                outline: none;
                background-color: transparent;
            }
        </style>

        <script>
            var tableContainer = document.getElementById("tableContainer");

            tableContainer.onscroll = function() {
                if ((tableContainer.scrollTop + tableContainer.clientHeight) 
                >= tableContainer.scrollHeight) {
                    var xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function() {
                        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                            var response = JSON.parse(xhr.responseText);
                            var table = document.querySelector("#tableContainer");
                            table.innerHTML += response.newRows;
                        }
                    };
                    xhr.open("GET","search_result.jsp", true);
                    xhr.send();
                }
            };
        </script>
    </body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>  

<html>
    <head></head>
    <body>
        <h1>主界面</h1> 
        <div class="formContainer">
            <div class="form">
                <form action="#callSearch" method="get" id="search_form" >
                    <div class="search_container">
                        <div class="form_item">
                            <label for="name" class="form_label">名字:</label>
                            <input type="text" class="form_input" id="nameInput" name="name" placeholder="请输入姓名">
                        </div>
                        <div class="form_item">
                            <label for="course" class="form_label">班级:</label>
                            <input type="text" class="form_input" id="courseInput" name="course" placeholder="请输入所属班级">
                        </div>
                        <input type="submit" class="form_button" value="查询">
                    </div>
                </form>
            </div>
            <div class="addContainer">
                <div class="form" id="addForm">
                    <form action="add.jsp">
                        <input type="submit" class="form_button" value="增加">
                    </form>
                </div>
            </div>
        </div>         

        <div id="callSearch">
            <c:import url="student_search_servlet"/>
        </div>

        <div>     
            <jsp:include page="search-result.jsp"/>
        </div>

        <script>
            function confirmDelete() {
                return confirm("确定删除此学生记录？");
            }

            document.getElementById("search_form").addEventListener("submit", function(event){
                event.preventDefault();
                var nameValue = document.getElementById("nameInput").value;
                var courseValue = document.getElementById("courseInput").value;
                localStorage.setItem("name", nameValue);
                localStorage.setItem("course", courseValue);
                document.getElementById("nameInput").value = nameValue;
                document.getElementById("courseInput").value = courseValue;
                document.getElementById("search_form").submit();
            });
        </script>

        <script>
            document.addEventListener("DOMContentLoaded", function() {
                var storedName = localStorage.getItem("name");
                var storedCourse = localStorage.getItem("course");
                if (storedName) {
                    document.getElementById("nameInput").value = storedName;
                }
                if (storedCourse) {
                    document.getElementById("courseInput").value = storedCourse;
                }
            });
        </script>

        <style>
            .form {
                font-size: 16px;
                font-family: sans-serif;
                flex: 1;
            }

            .form * {
                box-sizing: border-box;
                line-height: 1.2;
            }

            .formContainer {
                display: flex;
            }

            .addContainer {
                display: flex;
                justify-content: center;
                align-items: center;
                width: 950px;
            }

            #addForm {
                display: flex;
                left: 100px;
            }

            .form_label {
                font-weight: 600;
                padding: 12px 0;
                width: 50px;
            }

            .form_input {
                -webkit-appearance: none;
                max-width: 425px;
                margin-left: 10px;

                font-family: sans-serif;

                outline: none;
                border: 1px solid #dddddd;
                border-radius: 4px;
                background: #f9f9f9;
                transition: background 0.25s, border-color 0.25s, color 0.25s;
            }

            .form_input label{
                margin-right: 10px;
            }

            label{
                display: inline;
            }

            .form_input:focus {
                background: #ffffff;
            }

            .form_input::placeholder {
                color: #bbbbbb;
            }

            .form_button {
                font-family: sans-serif;
                font-weight: 400;
                padding: 5px 10px;
                margin: 10px 0;
                margin-right: 10px;
                width: 60px;

                color: white;
                background-color: #04AA6D;
                border: 2px solid #0fa942;
                border-radius: 8px;
                cursor: pointer;

                outline: none;
            }

            h1 {
                font-size: 60px;
                text-align: center;
            }

            .form_item {
                display: flex;
                justify-content: center;
                margin-right: 20px;
            }

            .search_container {
                display: flex;
            }
        </style>
    </body>
</html>



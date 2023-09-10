<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %> 

<html>
    <body>
        <h1>修改</h1>
        <div>
            <form action="student_edit_servlet" method="POST" class="basic-info">
                <div class="form_item">
                    <label for="name" class="form_label">名字</label>
                    <input type="text" name="name" class="form_input" maxlength="25" value="${student.name}" required>
                </div>
                <div class="form_item">
                    <label for="id" class="form_label">身份证号</label>
                    <input type="number" name="personalId" class="form_input" min="1" value="${student.id}" required readonly>
                </div>
                <div class="form_item">
                    <label for="age" class="form_label">年龄</label>
                    <input type="number" name="age" class="form_input" min = "1" max="110" value="${student.age}">
                </div>
                <div class="form_item">
                    <label for="gender" class="form_label">性别</label>
                    <div>
                        <input type="radio" name="gender" value="male" <c:if test="${student.gender == 1}">checked</c:if>>男
                        <input type="radio" name="gender" value="female" <c:if test="${student.gender.equals('2')}">checked</c:if>>女
                    </div>
                </div>
                <div class="form_item">
                    <label for="date" class="form_label">出生日期</label>
                    <input type="date" name="date" value="${student.date}" class="form_input">
                </div>
                <div class="form_item">
                    <label for="address" class="form_label">地址</label>
                    <input type="text" name="address" class="form_input" maxlength="45" value="${student.address}">
                </div>
                <div class="form_item">
                    <label for="pCourse" class="form_label">所属班级</label>
                    <input type="text" name="pCourse" class="form_input" maxlength="30" value="${student.course}">
                </div>

                <div>
                    <h2>学生记录</h2>
                    <button class="ed_button" type="button" onclick="addNewRow()">添加一行</button>
                    <table id="formTable">
                        <tr id="tableLabel" >
                            <td><label for="studentId">学号</label></td>
                            <td><label for="schoolName">学校名字</label></td>
                            <td><label for="startDate">开始日期</label></td>
                            <td><label for="endDate">结束日期</label></td>
                            <td><label for="level">教育阶段</label></td>
                        </tr>
                        <tr id="newRowTemplate" style="display:none">
                            <td><input type="number" name="studentIds" class="form_input" min="1"></td>
                            <td><input type="text" name="schoolNames" class="form_input" maxlength="45"></td>
                            <td><input type="date" name="startDates" class="form_input" min="1920-01-01"></td>
                            <td><input type="date" name="endDates" class="form_input" max="2030-12-01"></td>
                            <td>
                                <select name="level">
                                    <option value="1">小学</option>
                                    <option value="2">初中</option>
                                    <option value="3">高中</option>
                                    <option value="4">大学</option>
                                </select>
                            </td>
                            <td><button class="ed_button" type="button" onclick="removeRow(this)">删除一行</button></td>
                        </tr>
                        <c:forEach items="${recordList}" var="record">            
                            <tr id="new-rowTemplate">
                                <td><input type="number" name="studentIds" class="form_input" value="${record.studentId}" min="1" readonly></td>
                                <td><input type="text" name="schoolNames" class="form_input" value="${record.school}" maxlength="45"></td>
                                <td><input type="date" name="startDates" class="form_input" value="${record.start}" min="1920-01-01"></td>
                                <td><input type="date" name="endDates" class="form_input" value="${record.end}" max="2030-12-01"></td>
                                <td>
                                    <select name="level">
                                        <option value="1" <c:if test="${record.level == 1}">selected</c:if>>小学</option>
                                        <option value="2" <c:if test="${record.level == 2}">selected</c:if>>初中</option>
                                        <option value="3" <c:if test="${record.level == 3}">selected</c:if>>高中</option>
                                        <option value="4" <c:if test="${record.level == 4}">selected</c:if>>大学</option>
                                    </select>
                                </td>
                                <td><button class="ed_button" type="button" onclick="removeRow(this)">删除一行</button></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <button class="form_button" type="submit" formaction="student_edit_servlet">修改</button>
            </form>
            <form action="index.jsp">
                <button class="form_button">返回</button>
            </form>
            <style>
                form {
                    margin-bottom: 10px;
                    display: inline-block;
                    font-size: 16px;
                    font-family: sans-serif;
                }

                .form * {
                    box-sizing: border-box;
                    line-height: 2;
                }

                .form_item {
                    display: flex;
                    flex-direction: column;
                }

                /* every single descendant of the form_it */
                .form_item > * {
                    align-self: flex-start;
                }

                .form_label {
                    font-weight: 600;
                    padding: 10px 0;
                }

                .form_input {
                    -webkit-appearance: none;

                    width: 100%;
                    max-width: 425px;

                    /* Fix for Safari/IOS date fields */
                    min-height: calc(0.9em + (0.8em * 2) + 0.6em);

                    padding: 0.8em;
                    font-size: 0.9em;
                    font-family: sans-serif;

                    outline: none;
                    border: 1px solid #dddddd;
                    border-radius: 4px;
                    background: #f9f9f9;
                    /* transition, can be used for when the user clicks on the input box */
                    transition: background 0.25s, border-color 0.25s, color 0.25s;
                }

                /* this focuses on when the user is typing into the input field */
                .form_input:focus {
                    background: #ffffff;
                }

                /* focuses on the input box's placeholder */
                .form_input::placeholder {
                    color: #bbbbbb;
                }

                textarea.form_input {
                    resize: none;
                    min-height: 200px;
                }

                .form_button {
                    font-family: sans-serif;
                    font-weight: 600;
                    font-size: 1.1em;
                    padding: 8px 30px;
                    margin: 10px 0;

                    color: white;
                    background-color: #04AA6D;
                    border: 2px solid #0fa942;
                    border-radius: 8px;
                    cursor: pointer;

                    outline: none;
                }
        
                .ed_level {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-top: 0.5rem;
                font-size: 1.2rem;
                }

                .ed_button {
                    font-weight: 400;
                    font-size: 0.8em;
                    padding: 7px 15px;

                    color: white;
                    background-color: #41a1f0;
                    border: 2px solid #41a1f0;
                    border-radius: 5px;
                    cursor: pointer;
                }

                h1 {
                    font-size: 60px;
                    text-align: center;
                }

                td {
                    padding: 10px;
                }
            </style>

            <script>
                function addNewRow() {
                    var table = document.getElementById("formTable");
                    var newRowTemplate = document.getElementById("newRowTemplate");
        
                    var newRow = newRowTemplate.cloneNode(true);
                    newRow.style.display = ""; // Make the new row visible
                    table.appendChild(newRow);
                }
        
                function removeRow(button) {
                    var row = button.parentNode.parentNode;
                    row.parentNode.removeChild(row);
                }
            </script>
        </div>
    </body>
</html>



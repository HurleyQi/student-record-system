<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<script>
    var errorMessage = "${exceptionMessage}";
    alert("失败: " + errorMessage);
    window.location.href = "index.jsp";
</script>
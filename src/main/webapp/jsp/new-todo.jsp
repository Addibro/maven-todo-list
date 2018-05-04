<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <div class="container">
        <title>New Todo</title>
    </div>
    <!-- font awesome-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.10/css/all.css">
    <!-- css-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <form action="/todo" method="post" role="form" data-toggle="validator">
            <c:if test="${empty action}">
                <c:set var="action" value="add"/>
            </c:if>
            <input type="hidden" id="action" name="action" value="${action}">
            <input type="hidden" id="idTodo" name="idTodo" value="${todo.id}">
            <h2>New Todo</h2>
            <div class="form-group col-4">
                <label for="name" class="control-label col-4">Name:</label>
                <input type="text" name="name" id="name" class="form-control" value="${todo.name}" placeholder="${todo.name}" required="true">
                <br>
                <label for="category" class="control-label col-4">Category:</label>
                <input type="text" name="category" id="category" class="form-control" value="${todo.category}" placeholder="${todo.category}" required="true">
                <br>
                <label for="dueDate" class="control-label col-4">Due Day:</label>
                <input type="text" pattern="^\d{2}-\d{2}-\d{4}$" value="${todo.dueDate}" name="dueDate" maxlength="10" id="dueDate" class="form-control" placeholder="DD-MM-YYYY">
                <br>
                <label for="turninLink" class="control-label col-4">Turnin Link</label>
                <input type="text" name="turninLink" id="turninLink" class="form-control" value="${todo.turninLink}" placeholder="${todo.turninLink}">
                <br>
                <button type="submit" class="btn btn-primary btn-md">Accept</button>

            </div>
        </form>
    </div>
</body>
</html>

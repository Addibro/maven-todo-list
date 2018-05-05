<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.10/css/all.css" integrity="sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg" crossorigin="anonymous">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
    </head>

    <body>
        <div class="container">
            <h2>Todos</h2>

            <!-- Search -->
            <form action="/todo" method="get" id="searchTodoForm" role="form">
                <input type="hidden" id="searchAction" name="searchAction" value="searchByName"/>
                <div class="form-group col-5">
                    <input type="text" name="todoName" id="todoName" class="form-control" required="true" placeholder="Type the name of the todo"/>
                </div>
                <div class="col-2">
                    <button type="submit" class="btn btn-info">
                        <span class="fas fa-search"></span> Search
                    </button>
                </div>
                <br>
                <br>
            </form>
        </div>

        <!-- Table form-->
        <div class="container">
            <c:if test="${not empty message}">
                <div class="alert alert-success col-5">
                    ${message}
                </div>
            </c:if>

            <form action="/todo" method="post" id="todoForm" role="form">
                <!-- These inputs are later used in the remove action (getElementById)-->
                <input type="hidden" id="idTodo" name="idTodo"/>
                <input type="hidden" id="action" name="action"/>
                <input type="hidden" id="name" name="name">
                <c:choose>
                    <c:when test="${not empty todoList}">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <td>#</td>
                                    <td>Name</td>
                                    <td>Category</td>
                                    <td>Due Date</td>
                                    <td>Turn in Link</td>
                                    <td>Done</td>
                                </tr>
                            </thead>
                            <c:forEach var="todo" items="${todoList}">
                                <c:set var="classSuccess" value=""/>
                                <c:if test="${idTodo == todo.id}">
                                    <c:set var="classSuccess" value="info"/>
                                </c:if>
                                <tr class="${classSuccess}">
                                    <td>
                                           <a href="/todo?idTodo=${todo.id}&searchAction=searchById">
                                               <span class="fas fa-edit"></span>
                                           </a>
                                    </td>
                                    <td>${todo.name}</td>
                                    <td>${todo.category}</td>
                                    <td>${todo.dueDate}</td>
                                    <td><a href="${todo.turninLink}" target="_blank">
                                        <span class="fas fa-external-link-alt"></span>
                                    </a></td>
                                    <td>
                                        <a href="#" id="done"
                                            onclick="document.getElementById('idTodo').value='${todo.id}';
                                                     document.getElementById('action').value='done';
                                                     document.getElementById('name').value='${todo.name}';
                                                     document.getElementById('todoForm').submit();">
                                            <span class="far fa-check-circle"/>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <br>
                        <div class="alert alert-info col-5">
                            No todos found
                        </div>
                    </c:otherwise>
                </c:choose>
            </form>
            <form action="jsp/new-todo.jsp">
                <br>
                <br>
                <button class="btn btn-info">
                    <span class="fa fa-plus"></span> New Todo
                </button>
            </form>
            <br>
            <br>
            <h3>Todo History</h3>
            <c:if test="${not empty messageHistory}">
                <div class="alert alert-success col-5">
                        ${messageHistory}
                </div>
            </c:if>
            <form action="/todo" method="post" id="todoForm" role="form">
                <!-- These inputs are later used in the remove action (getElementById)-->
                <input type="hidden" id="idTodo" name="idTodo"/>
                <input type="hidden" id="action" name="action"/>
                <input type="hidden" id="name" name="name">
                <c:choose>
                    <c:when test="${not empty todoHistory}">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <td>Name</td>
                                <td>Category</td>
                                <td>Due Date</td>
                                <td>Turn in Link</td>
                                <td>Remove</td>
                            </tr>
                            </thead>
                            <c:forEach var="todo" items="${todoHistory}">
                                <c:set var="classSuccess" value=""/>
                                <c:if test="${idTodo == todo.id}">
                                    <c:set var="classSuccess" value="info"/>
                                </c:if>
                                <tr class="${classSuccess}">
                                    <td>${todo.name}</td>
                                    <td>${todo.category}</td>
                                    <td>${todo.dueDate}</td>
                                    <td><a href="${todo.turninLink}" target="_blank">
                                        <span class="fas fa-external-link-alt"></span>
                                    </a></td>
                                    <td>
                                        <a href="#" id="remove"
                                           onclick="document.getElementById('idTodo').value='${todo.id}';
                                                   document.getElementById('action').value='remove';
                                                   document.getElementById('name').value='${todo.name}';
                                                   document.getElementById('todoForm').submit();">
                                            <span class="fas fa-trash"/>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <br>
                        <div class="alert alert-info col-5">
                            No todo history
                        </div>
                    </c:otherwise>
                </c:choose>
            </form>

        </div>
    </body>
</html>



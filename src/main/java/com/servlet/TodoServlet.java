package com.servlet;

import com.todo.GetTodos;
import com.todo.Todo;
import org.bson.types.ObjectId;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "TodoServlet", urlPatterns = {"/todo"})
public class TodoServlet extends HttpServlet {

    GetTodos getTodos = new GetTodos();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                addTodo(request, response);
                break;
            case "remove":
                removeTodo(request, response);
                break;
            case "edit":
                editTodo(request, response);
                break;
        }
    }

    private void editTodo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ObjectId idTodo = new ObjectId(req.getParameter("idTodo"));
        String name = req.getParameter("name");
        String category = req.getParameter("category");
        String dueDate = req.getParameter("dueDate");
        String turninLink = req.getParameter("turninLink");
        Todo todo = new Todo(name, category, dueDate, turninLink);
        boolean confirm = getTodos.updateTodo(idTodo, todo);
        if (confirm) {
            String message = "Todo was updated";
            req.setAttribute("message", message);
        }
        req.setAttribute("idTodo", idTodo);
        List<Todo> todoList = getTodos.getAllTodos();
        forwardListTodos(req, resp, todoList);
    }

    private void addTodo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String category = req.getParameter("category");
        String dueDate = req.getParameter("dueDate");
        String turninLink = req.getParameter("turninLink");
        Todo todo = new Todo(name, category, dueDate, turninLink);
        ObjectId idTodo = getTodos.addTodo(todo);
        if (idTodo == null) {
            System.out.println("idTodo is null");
        }
        List<Todo> todoList = getTodos.getAllTodos();
        req.setAttribute("idTodo", idTodo);
        String message = name + " has been successfully been created";
        req.setAttribute("message", message);
        System.out.println("Servlet: addTodo() - " + todoList);
        forwardListTodos(req, resp, todoList);
    }

    private void removeTodo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectId idTodo = new ObjectId(req.getParameter("idTodo"));
        String name = req.getParameter("name");
        boolean confirm = getTodos.removeTodo(idTodo);
        String message;
        if (confirm) {
            message = name + " was removed";
            req.setAttribute("message", message);
        }
        List<Todo> todoList = getTodos.getAllTodos();
        forwardListTodos(req, resp, todoList);
    }

    private void searchTodoByName(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String todoName = request.getParameter("todoName");
        List<Todo> result = getTodos.searchTodoByName(todoName);
        forwardListTodos(request, response, result);
    }

    private void searchTodoById(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectId id = new ObjectId(request.getParameter("idTodo"));
        Todo todo = null;
        try {
            todo = getTodos.getTodoById(id);
        } catch (RuntimeException ex) {
            Logger.getLogger(TodoServlet.class.getName())
                    .log(Level.SEVERE, ex.getMessage());
        }
        request.setAttribute("todo", todo);
        request.setAttribute("action", "edit");
        String nextJSP = "/jsp/new-todo.jsp";

        // RequestDispatcher receives a requests from client and sends them to any resource on the server
        // (could be a servlet, HTML or jsp file)
        // getServletContext returns a reference to the context in which the servlet is running
        // getRequestDispatcher(nextJSP) returns a RequestDispatcher object which acts as a wrapper
        // for the resource in the path specified
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("searchAction");
        if (action != null) {
            switch (action) {
                case "searchById":
                    searchTodoById(request, response);
                    break;
                case "searchByName":
                    searchTodoByName(request, response);
                    break;
            }
        } else {
            List<Todo> todoList = getTodos.getAllTodos();
            forwardListTodos(request, response, todoList);
        }

    }

    private void forwardListTodos(HttpServletRequest request, HttpServletResponse response, List<Todo> todoList)
            throws  ServletException, IOException {
        String nextJSP = "/jsp/list-todos.jsp"; // resource
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        request.setAttribute("todoList", todoList);
        dispatcher.forward(request, response);
    }
}
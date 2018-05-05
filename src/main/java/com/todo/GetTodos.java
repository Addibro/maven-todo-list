package com.todo;

import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GetTodos {

    private List<Todo> todos;
    private List<Todo> todoHistory;

    /**
     * Get all todos
     */
    public List<Todo> getAllTodos() throws UnknownHostException {
        return todos = DBHelper.getTodos("todos");
    }

    public List<Todo> getTodoHistory() throws UnknownHostException {
        return todoHistory = DBHelper.getTodos("todoHistory");
    }

    /**
     * Get all todos whose name match the query string, ordered by name
     */
    public List<Todo> searchTodoByName(String query) {
        Comparator<Todo> compareByName = Comparator.comparing(Todo::getName);
        return todos.stream()
                .filter(i -> i.getName().equalsIgnoreCase(query))
                .sorted(compareByName)
                .collect(Collectors.toList());
    }
    
    /**
     * Get the todo by id
     * 
     */
    public Todo getTodoById(ObjectId id) throws RuntimeException {
        Optional<Todo> match = todos.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
        if (match.isPresent()) {
            return match.get();
        } else {
            throw new RuntimeException("The Todo id " + id + "was not found");
        }
    }

    /**
     * Add a todo
     */
    public ObjectId addTodo(Todo todo) {
        ObjectId id = DBHelper.addToDb(todo);
        System.out.println("GetTodos: " + todo.getName() + " with ObjectId: " + id);
        return id;
    }

    /**
     * Remove todo
     */
    public boolean removeTodo(ObjectId idTodo) {
        return DBHelper.removeTodoFromHistory(idTodo);
    }

    /**
     * Done
     */
    public ObjectId handleDoneTodo(Todo todo) {
        return DBHelper.doneTodo(todo);
    }

    /**
     * Get
     */
    public Todo getTodo(ObjectId id, String collectionName) {
        return DBHelper.getOneTodo(id, collectionName);
    }

    /**
     * Update todo
     */
    public boolean updateTodo(ObjectId idTodo, Todo todo) {
        return DBHelper.updateTodo(idTodo, todo);
    }
}

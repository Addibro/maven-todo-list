package com.todo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DBHelper {

    private static MongoClient mongoClient;
    private static Jongo jongo;
    private static MongoCollection todos;
    private static MongoCollection todoHistory;

    static {
        try {
            connect();
        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private DBHelper() {}

    static List<Todo> getTodos(String collectionName) {
        Iterable<Todo> todoList = jongo.getCollection(collectionName)
                .find().as(Todo.class);
        return StreamSupport.stream(todoList.spliterator(), false)
                .collect(Collectors.toList());
    }

    static ObjectId addToDb(Todo todo) {
        return (ObjectId) todos.save(todo).getUpsertedId();
    }

    static boolean removeTodoFromHistory(ObjectId idTodo) {
        todoHistory.remove(idTodo);
        Todo todo = todoHistory.findOne(idTodo).as(Todo.class);
        return todo == null;
    }

    static ObjectId doneTodo(Todo todo) {
        // save in history
        ObjectId saved = (ObjectId) todoHistory.save(todo).getUpsertedId();
        // remove from todos
        todos.remove(todo.getId());
        return saved;
    }

    static boolean updateTodo(ObjectId idTodo, Todo todo) {
        return todos.update(idTodo)
                .with(todo)
                .isUpdateOfExisting();
    }

    static Todo getOneTodo(ObjectId id, String collectionName) {
        return jongo.getCollection(collectionName).findOne(id).as(Todo.class);
    }

    static boolean closeDB() {
        if (mongoClient != null) {
            mongoClient.close();
            return true;
        }
        return false;
    }

    private static void connect() throws UnknownHostException {
        if (mongoClient == null) {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB("todos");
            jongo = new Jongo(db);
            todos = jongo.getCollection("todos");
            todoHistory = jongo.getCollection("todoHistory");
        }
    }
}

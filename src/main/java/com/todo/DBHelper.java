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

    private DBHelper() {}

    static List<Todo> qetAllFromDB() throws UnknownHostException {
        connect();
        Iterable<Todo> todoList = jongo.getCollection("todos")
                .find().as(Todo.class);
        return StreamSupport.stream(todoList.spliterator(), false)
                .collect(Collectors.toList());
    }

    static ObjectId addToDb(Todo todo) {
        System.out.println(todo);
        todos.save(todo);
        Todo returnTodo = todos.findOne(todo.getId()).as(Todo.class);
        return returnTodo.getId();
    }

    static boolean removeTodo(ObjectId idTodo) {
        todos.remove(idTodo);
        Todo removed = todos.findOne(idTodo).as(Todo.class);
        return removed == null;
    }

    static boolean updateTodo(ObjectId idTodo, Todo todo) {
        return todos.update(idTodo)
                .with(todo)
                .isUpdateOfExisting();
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
        }
    }
}

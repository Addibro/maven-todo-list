package com.todo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;


public class Todo {
    private ObjectId _id;
    private String name;
    private String category;
    private String dueDate;
    private String turninLink;

    private Todo() {}

    @JsonCreator
    public Todo(@JsonProperty("name") String name, @JsonProperty("category") String category, @JsonProperty("dueDate") String dueDate, @JsonProperty("turninLink") String turninLink) {
        this._id = new ObjectId();
        this.name = name;
        this.category = category;
        this.dueDate = dueDate;
        this.turninLink = turninLink;
    }

    public ObjectId getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getTurninLink() {
        return turninLink;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setTurninLink(String turninLink) {
        this.turninLink = turninLink;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "_id=" + _id.toString() +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", dueDate=" + dueDate +
                ", turninLink='" + turninLink + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Todo
                && ((Todo) obj)._id == this._id
                && ((Todo) obj).name.equals(this.name)
                && ((Todo) obj).category.equals(this.category)
                && ((Todo) obj).dueDate.equals(this.dueDate)
                && ((Todo) obj).turninLink.equals(this.turninLink);
    }
}

package com.example.films;

public class Model {
    private String id,desription,title;
    public Model(){

    }

    public Model(String id, String title, String desription) {
        this.id = id;
        this.title = title;
        this.desription = desription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id='" + id + '\'' +
                ", desription='" + desription + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

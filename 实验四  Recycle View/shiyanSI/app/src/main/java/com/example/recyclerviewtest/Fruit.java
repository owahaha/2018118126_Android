package com.example.recyclerviewtest;

public class Fruit {

    private String name;

    private int imageId;

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setName(String name){
        this.name = name;
    }

    public String toString(){
        return "Fruit{"+"name"+name+'\''+",imageld="+imageId+"}";
    }
}

package edu.eci.arsw.application.entities.util;

public class Point {


    private int x;
    private int y;
    private String color;


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("x: %d - y: %d - color: %s", x,y,color);
    }
}

package edu.eci.arsw.application.entities.util;

public class Line {
    private String color;
    private Point start;
    private Point end;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("start: %s - end: %s - color: %s", start,end, color);
    }


}

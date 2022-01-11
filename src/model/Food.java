package model;

import java.awt.*;
/**
 *define the dots in the board that can be eaten by the pacman
 */
public class Food {

    public Point position;
    public boolean isEaten;
    public long eatenTime;

    public Food(int x,int y){
        position = new Point(x,y);
        this.isEaten = false;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public boolean isEaten() {
        return isEaten;
    }

    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    public long getEatenTime() {
        return eatenTime;
    }

    public void setEatenTime(long eatenTime) {
        this.eatenTime = eatenTime;
    }
}

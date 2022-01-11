package model;

import java.awt.*;

public class Bomb extends Food{

    public int type; //0-5


    public Bomb(int x, int y, int type) {
        super(x, y);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

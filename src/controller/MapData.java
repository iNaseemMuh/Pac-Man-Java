package controller;

import model.Food;
import model.Bomb;
import model.InitGhostData;
import model.TeleportTunnel;

import java.awt.*;
import java.util.ArrayList;
/**
 * class that saves the map elements as objects
 */
public class MapData {

    public int x;
    public int y;
    public int[][] map;
    public Point pacmanPosition;
    public Point ghostBasePosition;
    public boolean isCustom;
    public ArrayList<Food> foodPositions;
    public ArrayList<Bomb> pufoodPositions;
    public ArrayList<TeleportTunnel> teleports;
    public ArrayList<InitGhostData> ghostsData;
    public ArrayList<Point> availablePointsForQuestion;

    public MapData() {
        foodPositions = new ArrayList<>();
        pufoodPositions = new ArrayList<>();
        teleports = new ArrayList<>();
        ghostsData = new ArrayList<>();
    }

    public MapData(int x, int y) {
        this.x = x;
        this.y = y;

        foodPositions = new ArrayList<>();
        pufoodPositions = new ArrayList<>();
        teleports = new ArrayList<>();
        ghostsData = new ArrayList<>();
        availablePointsForQuestion = new ArrayList<>();
    }

    public MapData(int x, int y, int[][] map, Point pacPosition) {
        this.x = x;
        this.y = y;
        this.map = map;
        pacmanPosition = pacPosition;

        foodPositions = new ArrayList<>();
        pufoodPositions = new ArrayList<>();
        teleports = new ArrayList<>();
        ghostsData = new ArrayList<>();
    }

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

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Point getPacmanPosition() {
        return pacmanPosition;
    }

    public void setPacmanPosition(Point pacmanPosition) {
        this.pacmanPosition = pacmanPosition;
    }

    public Point getGhostBasePosition() {
        return ghostBasePosition;
    }

    public void setGhostBasePosition(Point ghostBasePosition) {
        this.ghostBasePosition = ghostBasePosition;
    }

    public ArrayList<Food> getFoodPositions() {
        return foodPositions;
    }

    public ArrayList<Bomb> getPufoodPositions() {
        return pufoodPositions;
    }

    public ArrayList<TeleportTunnel> getTeleports() {
        return teleports;
    }

    public void setTeleports(ArrayList<TeleportTunnel> teleports) {
        this.teleports = teleports;
    }

    public ArrayList<InitGhostData> getGhostsData() {
        return ghostsData;
    }

    public ArrayList<Point> getAvailablePointsForQuestion() {
        return availablePointsForQuestion;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }
}

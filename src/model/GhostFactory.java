package model;

import controller.Game;
/**
 * Use in the factory design pattern to initialize new ghosts based on the given types
 */
public class GhostFactory {
    public Ghost getGhost(ghostType type, int x, int y, Game g) {
        Ghost gh = null;
        switch (type) {
            case RED:
                gh = new RedGhost(x, y, g);
                break;
            case PINK:
                gh = new PinkGhost(x, y, g);
                break;
            case CYAN:
                gh = new CyanGhost(x, y, g);
                break;
        }
        return gh;
    }
}

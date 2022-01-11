package model;

import controller.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
/**
 * these class defines the pacman. the user can move the pacman up/down/right/left 
 * in order to eat dots and escape from ghosts.
 */
public class Pacman implements KeyListener{

    //Move Vars
    public Timer moveTimer;
    public ActionListener moveAL;
    public moveType activeMove;
    public moveType todoMove;
    boolean isStuck = true;
    boolean speedUp = false;

    //Animation Vars
    public Timer animTimer;
    public ActionListener animAL;
    public Image[] pac;
    public int activeImage = 0;
    public int addFactor = 1;

    public Point pixelPosition;
    public Point logicalPosition;

    public Game parentBoard;


    public Pacman (int x, int y, Game pb) {

        logicalPosition = new Point(x,y);
        pixelPosition = new Point(28*x,28*y);

        parentBoard = pb;

        pac = new Image[5];

        activeMove = moveType.NONE;
        todoMove = moveType.NONE;

        try {
            pac[0] = ImageIO.read(this.getClass().getResourceAsStream("/resources/images/pac/pac0.png"));
            pac[1] = ImageIO.read(this.getClass().getResourceAsStream("/resources/images/pac/pac1.png"));
            pac[2] = ImageIO.read(this.getClass().getResourceAsStream("/resources/images/pac/pac2.png"));
            pac[3] = ImageIO.read(this.getClass().getResourceAsStream("/resources/images/pac/pac3.png"));
            pac[4] = ImageIO.read(this.getClass().getResourceAsStream("/resources/images/pac/pac4.png"));
        }catch(IOException | IllegalArgumentException e){
            System.err.println("Cannot Read Images !");
        }

        //animation timer
        animAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                activeImage = activeImage + addFactor;
                if(activeImage==4 || activeImage==0){
                    addFactor *= -1;
                }
            }
        };
        animTimer = new Timer(40,animAL);
        animTimer.start();


        moveAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                //update logical position
                if((pixelPosition.x % 28 == 0) && (pixelPosition.y % 28 == 0)){
                    if(!isStuck) {
                        switch (activeMove) {
                            case RIGHT:
                                logicalPosition.x++;
                                break;
                            case LEFT:
                                logicalPosition.x--;
                                break;
                            case UP:
                                logicalPosition.y--;
                                break;
                            case DOWN:
                                logicalPosition.y++;
                                break;
                        }
                        //send update message
                        parentBoard.dispatchEvent(new ActionEvent(this, Messages.UPDATE,null));
                    }
                    isStuck = true;
                    animTimer.stop();

                    if(todoMove != moveType.NONE && isPossibleMove(todoMove) ) {
                        activeMove = todoMove;
                        todoMove = moveType.NONE;
                    }
                }else{
                    isStuck = false;
                    animTimer.start();
                }

                switch(activeMove){
                    case RIGHT:
                        if((pixelPosition.x >= (parentBoard.m_x-1) * 28)&&parentBoard.isCustom){
                            return;
                        }
                        /*if((logicalPosition.x+1 < parentBoard.m_x) && (parentBoard.map[logicalPosition.x+1][logicalPosition.y]>0)){
                            return;
                        }*/
                        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
                            if (parentBoard.map[logicalPosition.x + 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x ++;
                        break;
                    case LEFT:
                        if((pixelPosition.x <= 0)&&parentBoard.isCustom){
                            return;
                        }
                        /*if((logicalPosition.x-1 >= 0) && (parentBoard.map[logicalPosition.x-1][logicalPosition.y]>0)){
                            return;
                        }*/
                        if(logicalPosition.x > 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
                            if (parentBoard.map[logicalPosition.x - 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x--;
                        break;
                    case UP:
                        if((pixelPosition.y <= 0)&&parentBoard.isCustom){
                            return;
                        }
                        /*if((logicalPosition.y-1 >= 0) && (parentBoard.map[logicalPosition.x][logicalPosition.y-1]>0)){
                            return;
                        }*/
                        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
                            if(parentBoard.map[logicalPosition.x][logicalPosition.y-1]>0){
                                return;
                            }
                        }
                        pixelPosition.y--;
                        break;
                    case DOWN:
                        if((pixelPosition.y >= (parentBoard.m_y-1) * 28)&&parentBoard.isCustom){
                            return;
                        }
                        /*if((logicalPosition.y+1 < parentBoard.m_y) && (parentBoard.map[logicalPosition.x][logicalPosition.y+1]>0)){
                            return;
                        }*/
                        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
                            if(parentBoard.map[logicalPosition.x][logicalPosition.y+1]>0){
                                return;
                            }
                        }
                        pixelPosition.y ++;
                        break;
                }

                //send Messege to PacBoard to check collision
                parentBoard.dispatchEvent(new ActionEvent(this, Messages.COLTEST,null));

            }
        };
        moveTimer = new Timer(16,moveAL);
        moveTimer.start();

    }
    //check if the next step of the pacman is allowed
    public boolean isPossibleMove(moveType move){
        if(logicalPosition.x >= 0 && logicalPosition.x <= parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
            switch(move){
                case RIGHT:
                    if (logicalPosition.x < parentBoard.m_x-1)
                        return !(parentBoard.map[logicalPosition.x + 1][logicalPosition.y] > 0);
                case LEFT:
                    if (logicalPosition.x > 0)
                        return !(parentBoard.map[logicalPosition.x - 1][logicalPosition.y] > 0);
                case UP:
                    return !(parentBoard.map[logicalPosition.x][logicalPosition.y - 1] > 0);
                case DOWN:
                    return !(parentBoard.map[logicalPosition.x][logicalPosition.y+1] > 0);
            }
        }
        return false;
    }

    public Image getPacmanImage(){
        return pac[activeImage];
    }

    @Override
    public void keyReleased(KeyEvent ke){
        //
    }

    @Override
    public void keyTyped(KeyEvent ke){
        //
    }

    //Handle Arrow Keys
    @Override
    public void keyPressed(KeyEvent ke){
        switch(ke.getKeyCode()){
            case 27: //ESC
                parentBoard.dispatchEvent(new ActionEvent(this, Messages.BACK,null));
                break;
            case 32: //SPACE
                parentBoard.dispatchEvent(new ActionEvent(this, Messages.EXPLODE,null));
                break;
            case 37:
                todoMove = moveType.LEFT;
                break;
            case 38:
                todoMove = moveType.UP;
                break;
            case 39:
                todoMove = moveType.RIGHT;
                break;
            case 40:
                todoMove = moveType.DOWN;
                break;
            case 82: // r
                parentBoard.dispatchEvent(new ActionEvent(this, Messages.RESET,null));
                break;
            case 17: // Ctrl
                parentBoard.dispatchEvent(new ActionEvent(this, Messages.TRADE_SCORE_FOR_LIFE,null));
                break;
        }
        //System.out.println(ke.getKeyCode());
    }

    public boolean isSpeedUp() {
        return speedUp;
    }

    public void setSpeedUp(boolean speedUp) {
        this.speedUp = speedUp;
        if (isSpeedUp())
            moveTimer.setDelay(8);
        else
            moveTimer.setDelay(16);
    }
}

import controller.Game;
import controller.SysData;
import model.*;
import org.junit.Test;
import view.PacWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Tests {

    @Test
    public void correctAnswerTest(){
        Answer testAnswer = new Answer("1. This is the answer", true);
        assertTrue(testAnswer.isCorrect);
    }


    // Test if point initiated for food properly
    @Test
    public void foodPointTest(){
        int x = 5, y = 10;
        Point p =  new Point(x,y);

        Food foodTest = new Food(x, y);
        assertEquals(p, foodTest.getPosition());
    }

    @Test
    public void teleportTest(){
        int x1 = 5, y1 = 10;
        int x2 = 15, y2 = 20;

        TeleportTunnel tp = new TeleportTunnel(x1,y1,x2,y2, moveType.UP);
        Point p1 =  new Point(x1,y1);
        Point p2 =  new Point(x2,y2);

        assertEquals(p1, tp.getFrom());
        assertEquals(p2, tp.getTo());
    }

    //Test level 1 & 2 works, other levels require dependencies
    @Test
    public void levelTest(){
        Game g = new Game();
        assertEquals(1, g.levelCheck(0));
        assertEquals(2, g.levelCheck(51));
        assertEquals(3, g.levelCheck(101));
        assertEquals(4, g.levelCheck(151));
    }

    @Test
    public void sysDataAddQuestionTest(){
        Answer testAnswer1 = new Answer("1. This is the answer", true);
        Answer testAnswer2 = new Answer("2. This is the answer", false);
        Answer testAnswer3 = new Answer("3. This is the answer", false);
        Answer testAnswer4 = new Answer("4. This is the answer", false);
        ArrayList<Answer> answers = new ArrayList<>(Arrays.asList(testAnswer1,testAnswer2,testAnswer3,testAnswer4));

        assertTrue(SysData.addQuestion(new Question(5,5,1, "this is a question example?", answers)));
    }

    @Test
    public void sysDataRemoveQuestionTest(){
        Answer testAnswer1 = new Answer("1. This is the answer", true);
        Answer testAnswer2 = new Answer("2. This is the answer", false);
        Answer testAnswer3 = new Answer("3. This is the answer", false);
        Answer testAnswer4 = new Answer("4. This is the answer", false);
        ArrayList<Answer> answers = new ArrayList<>(Arrays.asList(testAnswer1,testAnswer2,testAnswer3,testAnswer4));

        Question toRemove = new Question(5,5,1, "this is a question example?", answers);
        SysData.addQuestion(toRemove);

        assertTrue(SysData.deleteQuestion(toRemove));
    }


//    @Test
//    public void ghostDiesTest(){
//        Game game = new Game();
//        Ghost g = new Ghost(10, 10, game, 16) {
//            @Override
//            public void loadImages() {
//
//            }
//
//            @Override
//            public moveType getMoveAI() {
//                return null;
//            }
//        };
//
//        g.die();
//
//        assertTrue(g.isDead());
//    }

    @Test
    public void sysDataAddHighscoreTest(){
        SysData s = SysData.getInstance();
        int size = SysData.getHighscores().size();
        s.addHighscore(new Highscore(5, "Sharon", "2021-11-15"));

        assertEquals(size+1, SysData.getHighscores().size());
    }
}

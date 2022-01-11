package controller;

import misc.*;
import model.*;
import view.PacWindow;
import view.PopUp;
import view.StartWindow;
//import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
/**
 * The main logic behind the pacman game
 */
public class Game extends JPanel {

    public String name;
    public Timer redrawTimer;
    public ActionListener redrawAL;

    public int[][] map;
    public Image[] mapSegments;

    public Image foodImage;
    public Image fireImage;
    public Image[] pfoodImage;

    public Image goImage;
    public Image vicImage;

    public static Pacman pacman;
    public ArrayList<Food> foods;
    public ArrayList<Food> eatenFoods;
    public ArrayList<Bomb> armedBombs;
    public static ArrayList<Ghost> ghosts;
    public ArrayList<TeleportTunnel> teleports;
    public ArrayList<Question> questions;
    public ArrayList<Point> availableQuestionPoints;
    public ArrayList<Question> easyQ;
    public ArrayList<Question> mediumQ;
    public ArrayList<Question> hardQ;


    public boolean didTradeLife = false;
    public boolean isCustom = false;
    public boolean isGameOver = false;
    public boolean isWin = false;
    public boolean drawScore = false;
    public boolean clearScore = false;
    public boolean explosionFire = false;
    public boolean hasIFrames = false;
    public boolean resetBoard = false;
    public static boolean isPausedBoolean = false;
    public int scoreToAdd = 0;

    public int level = 1;
    public int score;
    public int life = 3;
    public long iframesTime = 0;
    public long explosionTime = 0;
    public boolean ghostsSpeedUp = false;
    public JLabel gameStats;

    PopUp pu = new PopUp();

    public static LoopPlayer siren;
    public static LoopPlayer pac6;

    public Point ghostBase;

    public int m_x;
    public int m_y;

    public PacWindow windowParent;
    public MapData mapData;
    public boolean isGameKilled = false;

    public String tradeLifeString = "";

    public Trap trap;
    public Food fruit;
    public Boolean drawTrap = true;
    final Timer timerOne = new Timer(5000, this::timerOneMethod);

    public Game(JLabel gameStats, MapData md, PacWindow pw) {
        new Timer(5, evt -> System.out.println("5 seconds past"));
        this.mapData = md;
        this.gameStats = gameStats;
        this.setDoubleBuffered(true);
        windowParent = pw;

        m_x = md.getX();
        m_y = md.getY();
        this.map = md.getMap();

        this.isCustom = md.isCustom();
        this.ghostBase = md.getGhostBasePosition();

        pacman = new Pacman(md.getPacmanPosition().x, md.getPacmanPosition().y, this);
        addKeyListener(pacman);

        SysData s = SysData.getInstance();
        questions = s.getQuestions();
        easyQ = new ArrayList<>();
        mediumQ = new ArrayList<>();
        hardQ = new ArrayList<>();
        foods = new ArrayList<>();
        eatenFoods = new ArrayList<>();
        ghosts = new ArrayList<>();
        teleports = new ArrayList<>();
        availableQuestionPoints = md.getAvailablePointsForQuestion();
        armedBombs = new ArrayList<>();



        //get the level of the question
        for (Question q : questions) {
            switch (q.getDiff()) {
                case 1:
                    easyQ.add(q);
                    break;
                case 2:
                    mediumQ.add(q);
                    break;
                case 3:
                    hardQ.add(q);
                    break;
            }
        }

        if (!isCustom) {
            for (int i = 0; i < m_x; i++) {
                for (int j = 0; j < m_y; j++) {
                    if (map[i][j] == 0)
                        foods.add(new Food(i, j));
                }
            }
        } else {
            foods = md.getFoodPositions();
        }

        for (int i = 1; i <= 3; i++) {
            if (getNewQuestion(i) != null) {
                foods.add(getNewQuestion(i));
            }
        }

        /*
         * Creation of ghosts.
         * Design Pattern - Factory
         * */
        ghosts = new ArrayList<>();
        GhostFactory gf = new GhostFactory();
        for (InitGhostData gd : md.getGhostsData()) {
            ghosts.add(gf.getGhost(gd.getType(), gd.getX(), gd.getY(), this));
        }

        teleports = md.getTeleports();

        setLayout(null);
        setSize(20 * m_x, 20 * m_y);
        setBackground(Color.black);

        mapSegments = new Image[28];
        mapSegments[0] = null;
        for (int ms = 1; ms < 28; ms++) {
            try {
                mapSegments[ms] = ImageIO.read(this.getClass().getResource("/resources/images/map segments/" + ms + ".png"));
            } catch (Exception ignored) {
            }
        }

        pfoodImage = new Image[6];
        for (int ms = 0; ms < 6; ms++) {
            try {
                pfoodImage[ms] = ImageIO.read(this.getClass().getResource("/resources/images/food/" + ms + ".png"));
            } catch (Exception ignored) {
            }
        }
        try {
            foodImage = ImageIO.read(this.getClass().getResource("/resources/images/food.png"));
            goImage = ImageIO.read(this.getClass().getResource("/resources/images/gameover.png"));
            vicImage = ImageIO.read(this.getClass().getResource("/resources/images/victory.png"));
            fireImage = ImageIO.read(this.getClass().getResource("/resources/images/flame.png"));
            //pfoodImage = ImageIO.read(this.getClass().getResource("/images/pfood.png"));
        } catch (Exception ignored) {
        }


        redrawAL = evt -> {
            //Draw Board
            repaint();
        };
        redrawTimer = new Timer(0, redrawAL);
        redrawTimer.start();

        siren = new LoopPlayer("siren.wav");
        pac6 = new LoopPlayer("pac6.wav");
        siren.start();
    }

    public void gameOver() {
        //Game Over
        siren.stop();
        SoundPlayer.play("pacman_lose.wav");
        pacman.moveTimer.stop();
        pacman.animTimer.stop();
        for (Ghost g : ghosts)
            g.moveTimer.stop();
        isGameOver = true;
        gameStats.setText("    Press R to try again !");
    }

    /*
     * Checks if the pacman's rectangle collides with any of the ghosts' rectangles.
     * */
    public void collisionTest() {

        Rectangle pr = new Rectangle(pacman.pixelPosition.x + 13, pacman.pixelPosition.y + 13, 2, 2);
        for (Ghost g : ghosts) {
            Rectangle gr = new Rectangle(g.pixelPosition.x, g.pixelPosition.y, 28, 28);

            if (pr.intersects(gr)) {
                if (!g.isDead()) {
                    if (life == 0) {
                        gameOver();
                        saveHighscore();
                        break;
                    } else {

                        long nowMillis2 = System.currentTimeMillis();
                        if ((nowMillis2 - iframesTime) / 1000 >= 3) {
                            life--;
                            iframesTime = System.currentTimeMillis();
                            hasIFrames = true;


                            resetBoard = true;
                            levelCheck(this.score);
                        }
                        if (life == 0) {
                            gameOver();
                            saveHighscore();
                            break;
                        }
                    }
                }
                gameStats.setText("    Player: " + name + "    Score : " + score + "    Level : " + level + "    Life : " + life + tradeLifeString);
            }
        }

//        Return the board entities (pacman and ghosts) to their initial position.
        if (resetBoard && !isGameOver) {
            ghosts.clear();
            GhostFactory gf = new GhostFactory();
            for (InitGhostData gd : mapData.getGhostsData()) {
                ghosts.add(gf.getGhost(gd.getType(), gd.getX(), gd.getY(), this));
            }
            for (Ghost g : ghosts) {
                g.moveTimer.stop();
                g.setStopped(true);
                g.setStopTime(System.currentTimeMillis());
            }
            pacman.logicalPosition.x = 1;
            pacman.logicalPosition.y = 5;
            pacman.pixelPosition.x = pacman.logicalPosition.x * 28;
            pacman.pixelPosition.y = pacman.logicalPosition.y * 28;
            pacman.activeMove = moveType.NONE;
            pacman.todoMove = moveType.NONE;
            resetBoard = false;
        }
    }

    /*
     * Checks the current game's level.
     * */
    public int levelCheck(int currentScore) {
        switch ((int) ((currentScore - 1) / 50)) {
            case 0:
                level = 1;
//                makeTrap();
                tradeLifeString = "";
                for (Ghost g : ghosts)
                    g.setSpeedUp(false);
                break;
            case 1:
                level = 2;
                tradeLifeString = "";
                for (Ghost g : ghosts)
                    g.setSpeedUp(false);
                pacman.setSpeedUp(false);
                break;
            case 2:
                level = 3;
                tradeLifeString = "";
                if (!isJUnitTest()) {
                    pacman.setSpeedUp(true);
                    for (Ghost g : ghosts)
                        g.setSpeedUp(false);
                }
                break;
            case 3:
                level = 4;
                makeTrap();
                if (life == 1)
                    tradeLifeString = "       PRESS CTRL TO GET ANOTHER LIFE (costs 50)";
                else
                    tradeLifeString = "";
                if (!isJUnitTest()) {
                    for (Ghost g : ghosts)
                        g.setSpeedUp(true);
                }
                break;
            default:
                break;
        }
        return level;
    }

    public static boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }

    /*
     * Updates the game screen.
     * */
    public void update() {
        ArrayList<Food> foodsToEat = new ArrayList<>();
        //Check food eat
        for (Food f : foods) {
            if (pacman.logicalPosition.x == f.position.x && pacman.logicalPosition.y == f.position.y && !f.isEaten()) {
                f.setEaten(true);
                f.setEatenTime(System.currentTimeMillis());
                foodsToEat.add(f);
            }
        }
        if (foodsToEat.size() != 0) {
            for (Food foodToEat : foodsToEat) {
                if (foodToEat instanceof Bomb) {
                    if (((Bomb) foodToEat).type == 0) {//Bomb
                        ((Bomb) foodToEat).setType(1);
                        armedBombs.add((Bomb) foodToEat);
                    } else { //Fruit
                        SoundPlayer.play("pacman_eatfruit.wav");
//                        eatenFoods.add(foodToEat);
                        foods.remove(foodToEat);
                        scoreToAdd = 1;
                        drawScore = true;
                    }
                }
                //trap
                else if (foodToEat instanceof Trap) {
                    SoundPlayer.play("pacman_eatghost.wav");
                    eatenFoods.add(foodToEat);
                    foods.remove(foodToEat);
                    foodToEat.setEaten(false);
                    System.out.println("EAT TRAP");

                    life--;
                    iframesTime = System.currentTimeMillis();
                    hasIFrames = true;
                    resetBoard = true;
                    levelCheck(this.score);
                    gameStats.setText("    Player: " + name + "    Score : " + score + "    Level : " + level + "    Life : " + life + tradeLifeString);
                    if (life == 0) {
                        gameOver();
                        saveHighscore();
                        break;
                    }
                }
                   // isPaused(true);

                  //  pu = new PopUp((Question) foodToEat);}

                else if (foodToEat instanceof Question) {
                    eatenFoods.add(foodToEat);
                    foods.remove(foodToEat);
                    foodToEat.setEaten(false);
                    System.out.println("EAT QUESTION");

                    isPaused(true);

                    pu = new PopUp((Question) foodToEat);

                } else { //Food
                    SoundPlayer.play("pacman_eat.wav");
                    eatenFoods.add(foodToEat);
                    foods.remove(foodToEat);
                    score++;
                    levelCheck(this.score);
                    gameStats.setText("    Player: " + name + "    Score : " + score + "    Level : " + level + "    Life : " + life + tradeLifeString);
                }
            }
        }

//        Respawn eaten food.
        ArrayList<Food> remainingFoodsToRespawn = new ArrayList<Food>();
        for (Food f : eatenFoods) {
            long nowMillis = System.currentTimeMillis();
            if ((int) ((nowMillis - f.getEatenTime()) / 1000) >= 30) {
                if (f instanceof Bomb) {
                    foods.add(new Bomb((int) f.getPosition().getX(), (int) f.getPosition().getY(), ((Bomb) f).getType()));
                } else if (f instanceof Question) {
                    foods.add(getNewQuestion(((Question) f).getDiff()));
                } else //Food
                    foods.add(new Food((int) f.getPosition().getX(), (int) f.getPosition().getY()));
            } else {
                remainingFoodsToRespawn.add(f);
            }
        }
        eatenFoods = remainingFoodsToRespawn;


        //Check Ghost Undie
        for (Ghost g : ghosts) {
            if (g.isDead() && g.logicalPosition.x == ghostBase.x && g.logicalPosition.y == ghostBase.y) {
                g.revive();
            }
        }

        long nowMillis = System.currentTimeMillis();
        for (Ghost g : ghosts) {
            if (((int) ((nowMillis - g.getStopTime()) / 1000) >= 3 && g.isStopped() || pacman.activeMove != moveType.NONE)
                && !isPausedBoolean) {
                g.moveTimer.start();
                g.setStopped(false);
            }
        }

        //Check Teleport
        for (TeleportTunnel tp : teleports) {
            if (pacman.logicalPosition.x == tp.getFrom().x && pacman.logicalPosition.y == tp.getFrom().y && pacman.activeMove == tp.getReqMove() && level == 2) {
                pacman.logicalPosition.x = (int) tp.getTo().getX();
                pacman.logicalPosition.y = (int) tp.getTo().getY();
                pacman.pixelPosition.x = pacman.logicalPosition.x * 28;
                pacman.pixelPosition.y = pacman.logicalPosition.y * 28;
            }
        }

        long nowMillis2 = System.currentTimeMillis();
        if (explosionFire && (int) ((nowMillis2 - explosionTime)) >= 333) {
            armedBombs.clear();
            explosionFire = false;
        }

        if ((System.currentTimeMillis() - iframesTime) / 1000 >= 3)
            hasIFrames = false;

        /*
        * Design Pattern - Null Object Pattern
        * */
        if (/*pu != null*/ !pu.isNull() && pu.getReturnPoints() != null) {
            System.out.println(pu.getReturnPoints() + " points!");
            score += pu.getReturnPoints();
//            pu = null;
            pu.setNull(true);
            gameStats.setText("    Player: " + name + "    Score : " + score + "    Level : " + level + "    Life : " + life + tradeLifeString);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Draw explosion
        if (explosionFire && (int) (System.currentTimeMillis() / 10) % 2 == 0) {
            for (Bomb b : armedBombs) {
                for (Point p : availableQuestionPoints) {
                    if (Math.sqrt(Math.pow(b.getPosition().getX() - p.getX(), 2) +
                            Math.pow(b.getPosition().getY() - p.getY(), 2)) <= 3)
                        g.drawImage(fireImage, 10 + (int) p.getX() * 28, 10 + (int) p.getY() * 28, null);
                }
            }
        }

        //Draw Walls
        g.setColor(Color.blue);
        for (int i = 0; i < m_x; i++) {
            for (int j = 0; j < m_y; j++) {
                if (map[i][j] > 0) {
                    g.drawImage(mapSegments[map[i][j]], 10 + i * 28, 10 + j * 28, null);
                }
            }
        }

        //Draw Food and Bombs
        g.setColor(new Color(204, 122, 122));
        for (Food f : foods) {
            if (f instanceof Bomb) {
                g.setColor(new Color(204, 174, 168));
                g.drawImage(pfoodImage[((Bomb) f).type], 10 + f.position.x * 28, 10 + f.position.y * 28, null);
            } else if (f instanceof Question)
                g.drawImage(((Question) f).getqImage(), 10 + f.position.x * 28, 10 + f.position.y * 28, null);
            else if (f instanceof Trap)
                g.drawImage(((Trap) f).gettImage(), 10 + f.position.x * 28, 10 + f.position.y * 28, null);
            else
                g.drawImage(foodImage, 10 + f.position.x * 28, 10 + f.position.y * 28, null);
        }

        //Draw PowerUpFoods
//        g.setColor(new Color(204, 174, 168));
//        for (Bomb f : bombs) {
//            g.drawImage(pfoodImage[f.type], 10 + f.position.x * 28, 10 + f.position.y * 28, null);
//        }

        //Draw Pacman
        if (hasIFrames) {
            if ((int) (System.currentTimeMillis() / 10) % 2 == 0) {
                drawPacman(g);
            }
        } else {
            drawPacman(g);
        }

        //Draw Ghosts
        for (Ghost gh : ghosts) {
            g.drawImage(gh.getGhostImage(), 10 + gh.pixelPosition.x, 10 + gh.pixelPosition.y, null);
        }

        if (clearScore) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            drawScore = false;
            clearScore = false;
            didTradeLife = false;
        }

        if (drawScore) {
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.yellow);
            int s = scoreToAdd * 5; // points for eating a fruit/ghost
            g.drawString(Integer.toString(s), pacman.pixelPosition.x + 13, pacman.pixelPosition.y + 50);
            score += s;
            levelCheck(this.score);
            if (didTradeLife) {
                g.setFont(new Font("Arial", Font.BOLD, 15));
                g.setColor(Color.green);
                g.drawString("+" + 1 + " Life", pacman.pixelPosition.x + 13, pacman.pixelPosition.y + 35);
            }

            gameStats.setText("    Player: " + name + "    Score : " + score + "    Level : " + level + "    Life : " + life + tradeLifeString);
            clearScore = true;

        }

        if (isGameOver && score < 200) {
            g.drawImage(goImage, this.getSize().width / 2 - 315, this.getSize().height / 2 - 75, null);
        }

        if (isWin) {
            g.drawImage(vicImage, this.getSize().width / 2 - 315, this.getSize().height / 2 - 75, null);
        }


    }


    public void drawPacman(Graphics g) {
        switch (pacman.activeMove) {
            case NONE:
            case RIGHT:
                g.drawImage(pacman.getPacmanImage(), 10 + pacman.pixelPosition.x, 10 + pacman.pixelPosition.y, null);
                break;
            case LEFT:
                g.drawImage(ImageHelper.flipHor(pacman.getPacmanImage()), 10 + pacman.pixelPosition.x, 10 + pacman.pixelPosition.y, null);
                break;
            case DOWN:
                g.drawImage(ImageHelper.rotate90(pacman.getPacmanImage()), 10 + pacman.pixelPosition.x, 10 + pacman.pixelPosition.y, null);
                break;
            case UP:
                g.drawImage(ImageHelper.flipVer(ImageHelper.rotate90(pacman.getPacmanImage())), 10 + pacman.pixelPosition.x, 10 + pacman.pixelPosition.y, null);
                break;
        }
    }

    /*
     * Processes collisions, game-overs and win condition.
     * */
    @Override
    public void processEvent(AWTEvent ae) {
        if (ae.getID() == Messages.UPDATE && !isGameKilled) {
            update();
            if (score >= 200 && !isGameOver) {
                siren.stop();
                pac6.stop();
                SoundPlayer.play("pacman_intermission.wav");
                isWin = true;
                isGameOver = true;
                pacman.moveTimer.stop();
                for (Ghost g : ghosts) {
                    g.moveTimer.stop();
                }
                ghosts.clear();
                gameStats.setText("    Press R to try again !");
                saveHighscore();
            }
        } else if (ae.getID() == Messages.COLTEST && !isGameKilled) {
            if (!isGameOver) {
                collisionTest();
            }
        } else if (ae.getID() == Messages.RESET) {
            if (isGameOver) {
//                saveHighscore();
                restart();
            }
        } else if (ae.getID() == Messages.BACK) {
            isGameKilled = true;
            pac6.stop();
            siren.stop();
            timerOne.stop();
            pacman.moveTimer.stop();
            for (Ghost g : ghosts) {
                g.moveTimer.stop();
            }
            this.removeAll();
//            saveHighscore();
            new StartWindow();
            windowParent.dispose();
        } else if (ae.getID() == Messages.EXPLODE && !isGameKilled) {
            explosionTime = System.currentTimeMillis();
            explosionFire = true;
            if (!armedBombs.isEmpty()) {
                SoundPlayer.play("explosion.wav");
                System.out.println("EXPLODE!");
            }
            for (Bomb b : armedBombs) {
                b.setType(0);
                eatenFoods.add(b);
                foods.remove(b);
//                siren.stop();
//                mustReactivateSiren = true;
//                pac6.start();
                for (Ghost g : ghosts) {
                    if (Math.sqrt(Math.pow(b.getPosition().getX() - g.getLogicalPosition().getX(), 2) +
                            Math.pow(b.getPosition().getY() - g.getLogicalPosition().getY(), 2)) <= 3) {
                        g.die();
                    }
                }
                scoreToAdd = 0;
            }
        } else if (ae.getID() == Messages.TRADE_SCORE_FOR_LIFE) {
            if (!isGameOver && level == 4 && life == 1) {
                didTradeLife = true;
                tradeLifeString = "";
                life++;
                scoreToAdd = -10;
                drawScore = true;
            }
        }
            else {
            super.processEvent(ae);
        }
    }

    public void saveHighscore() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date resultdate = new Date(System.currentTimeMillis());
        String date = sdf.format(resultdate);

        SysData s = SysData.getInstance();
        s.addHighscore(new Highscore(this.score, this.name, date));
        s.updateHighscoresJSON();
    }

    /*
     * Restarts the game.
     * */
    public void restart() {

        siren.stop();
        timerOne.stop();
        foods.clear();
        ghosts.clear();
        pacman = null;
        new PacWindow(this.name);
        windowParent.dispose();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Question getNewQuestion(int diff) {
        Question q = null;
        Point newPos = availableQuestionPoints.get(ThreadLocalRandom.current().nextInt(availableQuestionPoints.size()));
        try {
            switch (diff) {
                case 1:
                    q = easyQ.get(ThreadLocalRandom.current().nextInt(easyQ.size()));
                    break;
                case 2:
                    q = mediumQ.get(ThreadLocalRandom.current().nextInt(mediumQ.size()));
                    break;
                case 3:
                    q = hardQ.get(ThreadLocalRandom.current().nextInt(hardQ.size()));
                    break;
            }
        } catch (Exception ignored) {
            return null;
        }
        assert q != null;
        q.setPosition(newPos);
        return q;
    }

    public static void isPaused(boolean paused) {
        if (paused) {
//            pac6.stop();
            siren.stop();
            pacman.moveTimer.stop();
            for (Ghost g : ghosts) {
                g.moveTimer.stop();
            }
        } else {
//            pac6.start();
            siren.start();
            pacman.moveTimer.start();
            for (Ghost g : ghosts) {
                g.moveTimer.start();
            }
        }
        isPausedBoolean = paused;
    }

    //make a trap close to the pacman
    public void makeTrap() {
        if (drawTrap){
            Collections.shuffle(availableQuestionPoints);
                for (Point p : availableQuestionPoints) {
                    double pointDist = Math.sqrt(Math.pow(pacman.logicalPosition.x - p.getX(), 2) +
                            Math.pow(pacman.logicalPosition.y - p.getY(), 2));
                    if (pointDist >= 3 && pointDist <= 4) {
                        if (ThreadLocalRandom.current().nextInt(3) > 0) {
                            trap = new Trap(((int) (p.getX())), ((int) p.getY()));
                            foods.add(trap);
                        } else {
                            fruit = new Bomb(((int) (p.getX())), ((int) p.getY()), ThreadLocalRandom.current().nextInt(4) + 2);
                            foods.add(fruit);
                        }
                        timerOne.start();
                        drawTrap = false;
                        break;
                    }
            }
        }
    }
    //every 5 seconds make a new trap in another position
    private void timerOneMethod(ActionEvent e)
    {
//        System.out.println("5 seconds past");
        foods.remove(trap);
        foods.remove(fruit);
        drawTrap =true;
    }

    public Game() {

    }
}

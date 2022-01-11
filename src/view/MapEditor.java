package view;

import controller.MapData;
import misc.StringHelper;
import model.*;
import view.FancyButton;
import view.PacWindow;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MapEditor extends JFrame {
    /**
     * This page allow the user to edit the map of the game
     * and then play with the new map
     */
    public MapEditor() {
        setSize(650, 400);
        setTitle("Map Editor");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.black);

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BorderLayout());
        sideBar.setBackground(Color.black);
        JPanel ghostSelection = new JPanel();
        ghostSelection.setLayout(new BoxLayout(ghostSelection, BoxLayout.Y_AXIS));
        ghostSelection.setBackground(Color.black);
        JLabel l0 = new JLabel("= : Blank Space (without Food)");
        JLabel l1 = new JLabel("_ : Blank Space (with Food)");
        JLabel l2 = new JLabel("X : Wall");
        JLabel l3 = new JLabel("Y : Semi-Wall (Passable by Ghosts)");
        JLabel l4 = new JLabel("P : Pacman Start Position");
        JLabel l5 = new JLabel("1 : Red Ghost (Chaser)");
        JLabel l6 = new JLabel("2 : Pink Ghost (Traveler)");
        JLabel l7 = new JLabel("3 : Cyan Ghost (Patrol)");
        JLabel l8 = new JLabel("F : Fruit");
        JLabel l9 = new JLabel("B : Ghost Base");


        l0.setForeground(Color.yellow);
        l1.setForeground(Color.yellow);
        l2.setForeground(Color.yellow);
        l3.setForeground(Color.yellow);
        l4.setForeground(Color.yellow);
        l5.setForeground(Color.yellow);
        l6.setForeground(Color.yellow);
        l7.setForeground(Color.yellow);
        l8.setForeground(Color.yellow);
        l9.setForeground(Color.yellow);

        ghostSelection.add(l0);
        ghostSelection.add(l1);
        ghostSelection.add(l2);
        ghostSelection.add(l3);
        ghostSelection.add(l4);
        ghostSelection.add(l5);
        ghostSelection.add(l6);
        ghostSelection.add(l7);
        ghostSelection.add(l8);
        ghostSelection.add(l9);

        setLayout(new BorderLayout());
        sideBar.add(ghostSelection, BorderLayout.NORTH);
        getContentPane().add(sideBar, BorderLayout.EAST);

        JTextArea ta = new JTextArea();
        ta.setBackground(Color.black);
        ta.setForeground(Color.yellow);
        ta.setText("XXXXXXXXXX\n"
                + "XP_______X\n"
                + "X________X\n"
                + "X________X\n"
                + "XXXXXXXXXX");
        ta.setBorder(new CompoundBorder(new CompoundBorder(new EmptyBorder(20, 10, 20, 10), new LineBorder(Color.yellow)), new EmptyBorder(10, 10, 10, 10)));
        getContentPane().add(ta);


        FancyButton backButton = new FancyButton("Back", 30f);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartWindow();
                dispose();
            }
        });
        sideBar.add(backButton, BorderLayout.CENTER);

        FancyButton startButton = new FancyButton("Start Game", 30f);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new PacWindow(compileMap(ta.getText()));
                } catch (ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Map must be rectangular!");
                }
            }
        });
        sideBar.add(startButton, BorderLayout.SOUTH);


        setVisible(true);
    }

    //Resolve Map
    public static MapData compileMap(String input) throws ArrayIndexOutOfBoundsException {
        int mx = input.indexOf('\n');
        int my = StringHelper.countLines(input);
        System.out.println("Making Map " + mx + "x" + my);

        MapData customMap = new MapData(mx, my);
        customMap.setCustom(true);
        int[][] map = new int[mx][my];

        //Pass Map As Argument
        int i = 0;
        int j = 0;
        for (char c : input.toCharArray()) {
            if (c == '1') {
                map[i][j] = 0;
                customMap.getGhostsData().add(new InitGhostData(i, j, ghostType.RED));
                customMap.getFoodPositions().add(new Food(i, j));
                customMap.getAvailablePointsForQuestion().add(new Point(i, j));
            }
            if (c == '2') {
                map[i][j] = 0;
                customMap.getGhostsData().add(new InitGhostData(i, j, ghostType.PINK));
                customMap.getFoodPositions().add(new Food(i, j));
                customMap.getAvailablePointsForQuestion().add(new Point(i, j));
            }
            if (c == '3') {
                map[i][j] = 0;
                customMap.getGhostsData().add(new InitGhostData(i, j, ghostType.CYAN));
                customMap.getFoodPositions().add(new Food(i, j));
                customMap.getAvailablePointsForQuestion().add(new Point(i, j));
            }
            if (c == 'P') {
                map[i][j] = 0;
                customMap.setPacmanPosition(new Point(i, j));
                customMap.getAvailablePointsForQuestion().add(new Point(i, j));
            }
            if (c == 'X') {
                map[i][j] = 23;
            }
            if (c == 'Y') {
                map[i][j] = 26;
            }
            if (c == '_') {
                map[i][j] = 0;
                customMap.getFoodPositions().add(new Food(i, j));
                customMap.getAvailablePointsForQuestion().add(new Point(i, j));
            }
            if (c == '=') {
                map[i][j] = 0;
            }
            if (c == 'O') {
                map[i][j] = 0;

                customMap.getFoodPositions().add(new Bomb(i, j, 0));
            }
            if (c == 'F') {
                map[i][j] = 0;
                customMap.getFoodPositions().add(new Bomb(i, j, ThreadLocalRandom.current().nextInt(4) + 2));

            }
            if (c == 'B') {
                map[i][j] = 0;
                customMap.setGhostBasePosition(new Point(i, j));
            }
            if (c == 'L') {
                map[i][j] = 0;
                ArrayList<TeleportTunnel> teleports = customMap.getTeleports();
                teleports.add(new TeleportTunnel(i, j, 26, 14, moveType.LEFT));
                customMap.setTeleports(teleports);
            }
            if (c == 'R') {
                map[i][j] = 0;
                ArrayList<TeleportTunnel> teleports = customMap.getTeleports();
                teleports.add(new TeleportTunnel(i, j, 0, 14, moveType.RIGHT));
                customMap.setTeleports(teleports);
            }
            i++;
            if (c == '\n') {
                j++;
                i = 0;
            }
        }



        customMap.setMap(map);
        customMap.setCustom(true);
        System.out.println("Map Read OK !");
        return customMap;

}

}

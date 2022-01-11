package view;

import controller.SysData;
import model.Highscore;
import model.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
/**
 * This page will display the best scores include the name of the user and the
 * date of the game.
 */
public class HighScoresScreen extends JFrame {


    // Constructor
    HighScoresScreen() {
        SysData s = SysData.getInstance();
        setSize(600, 400);
        setTitle("High Scores");
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ArrayList<Highscore> hs = s.getHighscores();
        setForeground(Color.yellow);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};

        getContentPane().setLayout(gridBagLayout);

        FancyButton backBTN = new FancyButton("Back", 20f);
        GridBagConstraints gbc_backBTN = new GridBagConstraints();
        gbc_backBTN.insets = new Insets(0, 0, 5, 5);
        gbc_backBTN.gridx = 1;
        gbc_backBTN.gridy = 1;
        getContentPane().add(backBTN, gbc_backBTN);

        ArrayList<String> usersArray = new ArrayList<>();
        ArrayList<String> usersScore = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();

        //if the user got 200 points - add an icon of a cup to the name
        for (int i = 0; i < Math.min(10, hs.size()); i++) {
            if (hs.get(i).getScore() >= 200)
                usersArray.add(i + 1 + ") " + hs.get(i).getUsername() + " 🏆");
            else
                usersArray.add(i + 1 + ") " + hs.get(i).getUsername());
            usersScore.add(hs.get(i).getScore().toString() + "               ");
            dates.add(hs.get(i).getDate().toString());
        }
        JList usersList = new JList(usersArray.toArray());
        usersList.setBackground(Color.black);
        usersList.setForeground(Color.ORANGE);
        GridBagConstraints gbc_usersList = new GridBagConstraints();
        gbc_usersList.insets = new Insets(0, 80, 1, 1);
        gbc_usersList.gridx = 3;
        gbc_usersList.gridy = 2;


        JList usersScores = new JList(usersScore.toArray());
        usersScores.setBackground(Color.black);
        usersScores.setForeground(Color.ORANGE);
        GridBagConstraints gbc_usersScores = new GridBagConstraints();
        gbc_usersScores.insets = new Insets(0, 50, 1, 0);
        gbc_usersScores.gridx = 4;
        gbc_usersScores.gridy = 2;


        JList datesList = new JList(dates.toArray());
        datesList.setBackground(Color.black);
        datesList.setForeground(Color.ORANGE);
        GridBagConstraints gbc_datesList = new GridBagConstraints();
        gbc_datesList.insets = new Insets(0, 0, 1, 200);
        gbc_datesList.gridx = 5;
        gbc_datesList.gridy = 2;


        getContentPane().add(usersList, gbc_usersList);
        getContentPane().add(usersScores, gbc_usersScores);
        getContentPane().add(datesList, gbc_datesList);

        backBTN.addActionListener(e -> {
            new StartWindow();
            dispose();
        });


        pack();
        setVisible(true);
    }

}

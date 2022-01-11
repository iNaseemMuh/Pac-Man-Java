package view;

import view.FancyButton;
import view.StartWindow;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instructions extends JFrame {

    public Instructions(){
        setSize(650,400);
        setTitle("Instructions");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.black);
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BorderLayout());
        sideBar.setBackground(Color.black);

        setLayout(new BorderLayout());
        getContentPane().add(sideBar,BorderLayout.EAST);


        JTextArea ta = new JTextArea();
        ta.setBackground(Color.black);
        ta.setForeground(Color.yellow);
        ta.setText("Playing Pacman is easy to learn and hard to master. Simply score as many \n" +
                "points as you can eating the small dots all around the maze. 1 point per dot, \n" +
                "Big points come when you eat a Question Mark worth 1-3 points depending on the \n" +
                "difficulty of the question located randomly around the maze. \n" +
                "Warning!! incorrect answers will lower the score depending on the level of difficulty. \n" +
                "5 points reward for eating a fruit. \n" +
                "You can gather a possible 50 points for each level (4 levels in total of 200 points).\n" +
                "Big flashing dots called Bombs located in each corner of the maze, once eaten,\n" +
                "it turns red and you can explode it by clicking space to kill a ghost.\n" +
                "Make sure to avoid ghosts at all times, once you make contact with a ghost\n" +
                "it will cost you a life, you have a total of 3 lives.\n\n" +
                "Key bindings:\nMovement:             Back:             Explode bomb(s):             Trade score for life:\n" +
                "       [↑]\n" +
                "[←] [↓] [→]               [Esc]               [Space-bar]                        [Ctrl]");

        ta.setBorder(new CompoundBorder(new CompoundBorder(new EmptyBorder(20,10,20,10),new LineBorder(Color.yellow)),new EmptyBorder(10,10,10,10)));
        getContentPane().add(ta);

        FancyButton backButton = new FancyButton("Back", 20f);
        backButton.addActionListener(e -> {
            new StartWindow();
            dispose();
        });
        sideBar.add(backButton,BorderLayout.SOUTH);

        setVisible(true);

    }
}

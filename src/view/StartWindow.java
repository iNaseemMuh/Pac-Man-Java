package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
/**
The main page of the game. the user can start the game, read the instructions, customize the map, edit
 the questions and view the highscores
 */
public class StartWindow extends JFrame {

    public StartWindow(){
        setSize(600,400);
        setTitle("Pacman");
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon logo = new ImageIcon();
        try {
            logo = new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/images/pacman_logo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //  Register Custom fonts
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/resources/fonts/crackman.ttf")));
        } catch (IOException|FontFormatException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        getContentPane().add(new JLabel(logo),BorderLayout.NORTH);

        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);


        buttonsC.setLayout(new BoxLayout(buttonsC,BoxLayout.Y_AXIS));

        FancyButton startButton = new FancyButton("Start Game", 30f);
        FancyButton customButton = new FancyButton("Customize Game", 30f);
        FancyButton editButton = new FancyButton("Edit Questions", 30f);
        FancyButton highButton = new FancyButton("Highscores", 30f);
        FancyButton instButton = new FancyButton("Instructions", 30f);

        startButton.addActionListener(e -> {
            editName en = new editName();
            dispose();
        });
        customButton.addActionListener(e -> {
            MapEditor me = new MapEditor();
            dispose();
        });

        instButton.addActionListener(e -> {
            Instructions me = new Instructions();
            dispose();
        });
        
        editButton.addActionListener(e -> {
            QuestionScreen qs = new QuestionScreen();
            dispose();
        });

        highButton.addActionListener(e -> {
            HighScoresScreen hs = new HighScoresScreen();
            dispose();
        });


        buttonsC.add(startButton);
        buttonsC.add(customButton);
        buttonsC.add(editButton);
        buttonsC.add(highButton);
        buttonsC.add(instButton);

        getContentPane().add(buttonsC);

        setVisible(true);
    }
}

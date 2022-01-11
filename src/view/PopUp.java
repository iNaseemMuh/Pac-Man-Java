/*
 * Created by JFormDesigner on Mon Dec 13 11:03:47 IST 2021
 */

package view;

import controller.Game;
import misc.SoundPlayer;
import model.Question;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * This page is a popup that will display a question and will appear after
 * the user eat a question
 */
public class PopUp extends JFrame {
    public PopUp(Question q) {
        initComponents(q);
    }

    private void initComponents(Question q) {
        int pointsForCorrectAnswer = 0;
        int pointsForWrongAnswer = 0;
        String title = "";
        //get the level of the question and set the points and the title
        switch (q.getDiff()) {
            case 1:
                getContentPane().setBackground(Color.white);
                pointsForCorrectAnswer = 1;
                pointsForWrongAnswer = 10;
                title = "Easy Question";
                break;
            case 2:
                getContentPane().setBackground(Color.yellow);
                pointsForCorrectAnswer = 2;
                pointsForWrongAnswer = 20;
                title = "Medium Question";
                break;
            case 3:
                getContentPane().setBackground(Color.red);
                pointsForCorrectAnswer = 3;
                pointsForWrongAnswer = 30;
                title = "Hard Question";
                break;
        }
        String pointsForCorrectAnswerString = String.valueOf(pointsForCorrectAnswer);
        String pointsForWrongAnswerString = String.valueOf(pointsForWrongAnswer);
        setTitle(title);
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Omer Sharon
        question = new JLabel(q.getqBody());
        question.setFont(new Font(question.getFont().getName(), Font.PLAIN, 20));
        radioButton1 = new JRadioButton(q.getAnswers().get(0).aBody);
        radioButton2 = new JRadioButton(q.getAnswers().get(1).aBody);
        radioButton3 = new JRadioButton(q.getAnswers().get(2).aBody);
        radioButton4 = new JRadioButton(q.getAnswers().get(3).aBody);
        btnSave = new JButton();
        btnContinue = new JButton();
        outputLabel = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- question ----
        contentPane.add(question, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- radioButton1 ----
        radioButton1.setActionCommand(q.answers.get(0).isCorrect() ? "true" : "false");
        contentPane.add(radioButton1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- radioButton2 ----
        radioButton2.setActionCommand(q.answers.get(1).isCorrect() ? "true" : "false");
        contentPane.add(radioButton2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- radioButton3 ----
        radioButton3.setActionCommand(q.answers.get(2).isCorrect() ? "true" : "false");
        contentPane.add(radioButton3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- radioButton4 ----
        radioButton4.setActionCommand(q.answers.get(3).isCorrect() ? "true" : "false");
        contentPane.add(radioButton4, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        ButtonGroup answers = new ButtonGroup();
        answers.add(radioButton1);
        answers.add(radioButton2);
        answers.add(radioButton3);
        answers.add(radioButton4);

        int finalPointsForCorrectAnswer = pointsForCorrectAnswer;
        int finalPointsForWrongAnswer = pointsForWrongAnswer;
        //---- button5 ----
        btnSave.setText("save");
        contentPane.add(btnSave, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        btnSave.addActionListener(e -> {
            if (answers.getSelection() != null) {
                if (answers.getSelection().getActionCommand().equals("true")) {
                    SoundPlayer.play("correct_answer.wav");
                    outputLabel.setText("Correct Answer! You got " + pointsForCorrectAnswerString + " points!");
                    outputLabel.setForeground(Color.green);
                    setReturnPoints(finalPointsForCorrectAnswer);
                } else {
                    SoundPlayer.play("wrong_answer.wav");
                    outputLabel.setText("Wrong Answer :( You lost " + pointsForWrongAnswerString + " points");
                    outputLabel.setForeground(Color.black);
                    setReturnPoints(-finalPointsForWrongAnswer);
                }
                btnSave.setVisible(false);
                btnContinue.setVisible(true);
                pack();
            }
        });

        //---- button6 ----
        btnContinue.setText("continue");
        contentPane.add(btnContinue, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));
        btnContinue.setVisible(false);
        btnContinue.addActionListener(e -> {
            setVisible(false);
            Game.isPaused(false);
        });
        setVisible(true);
        pack();

        //---- label1 ----
//        outputLabel.setText("output message");
        contentPane.add(outputLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public Integer getReturnPoints() {
        return returnPoints;
    }

    public void setReturnPoints(Integer returnPoints) {
        this.returnPoints = returnPoints;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public PopUp() {

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Omer Sharon
    private JLabel question;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JButton btnSave;
    private JButton btnContinue;
    private JLabel outputLabel;

    private Integer returnPoints;
    private boolean isNull = false;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

package view;


import controller.Game;
import controller.SysData;
import misc.SoundPlayer;
import model.Answer;
import model.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpOld extends JFrame {
    Integer returnPoints;
    boolean isNull = false;

    public PopUpOld(Question q) {
        int pointsForCorrectAnswer = 0;
        int pointsForWrongAnswer = 0;
        String title = "";
        JDialog jd = new JDialog();
        switch (q.getDiff()) {
            case 1:
                jd.getContentPane().setBackground(Color.white);
                pointsForCorrectAnswer = 1;
                pointsForWrongAnswer = 10;
                title = "Easy Question";
                break;
            case 2:
                jd.getContentPane().setBackground(Color.yellow);
                pointsForCorrectAnswer = 2;
                pointsForWrongAnswer = 20;
                title = "Medium Question";
                break;
            case 3:
                jd.getContentPane().setBackground(Color.red);
                pointsForCorrectAnswer = 3;
                pointsForWrongAnswer = 30;
                title = "Hard Question";
                break;
        }
        String pointsForCorrectAnswerString = String.valueOf(pointsForCorrectAnswer);
        String pointsForWrongAnswerString = String.valueOf(pointsForWrongAnswer);
        jd.setTitle(title);

        jd.setBounds(500, 300, 400, 300);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{35, 55, 81, 19, 59, 81, 0};
        gridBagLayout.rowHeights = new int[]{23, 23, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        jd.getContentPane().setLayout(gridBagLayout);
        JLabel question = new JLabel(q.getqBody());

        GridBagConstraints gbc_question = new GridBagConstraints();
        gbc_question.anchor = GridBagConstraints.WEST;
        gbc_question.insets = new Insets(0, 0, 5, 5);
        gbc_question.gridx = 2;
        gbc_question.gridy = 1;
        jd.getContentPane().add(question, gbc_question);
        JRadioButton answer1 = new JRadioButton(q.getAnswers().get(0).aBody);
        answer1.setActionCommand(q.answers.get(0).isCorrect() ? "true" : "false");
        GridBagConstraints gbc_answer1 = new GridBagConstraints();
        gbc_answer1.anchor = GridBagConstraints.NORTHWEST;
        gbc_answer1.insets = new Insets(0, 0, 5, 5);
        gbc_answer1.gridx = 2;
        gbc_answer1.gridy = 2;
        jd.getContentPane().add(answer1, gbc_answer1);
        JRadioButton answer2 = new JRadioButton(q.getAnswers().get(1).aBody);
        answer2.setActionCommand(q.answers.get(1).isCorrect() ? "true" : "false");
        GridBagConstraints gbc_answer2 = new GridBagConstraints();
        gbc_answer2.anchor = GridBagConstraints.NORTHWEST;
        gbc_answer2.insets = new Insets(0, 0, 5, 5);
        gbc_answer2.gridwidth = 2;
        gbc_answer2.gridx = 2;
        gbc_answer2.gridy = 3;
        jd.getContentPane().add(answer2, gbc_answer2);
        JRadioButton answer3 = new JRadioButton(q.getAnswers().get(2).aBody);
        GridBagConstraints gbc_answer3 = new GridBagConstraints();
        answer3.setActionCommand(q.answers.get(2).isCorrect() ? "true" : "false");
        gbc_answer3.anchor = GridBagConstraints.NORTHWEST;
        gbc_answer3.insets = new Insets(0, 0, 5, 5);
        gbc_answer3.gridx = 2;
        gbc_answer3.gridy = 4;
        jd.getContentPane().add(answer3, gbc_answer3);
        JRadioButton answer4 = new JRadioButton(q.getAnswers().get(3).aBody);
        answer4.setActionCommand(q.answers.get(3).isCorrect() ? "true" : "false");
        GridBagConstraints gbc_answer4 = new GridBagConstraints();
        gbc_answer4.anchor = GridBagConstraints.NORTHEAST;
        gbc_answer4.insets = new Insets(0, 0, 5, 5);
        gbc_answer4.gridx = 2;
        gbc_answer4.gridy = 5;
        jd.getContentPane().add(answer4, gbc_answer4);

        ButtonGroup answers = new ButtonGroup();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);

        JLabel lblNewLabel = new JLabel();
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel.gridx = 2;
        gbc_lblNewLabel.gridy = 7;
        jd.getContentPane().add(lblNewLabel, gbc_lblNewLabel);


        FancyButton btnSave = new FancyButton("Save", 20f);
        btnSave.setForeground(Color.BLACK);
        GridBagConstraints gbc_btnSave = new GridBagConstraints();
        gbc_btnSave.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnSave.insets = new Insets(0, 0, 5, 5);
        gbc_btnSave.gridx = 2;
        gbc_btnSave.gridy = 6;
        jd.getContentPane().add(btnSave, gbc_btnSave);

        FancyButton btnContinue = new FancyButton("Continue", 20f);
        btnContinue.setForeground(Color.BLACK);
        GridBagConstraints gbc_btnContinue = new GridBagConstraints();
        gbc_btnContinue.insets = new Insets(0, 0, 5, 5);
        gbc_btnContinue.gridx = 3;
        gbc_btnContinue.gridy = 6;
        btnContinue.setVisible(false);
        jd.getContentPane().add(btnContinue, gbc_btnContinue);


        int finalPointsForCorrectAnswer = pointsForCorrectAnswer;
        int finalPointsForWrongAnswer = pointsForWrongAnswer;
        btnSave.addActionListener(e -> {
            if (answers.getSelection().getActionCommand().equals("true")) {
                SoundPlayer.play("correct_answer.wav");
                lblNewLabel.setText("Correct Answer! You got " + pointsForCorrectAnswerString + " points!");
                lblNewLabel.setForeground(Color.green);
                setReturnPoints(finalPointsForCorrectAnswer);
            } else {
                SoundPlayer.play("wrong_answer.wav");
                lblNewLabel.setText("Wrong Answer :( You lost " + pointsForWrongAnswerString + " points");
                lblNewLabel.setForeground(Color.black);
                setReturnPoints(-finalPointsForWrongAnswer);
            }
            btnSave.setVisible(false);
            btnContinue.setVisible(true);
            jd.pack();
        });

        btnContinue.addActionListener(e -> {
            jd.setVisible(false);
            Game.isPaused(false);
        });
        jd.setVisible(true);
        jd.pack();
    }

    public PopUpOld() {
    }

    public Integer getReturnPoints() {
        return returnPoints;
    }

    public void setReturnPoints(int returnPoints) {
        this.returnPoints = returnPoints;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }
}
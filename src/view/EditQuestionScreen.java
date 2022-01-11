package view;

import controller.SysData;
import model.Answer;
import model.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

/**
 *This page will let the user edit the selected question from previous page.
 *the question will be updated in the list
 */
public class EditQuestionScreen extends JFrame{
    private JTextField quesionBodyTF;
    private JTextField answer1;
    private JTextField answer2;
    private JTextField answer3;
    private JTextField answer4;
    private JLabel levelChecklbl;
    private JLabel answerChecklbl;
    private ButtonGroup level = new ButtonGroup();
    private ButtonGroup answers = new ButtonGroup();
    String emptyField = "Must fill this field";
    public EditQuestionScreen(Question question) {
        SysData s = SysData.getInstance();
        setSize(600,400);
        setTitle("Edit Question");
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);


        FancyButton btnBack = new FancyButton("Back", 20f);
        GridBagConstraints gbc_btnBack = new GridBagConstraints();
        gbc_btnBack.insets = new Insets(0, 0, 5, 5);
        gbc_btnBack.gridx = 4;
        gbc_btnBack.gridy = 2;
        getContentPane().add(btnBack, gbc_btnBack);

        JLabel lblFillQuestion = new JLabel("Question Body");
        lblFillQuestion.setForeground(Color.ORANGE);
        GridBagConstraints gbc_lblFillQuestion = new GridBagConstraints();
        gbc_lblFillQuestion.insets = new Insets(0, 0, 5, 5);
        gbc_lblFillQuestion.anchor = GridBagConstraints.EAST;
        gbc_lblFillQuestion.gridx = 6;
        gbc_lblFillQuestion.gridy = 3;
        getContentPane().add(lblFillQuestion, gbc_lblFillQuestion);

        quesionBodyTF = new JTextField();
        quesionBodyTF.setText(question.qBody);
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.gridwidth = 2;
        gbc_textField.insets = new Insets(0, 0, 5, 5);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 7;
        gbc_textField.gridy = 3;
        getContentPane().add(quesionBodyTF, gbc_textField);
        quesionBodyTF.setColumns(10);

//        JButton btnClean = new JButton("Reset");
        FancyButton btnClean = new FancyButton("Reset", 20f);
        GridBagConstraints gbc_btnClean = new GridBagConstraints();
        gbc_btnClean.insets = new Insets(0, 0, 5, 5);
        gbc_btnClean.gridx = 9;
        gbc_btnClean.gridy = 3;
        getContentPane().add(btnClean, gbc_btnClean);

        levelChecklbl = new JLabel("");
        levelChecklbl.setForeground(Color.RED);
        GridBagConstraints gbc_levelChecklbl = new GridBagConstraints();
        gbc_levelChecklbl.insets = new Insets(0, 0, 5, 5);
        gbc_levelChecklbl.gridx = 5;
        gbc_levelChecklbl.gridy = 4;
        getContentPane().add(levelChecklbl, gbc_levelChecklbl);

        JRadioButton rdbtnEasy = new JRadioButton("Easy");
        if(question.diff==1){
            rdbtnEasy.setSelected(true);
        }
        rdbtnEasy.setActionCommand("1");
        rdbtnEasy.addActionListener(levelRadioButtonsListener);
        rdbtnEasy.setBackground(Color.BLACK);
        rdbtnEasy.setForeground(Color.ORANGE);
        GridBagConstraints gbc_rdbtnEasy = new GridBagConstraints();
        gbc_rdbtnEasy.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnEasy.gridx = 6;
        gbc_rdbtnEasy.gridy = 4;
        getContentPane().add(rdbtnEasy, gbc_rdbtnEasy);

        JRadioButton rdbtnMedium = new JRadioButton("Medium");
        if(question.diff==2){
            rdbtnMedium.setSelected(true);
        }
        rdbtnMedium.setActionCommand("2");
        rdbtnMedium.addActionListener(levelRadioButtonsListener);
        rdbtnMedium.setForeground(Color.ORANGE);
        rdbtnMedium.setBackground(Color.BLACK);
        GridBagConstraints gbc_rdbtnMedium = new GridBagConstraints();
        gbc_rdbtnMedium.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnMedium.gridx = 7;
        gbc_rdbtnMedium.gridy = 4;
        getContentPane().add(rdbtnMedium, gbc_rdbtnMedium);

        JRadioButton rdbtnHard = new JRadioButton("Hard");
        if(question.diff==3){
            rdbtnHard.setSelected(true);
        }
        rdbtnHard.setActionCommand("3");
        rdbtnHard.addActionListener(levelRadioButtonsListener);
        rdbtnHard.setBackground(Color.BLACK);
        rdbtnHard.setForeground(Color.ORANGE);
        GridBagConstraints gbc_rdbtnHard = new GridBagConstraints();
        gbc_rdbtnHard.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnHard.gridx = 8;
        gbc_rdbtnHard.gridy = 4;
        getContentPane().add(rdbtnHard, gbc_rdbtnHard);



        answer1 = new JTextField();
        answer1.setText(question.answers.get(0).aBody);
        GridBagConstraints gbc_answer1 = new GridBagConstraints();
        gbc_answer1.insets = new Insets(0, 0, 5, 5);
        gbc_answer1.fill = GridBagConstraints.HORIZONTAL;
        gbc_answer1.gridx = 7;
        gbc_answer1.gridy = 5;
        getContentPane().add(answer1, gbc_answer1);
        answer1.setColumns(10);

        JRadioButton rdbtnAnswer1 = new JRadioButton("");
        if(question.answers.get(0).isCorrect){
            rdbtnAnswer1.setSelected(true);
        }
        rdbtnAnswer1.setActionCommand(answer1.getText());
        rdbtnAnswer1.addActionListener(answersRadioButtonsListener);
        rdbtnAnswer1.setBackground(Color.BLACK);
        GridBagConstraints gbc_rdbtnAnswer1 = new GridBagConstraints();
        gbc_rdbtnAnswer1.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnAnswer1.gridx = 6;
        gbc_rdbtnAnswer1.gridy = 5;
        getContentPane().add(rdbtnAnswer1, gbc_rdbtnAnswer1);

        answer2 = new JTextField();
        answer2.setText(question.answers.get(1).aBody);
        GridBagConstraints gbc_answer2 = new GridBagConstraints();
        gbc_answer2.insets = new Insets(0, 0, 5, 5);
        gbc_answer2.fill = GridBagConstraints.HORIZONTAL;
        gbc_answer2.gridx = 7;
        gbc_answer2.gridy = 6;
        getContentPane().add(answer2, gbc_answer2);
        answer2.setColumns(10);

        JRadioButton rdbtnAnswer2 = new JRadioButton("");
        if(question.answers.get(1).isCorrect){
            rdbtnAnswer2.setSelected(true);
        }
        rdbtnAnswer2.setActionCommand(answer2.getText());
        rdbtnAnswer2.addActionListener(answersRadioButtonsListener);
        rdbtnAnswer2.setBackground(Color.BLACK);
        GridBagConstraints gbc_rdbtnAnswer2 = new GridBagConstraints();
        gbc_rdbtnAnswer2.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnAnswer2.gridx = 6;
        gbc_rdbtnAnswer2.gridy = 6;
        getContentPane().add(rdbtnAnswer2, gbc_rdbtnAnswer2);

        answer3 = new JTextField();
        answer3.setText(question.answers.get(2).aBody);
        GridBagConstraints gbc_answer3 = new GridBagConstraints();
        gbc_answer3.insets = new Insets(0, 0, 5, 5);
        gbc_answer3.fill = GridBagConstraints.HORIZONTAL;
        gbc_answer3.gridx = 7;
        gbc_answer3.gridy = 7;
        getContentPane().add(answer3, gbc_answer3);
        answer3.setColumns(10);

        JRadioButton rdbtnAnswer3 = new JRadioButton("");
        if(question.answers.get(2).isCorrect){
            rdbtnAnswer3.setSelected(true);
        }
        rdbtnAnswer3.setActionCommand(answer3.getText().toString());
        rdbtnAnswer3.addActionListener(answersRadioButtonsListener);
        rdbtnAnswer3.setBackground(Color.BLACK);
        GridBagConstraints gbc_rdbtnAnswer3 = new GridBagConstraints();
        gbc_rdbtnAnswer3.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnAnswer3.gridx = 6;
        gbc_rdbtnAnswer3.gridy = 7;
        getContentPane().add(rdbtnAnswer3, gbc_rdbtnAnswer3);

        answer4 = new JTextField();
        answer4.setText(question.answers.get(3).aBody);
        GridBagConstraints gbc_answer4 = new GridBagConstraints();
        gbc_answer4.insets = new Insets(0, 0, 5, 5);
        gbc_answer4.fill = GridBagConstraints.HORIZONTAL;
        gbc_answer4.gridx = 7;
        gbc_answer4.gridy = 8;
        getContentPane().add(answer4, gbc_answer4);
        answer4.setColumns(10);

        JRadioButton rdbtnAnswer4 = new JRadioButton("");
        if(question.answers.get(3).isCorrect){
            rdbtnAnswer4.setSelected(true);
        }
        rdbtnAnswer4.setActionCommand(answer4.getText());
        rdbtnAnswer4.addActionListener(answersRadioButtonsListener);
        rdbtnAnswer4.setBackground(Color.BLACK);
        GridBagConstraints gbc_rdbtnAnswer4 = new GridBagConstraints();
        gbc_rdbtnAnswer4.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnAnswer4.gridx = 6;
        gbc_rdbtnAnswer4.gridy = 8;
        getContentPane().add(rdbtnAnswer4, gbc_rdbtnAnswer4);

        answerChecklbl = new JLabel("");
        answerChecklbl.setForeground(Color.RED);
        GridBagConstraints gbc_answerChecklbl = new GridBagConstraints();
        gbc_answerChecklbl.insets = new Insets(0, 0, 5, 5);
        gbc_answerChecklbl.gridx = 6;
        gbc_answerChecklbl.gridy = 9;
        getContentPane().add(answerChecklbl, gbc_answerChecklbl);

//        JButton btnSave = new JButton("Save");
        FancyButton btnSave = new FancyButton("Save", 20f);
        GridBagConstraints gbc_btnSave = new GridBagConstraints();
        gbc_btnSave.insets = new Insets(0, 0, 0, 5);
        gbc_btnSave.gridx = 7;
        gbc_btnSave.gridy = 10;
        getContentPane().add(btnSave, gbc_btnSave);

        //back to previous page
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuestionScreen qs = new QuestionScreen();
                dispose();
            }
        });

        // create Focus Listener for all textfileds in order to clear from the error message after press it
        quesionBodyTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(quesionBodyTF.getText().equals(emptyField)){
                    quesionBodyTF.setText((""));
                    quesionBodyTF.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                quesionBodyTF.setActionCommand(answer1.getText());
            }

        });
        answer1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (answer1.getText().equals(emptyField)) {
                    answer1.setText((""));
                    answer1.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                rdbtnAnswer1.setActionCommand(answer1.getText());
            }

        });
        answer2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (answer2.getText().equals(emptyField)) {
                    answer2.setText((""));
                    answer2.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                rdbtnAnswer2.setActionCommand(answer2.getText());
            }

        });
        answer3.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (answer3.getText().equals(emptyField)) {
                    answer3.setText((""));
                    answer3.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                rdbtnAnswer3.setActionCommand(answer3.getText());
            }

        });
        answer4.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (answer4.getText().equals(emptyField)) {
                    answer4.setText((""));
                    answer4.setForeground(Color.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                rdbtnAnswer4.setActionCommand(answer4.getText());
            }

        });
        //add the radio buttons of the level to a radio group
        level.add(rdbtnEasy);
        level.add(rdbtnMedium);
        level.add(rdbtnHard);

        //add the radio buttons of the answers to a radio group
        answers.add(rdbtnAnswer1);
        answers.add(rdbtnAnswer2);
        answers.add(rdbtnAnswer3);
        answers.add(rdbtnAnswer4);

        //let the user reset all the changes
        btnClean.addActionListener(e -> {
            answer1.setText(question.answers.get(0).aBody);
            answer2.setText(question.answers.get(1).aBody);
            answer3.setText(question.answers.get(2).aBody);
            answer4.setText(question.answers.get(3).aBody);
            quesionBodyTF.setText(question.qBody);
            if(question.diff==1){
                rdbtnEasy.setSelected(true);
            }
            if(question.diff==2){
                rdbtnMedium.setSelected(true);
            }
            if(question.diff==3){
                rdbtnHard.setSelected(true);
            }
            if(question.answers.get(0).isCorrect){
                rdbtnAnswer1.setSelected(true);
            }
            if(question.answers.get(1).isCorrect){
                rdbtnAnswer2.setSelected(true);
            }
            if(question.answers.get(2).isCorrect){
                rdbtnAnswer3.setSelected(true);
            }
            if(question.answers.get(3).isCorrect){
                rdbtnAnswer4.setSelected(true);
            }
        });

        //save the changes and update the question
        btnSave.addActionListener(e -> {
            if(validation()){
                String levelChoice = level.getSelection().getActionCommand();
                String questionBody = quesionBodyTF.getText();
                System.out.println("ACTION Candidate Selected: " + levelChoice);
                String correctAnswer= answers.getSelection().getActionCommand();
                System.out.println("Correct Answer is: " + correctAnswer);
                Answer a1 = new Answer(answer1.getText(), rdbtnAnswer1.isSelected());
                Answer a2 = new Answer(answer2.getText(), rdbtnAnswer2.isSelected());
                Answer a3 = new Answer(answer3.getText(), rdbtnAnswer3.isSelected());
                Answer a4 = new Answer(answer4.getText(), rdbtnAnswer4.isSelected());
                ArrayList answers = new ArrayList();
                answers.add(a1);
                answers.add(a2);
                answers.add(a3);
                answers.add(a4);
                System.out.println(answers);
                Question q= new Question(5,5, Integer.valueOf(levelChoice), questionBody, answers);
                s.deleteQuestion(question);
                s.addQuestion(q);
                s.updateQuestionsJSON();
                JOptionPane.showMessageDialog(null, "Question updated successfully", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        pack();
        setVisible(true);
    }
    //check whether the user filled the fields correctly or not. if not - show the user exactly where and what is the problem
    boolean validation(){
        boolean correct = true;
        if(quesionBodyTF.getText().equals("") || quesionBodyTF.getText().equals(emptyField)){
            quesionBodyTF.setText(emptyField);
            quesionBodyTF.setForeground(Color.red);
            correct = false;
        }
        if(answer1.getText().equals("") || answer1.getText().equals(emptyField)){
            answer1.setText(emptyField);
            answer1.setForeground(Color.red);
            correct = false;
        }
        if(answer2.getText().equals("") || answer2.getText().equals(emptyField)){
            answer2.setText(emptyField);
            answer2.setForeground(Color.red);
            correct = false;
        }
        if(answer3.getText().equals("") || answer3.getText().equals(emptyField)){
            answer3.setText(emptyField);
            answer3.setForeground(Color.red);
            correct = false;
        }
        if(answer4.getText().equals("") || answer4.getText().equals(emptyField)){
            System.out.println("inside if of answer 4");
            answer4.setText(emptyField);
            answer4.setForeground(Color.red);
            correct = false;
        }
        if(level.getSelection()==null){
            levelChecklbl.setText("Must select level");
            correct = false;
        }
        if(answers.getSelection()==null){
            answerChecklbl.setText("Must select correct answer");
            correct = false;
        }
        return correct;
    }



    ActionListener levelRadioButtonsListener = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            levelChecklbl.setText("");
        }
    };
    ActionListener answersRadioButtonsListener = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            answerChecklbl.setText("");
        }
    };
}

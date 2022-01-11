package controller;

import java.io.*;
import java.sql.Date;
import java.util.*;

import model.Answer;
import model.Highscore;
import model.Question;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Singleton class to read and write to our JSON database
 * reads will happen once when the program starts
 * writes will happen once as a game ends or a question is added/modified
 */
public class SysData {
    // parsing file "JSONExample.json"

    private static SysData sysData = null;
    private static ArrayList<Question> questions = new ArrayList<>();
    private static ArrayList<Highscore> highscores = new ArrayList<>();
    private static final String qPath = "src\\resources\\QuestionsFormat.json";
    private static final String hsPath = "src\\resources\\Highscores.json";

    public static SysData getInstance() {
        if (sysData == null) {
            sysData = new SysData();
            sysData.readJSON();
        }
        return sysData;
    }

    public void readJSON() {
        JSONParser parser = new JSONParser();
        // Handle JSON files and populate Arraylist<Question> and ArrayList<Highscore>
        try {
            Reader reader;
            if (isJUnitTest()){
                reader = new FileReader("..\\" + qPath);
            } else {
                reader = new FileReader(qPath);
            }
            //Reader reader = new FileReader("test.json");

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray questionsAndAnswers = (JSONArray) jsonObject.get("questions");

            questionsAndAnswers.forEach(QAndAs -> parseQuestion((JSONObject) QAndAs));

        } catch (IOException | ParseException e) {
            if (e instanceof FileNotFoundException) {
                File file = new File(qPath);
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else
                e.printStackTrace();
        }

        parser.reset();

        try {
            Reader reader;
            if (isJUnitTest()){
                reader = new FileReader("..\\" + hsPath);
            } else {
                reader = new FileReader(hsPath);
            }

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray JSONhighscores = (JSONArray) jsonObject.get("highscores");

            JSONhighscores.forEach(highscore -> parseHighscore((JSONObject) highscore));

        } catch (IOException | ParseException e) {
            if (e instanceof FileNotFoundException) {
                File file = new File(hsPath);
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else
                e.printStackTrace();
        }
        highscores.sort((hs2, hs1) -> hs1.getScore().compareTo(hs2.getScore())); // descending order
    }

    // Function to parse each highscore element and add it to highscores array
    private void parseHighscore(JSONObject highscore) {
        //Handle Highscore Data
        String username = (String) highscore.get("username");
        int score = Integer.parseInt((String) highscore.get("score"));
        String date = (String) highscore.get("date");

        Highscore hs = new Highscore(score, username, date);
        highscores.add(hs);
    }

    // Function to parse each question element and add it to questions array
    private static void parseQuestion(JSONObject qAndAs) {
        //Handle Answers Data
        //Get correct answer position
        String correct_ans = (String) qAndAs.get("correct_ans");

        JSONArray answers = (JSONArray) qAndAs.get("answers");

        ArrayList<Answer> a = new ArrayList<Answer>();
        for (int i = 0; i < answers.size(); i++) {
            a.add(new Answer((String) answers.get(i), i == Integer.parseInt(correct_ans) - 1));
        }

        //Handle Question Data
        //Get questionId object within list
        String questionId = (String) qAndAs.get("question");

        //Get question level
        String level = (String) qAndAs.get("level");

        //Get question team
        String team = (String) qAndAs.get("team");

        Question q = new Question(-1, -1, Integer.parseInt(level), questionId, a);
        questions.add(q);
    }

    // Insert Highscores elements to JSON
    public static void updateHighscoresJSON() {
        JSONObject hsArray = new JSONObject();
        JSONArray hsList = new JSONArray();

        highscores.sort((hs2, hs1) -> hs1.getScore().compareTo(hs2.getScore())); // descending order
        for (Highscore hs : highscores) {
            JSONObject hsDetails = new JSONObject();
            hsDetails.put("username", hs.getUsername());
            hsDetails.put("score", hs.getScore().toString());
            hsDetails.put("date", hs.getDate().toString());
            hsList.add(hsDetails);
        }
        hsArray.put("highscores", hsList);

        try {
            FileWriter file = new FileWriter(hsPath);
            file.append(hsArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateQuestionsJSON() {

        JSONObject questionArray = new JSONObject();
        JSONArray questionList = new JSONArray();
        for (Question q : questions) {

            JSONObject questionDetails = new JSONObject();

            // Handle answers details
            JSONArray answerArray = new JSONArray();
            int correct_ans = 0;
            ArrayList<Answer> ans = q.getAnswers();
            for (int i = 0; i < ans.size(); i++) {
                if (ans.get(i).isCorrect) {
                    correct_ans = i + 1;
                }
                answerArray.add(ans.get(i).aBody);
            }

            questionDetails.put("answers", answerArray);
            questionDetails.put("correct_ans", "" + correct_ans);
            // Handle Question details

            questionDetails.put("question", q.getqBody());
            questionDetails.put("level", "" + q.getDiff());
            questionDetails.put("team", "Husky");

            questionList.add(questionDetails);
        }

        questionArray.put("questions", questionList);

        try {
            FileWriter file = new FileWriter(qPath);
            file.append(questionArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Question> getQuestions() {
        return questions;
    }

    public static ArrayList<Highscore> getHighscores() {
        return highscores;
    }

    public static boolean deleteQuestion(String questionString) {
        for (Question q : questions) {
            if (q.getqBody().equals(questionString)) {
                return questions.remove(q);
            }
        }
        return false;
    }

    public static boolean deleteQuestion(Question q) {
        return questions.remove(q);
    }

    public static boolean addQuestion(Question q) {
        return questions.add(q);
    }

    public static boolean addHighscore(Highscore h) {
        return highscores.add(h);
    }

    public static boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }
}













package model;

import java.util.ArrayList;
/**
 * class that implements the criteria interface for hard questions
 */
public class CriteriaHard implements Criteria {
    @Override
    public ArrayList<Question> meetCriteria(ArrayList<Question> questions) {
        ArrayList<Question> hardQuestions = new ArrayList<Question>();
        for (Question q : questions) {
            if (q.getDiff() == 3) {
                hardQuestions.add(q);
            }
        }
        return hardQuestions;
    }
}

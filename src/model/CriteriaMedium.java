package model;

import java.util.ArrayList;
/**
 * class that implements the criteria interface for medium questions
 */
public class CriteriaMedium implements Criteria {
    @Override
    public ArrayList<Question> meetCriteria(ArrayList<Question> questions) {
        ArrayList<Question> mediumQuestions = new ArrayList<Question>();
        for (Question q : questions) {
            if (q.getDiff() == 2) {
                mediumQuestions.add(q);
            }
        }
        return mediumQuestions;
    }
}

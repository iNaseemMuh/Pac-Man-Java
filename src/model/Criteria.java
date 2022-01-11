package model;

import java.util.ArrayList;
/**
 * necessary interface class for the criteria
 */
public interface Criteria {
    public ArrayList<Question> meetCriteria(ArrayList<Question> questions);
}

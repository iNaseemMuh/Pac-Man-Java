package model;
/**
 *represent optional answer for each question. 
 *each question has 4 answers: 3 wrong and 1 correct
 */
public class Answer {
    public String aBody;
    public boolean isCorrect;

    public Answer(String aBody, boolean isCorrect) {
        this.aBody = aBody;
        this.isCorrect = isCorrect;
    }

    public String getaBody() {
        return aBody;
    }

    public void setaBody(String aBody) {
        this.aBody = aBody;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "aBody='" + aBody + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}

package cn.suiseiseki.suiquiz;

/**
 * Created by Administrator on 2015/12/29.
 */
public class TrueFalse {
    public int getmQuestion() {
        return mQuestion;
    }
    private boolean mTrueQuestion;


    private int mQuestion;

    public boolean ismTrueQuestion() {
        return mTrueQuestion;
    }


    public void setmQuestion(int mQuestion) {
        this.mQuestion = mQuestion;
    }

    public void setmTrueQuestion(boolean mTrueQuestion) {
        this.mTrueQuestion = mTrueQuestion;
    }
    public TrueFalse(int question,boolean trueQuestion)
    {
        this.mQuestion=question;
        this.mTrueQuestion=trueQuestion;
    }
}

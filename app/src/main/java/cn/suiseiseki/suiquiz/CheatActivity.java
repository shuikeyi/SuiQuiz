package cn.suiseiseki.suiquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static cn.suiseiseki.suiquiz.SuiQuiz.*;

/**
 * Created by Administrator on 2016/2/24.
 */
public class CheatActivity extends Activity {

    public final static String EXTRA_ANSWER_IS_TRUE = "cn.suiseiseki.answer_is_true";
    public final static String EXTRA_ANSWER_SHOWN = "cn.suiseiseki.answer_is_shown";
    private static final String CHEATCONDITION = "cheat recorded";
    private boolean mAnswerIsTrue;
    private boolean isAnswerShown;
    private Button showAnswerButton;
    private TextView showAnswerTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("CheatsActivity", "Cheat begin!");
        super.onCreate(savedInstanceState);
        setAnswerShownResult(false);
        if(savedInstanceState != null)
        {
            setAnswerShownResult(true);
        }
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,true);
        showAnswerButton = (Button)findViewById(R.id.showAnswerButton);
        showAnswerTextView = (TextView)findViewById(R.id.answerTextView);
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswerShownResult(true);
                if(mAnswerIsTrue)
                    showAnswerTextView.setText(R.string.true_text);
                else
                    showAnswerTextView.setText(R.string.false_text);
            }
        });

    }
    private void setAnswerShownResult(boolean isAnswerShown)
    {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK,data);
        this.isAnswerShown = isAnswerShown;
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(CHEATCONDITION,isAnswerShown);
    }
}

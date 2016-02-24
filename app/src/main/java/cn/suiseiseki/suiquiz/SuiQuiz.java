package cn.suiseiseki.suiquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SuiQuiz extends Activity {
    public static final String TAG = "SuiQuiz";
    private static final String KEY_INDEX = "index";
    private static final String ISCHEATER = "is a cheater";
    private Button mTrueButton,mFalseButton;
    private ImageButton mNextButton,mPrevButton;
    private Button mCheatButton;
    private Boolean mIsCheater = false;

    private TextView mQuestionTextView,mBuildVersionTextView;
    private TrueFalse[] mQuestionBank = {new TrueFalse(R.string.question_su,true),new TrueFalse(R.string.question_sea,true),new TrueFalse(R.string.question_budong,false),new TrueFalse(R.string.question_chuan,true),new TrueFalse(R.string.question_jack,false)};
    private int currentIndex = 0;
    private void updateQuestion(){
//        Log.d(TAG,"Updating question text #"+currentIndex,new Exception());
        mQuestionTextView.setText(mQuestionBank[currentIndex].getmQuestion());
//        Log.d(TAG ,":Current Index is "+currentIndex);
    }
    private void checkAnswer(boolean userPressedTure){
        boolean answerIsTrue = mQuestionBank[currentIndex].ismTrueQuestion();
        int messageResId = 0;
        if(mIsCheater)
        { messageResId = R.string.judgment_toast; }
        else {
            if (userPressedTure == answerIsTrue)
                messageResId = R.string.correct;
            else
                messageResId = R.string.incorrect;
        }
        Toast.makeText(SuiQuiz.this, messageResId, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
        {
            currentIndex=savedInstanceState.getInt(KEY_INDEX);
            mIsCheater = savedInstanceState.getBoolean(ISCHEATER);
        }
        final Boolean[] cheatNum = new Boolean[mQuestionBank.length];
        for(int b = 0;b<cheatNum.length;b++)
                cheatNum[b] = false;
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_sui_quiz);
        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mNextButton =(ImageButton)findViewById(R.id.next_button);
        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mBuildVersionTextView = (TextView)findViewById(R.id.build_version_text);
        String a = Integer.toString(Build.VERSION.SDK_INT);
        mBuildVersionTextView.setText("API level " + a);
        updateQuestion();
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % mQuestionBank.length;
                if(cheatNum[currentIndex])
                    mIsCheater = true;
                else
                    mIsCheater = false;
                updateQuestion();
            }
        });
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (++currentIndex)%mQuestionBank.length;
                if(cheatNum[currentIndex])
                mIsCheater = true;
                else
                mIsCheater = false;
                updateQuestion();
            }
        });
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex == 0)
                currentIndex = (currentIndex+mQuestionBank.length-1)%mQuestionBank.length;
                else
                    currentIndex = (currentIndex-1)%mQuestionBank.length;
                updateQuestion();
            }
        });
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);

            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              checkAnswer(false);
            }
        });
        /** cheat module */

        mCheatButton=(Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent发送到ActivityManager
                Intent i = new Intent(SuiQuiz.this,CheatActivity.class);
                // 附加extra信息
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, mQuestionBank[currentIndex].ismTrueQuestion());
                startActivityForResult(i, 0);
                cheatNum[currentIndex] = true;
            }
        });
        updateQuestion();
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(data == null )
        {
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN,false);
    }


    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"OnStart() called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"OnPause() called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "OnResume() called");
    }
    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG, "OnStop() called");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"OnDestroy() called");
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,currentIndex);
        savedInstanceState.putBoolean(ISCHEATER,mIsCheater);
    }
}

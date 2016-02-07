package bignerdranch.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // TAG for filtering logcat
    private static final String TAG = "QuizActivity";

    // Key for saving mCurrentIndex throughout configuration changes
    private static final String KEY_INDEX = "index";

    // Key for saving mIsCheating throughout configuration changes
    private static final String KEY_BOOLEAN = "boolean";

    // Request code for cheating
    private static final int REQUEST_CODE_CHEAT = 0;

    // Cheating status
    private boolean mIsCheating;

    // Question text
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;

    private void updateQuestion() {

        // Create an exception to log stack traces
//        Log.d(TAG, "Updating question text for question #" + mCurrentIndex, new Exception());
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        // Check for Cheating
        // If yes, create toast with text "Cheating is wrong."
        if (mIsCheating) {
            Toast.makeText(this, R.string.judgment_toast, Toast.LENGTH_SHORT).show();
        }

        // Set answerIsTrue to boolean of mCurrentIndex in mQuestionBank
        // Create correct and incorrect Strings
        // If userPressedTrue == answerIsTrue, create Toast that displays "Correct!"
        // Else, create toast that displays "Incorrect!"
        else {
            if (userPressedTrue == answerIsTrue) {
                Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_BOOLEAN, mIsCheating);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add log statement to onCreate
        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Get reference to inflated widgets
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        // Check savedInstanceState.
        // If it exists, set mCurrentIndex to its int and set mIsCheating.
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheating = savedInstanceState.getBoolean(KEY_BOOLEAN);
        }

        // Initialize question
        updateQuestion();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) return;

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) return;

            mIsCheating = CheatActivity.wasAnswerShown(data);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Set listeners to "listen" on events
    public void onTrueButtonClick(View view) {
        checkAnswer(true);
    }

    public void onFalseButtonClick(View view) {
        checkAnswer(false);
    }

    // Set listener for the "Next" button
    public void onNextButtonClick(View view) {

        // Increment mCurrentIndex
        // Update the mQuestionTextView with the new mCurrentIndex
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        mIsCheating = false;
        updateQuestion();

    }

    // Set listener for the "Prev" button
    public void onPrevButtonClick(View view) {

        // Decrement mCurrentIndex
        // If mCurrentIndex is -1, set it to the last index of mQuestionBank
        mCurrentIndex -= 1;

        if (mCurrentIndex == -1) {
            mCurrentIndex = mQuestionBank.length - 1;
        }

        updateQuestion();
    }

    // Listener for the TextView
    public void onTextViewClick(View view) {

        // Same functionality as clicking on the Next button
        onNextButtonClick(view);
    }

    public void onCheatButtonClick(View view) {

        // Create new intent with MainActivity.this as context and CheatActivity.class as class.
        // Call startActivity on the intent.
        // Concept: Sends an intent from MainActivity with extras to the ActivityManager and calls
        // for CheatActivity
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
        startActivityForResult(intent, REQUEST_CODE_CHEAT);

    }
}

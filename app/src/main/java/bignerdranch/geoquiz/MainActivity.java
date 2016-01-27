package bignerdranch.geoquiz;

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

    // Create a TAG for filtering logcat
    private static final String TAG = "QuizActivity";

    // Add a key for saving mCurrentIndex throughout configuration changes
    private static final String KEY_INDEX = "index";

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

        // Set answerIsTrue to boolean of mCurrentIndex in mQuestionBank
        // Create correct and incorrect Strings
        // If userPressedTrue == answerIsTrue, create Toast that displays "Correct!"
        // Else, create toast that displays "Incorrect!"
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        String correctToast = "Correct!";
        String incorrectToast = "Incorrect!";

        if (userPressedTrue == answerIsTrue) {
            Toast.makeText(this, correctToast, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, incorrectToast, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
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
        // If it exists, set mCurrentIndex to its int.
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
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
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
    }
}

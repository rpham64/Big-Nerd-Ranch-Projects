package bignerdranch.geoquiz;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        // Initialize question
        updateQuestion();

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

    public void onPrevButtonClick(View view) {

        // Decrement mCurrentIndex
        // If mCurrentIndex is -1, set it to mQuestionBank.length - 1
        mCurrentIndex -= 1;

        if (mCurrentIndex == -1) {
            mCurrentIndex = mQuestionBank.length - 1;
        }

        updateQuestion();
    }
}

package bignerdranch.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * no
 */
public class CheatActivity extends AppCompatActivity {

    // TAG for filtering logcat
    private static final String TAG = "CheatActivity";

    // Key for saving mAnswerTextView through configuration changes
    private static final String KEY_STRING = "string";

    // Extra for displaying answer from MainActivity "answer_is_true"
    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";

    // Extra for determining if answer was shown "answer_shown"
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;

    // Create new intent using QuizActivity's answerIsTrue extra
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    // Intent that determines if answer was shown or not
    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);

        // Send result back to parent activity
        setResult(RESULT_OK, data);
    }

    // Check if answer was shown
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "CheatActivity: onSaveInstanceState");

        // Store mAnswerTextView
        savedInstanceState.putString(KEY_STRING, mAnswerTextView.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize member variables
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        // Retrieve boolean value from extra and store it in mAnswerIsTrue
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Check savedInstanceState
        if (savedInstanceState != null) {
            // Set mAnswerTextView using saved data
            mAnswerTextView.setText(savedInstanceState.getString(KEY_STRING));
        }

    }

    public void onShowAnswerButtonClick(View view) {

        // If mAnswerIsTrue is "True", set text for mAnswerTextView to R.string.true_button
        // Else, set text to R.string.false_button
        if (mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }

        // Set answer shown result to True
        setAnswerShownResult(true);

    }
}

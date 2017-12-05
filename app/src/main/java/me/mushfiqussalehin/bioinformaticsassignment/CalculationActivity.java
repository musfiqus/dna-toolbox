package me.mushfiqussalehin.bioinformaticsassignment;

import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalculationActivity extends AppCompatActivity {

    public static final String EXTRA_OPERATION_NAME = "OpName";
    public static final int CALCULATE_COUNT = 1;
    public static final int TRANSCRIBE_DNA = 2;
    public static final int COMPLEMENT_DNA = 3;
    private int mCurrentOp;
    private String mResult;

    @BindView(R.id.calculate_button)
    Button mCalculateButton;
    @BindView(R.id.string_input)
    EditText mInputString;
    @BindView(R.id.result_header)
    TextView mResultHeader;
    @BindView(R.id.result)
    TextView mResultView;
    @BindView(R.id.string_input_layout)
    TextInputLayout mInputLayout;
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar()!= null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras() != null) {
            mCurrentOp = getIntent().getExtras().getInt(EXTRA_OPERATION_NAME, 1);
        }

        setTitle();

        mCalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate(view);
            }
        });

    }

    private void setTitle() {
        if (mCurrentOp == CALCULATE_COUNT) {
            getSupportActionBar().setTitle(getString(R.string.count_nucleotides));
            mCalculateButton.setText(R.string.calculate);
            mInputLayout.setHint(getString(R.string.hint_nuc));
        } else if (mCurrentOp == TRANSCRIBE_DNA) {
            getSupportActionBar().setTitle(getString(R.string.transcribe_dna_rna));
            mCalculateButton.setText(R.string.transcribe);
            mInputLayout.setHint(getString(R.string.hint_dna));
        } else {
            getSupportActionBar().setTitle(getString(R.string.complement_dna_rna));
            mCalculateButton.setText(R.string.complement);
            mInputLayout.setHint(getString(R.string.hint_dna));
        }
    }

    private void calculate(View v) {
        if (!validate()) {
            return;
        }
        String input = mInputString.getText().toString();
        if (mCurrentOp == CALCULATE_COUNT) {
            int A = 0, T = 0, G = 0, C = 0;
            for (int i = 0; i < input.length(); i++) {
                if (input.substring(i, i+1).equalsIgnoreCase("A")) {
                    A++;
                } else if (input.substring(i, i+1).equalsIgnoreCase("T")) {
                    T++;
                } else if (input.substring(i, i+1).equalsIgnoreCase("G")) {
                    G++;
                } else {
                    C++;
                }
            }
            mResultHeader.setText(R.string.nuc_count);
            mResult = "<font color='#5050FF'>A</font>: "+A+"<font color='#E00000'> C</font>: "+C+"<font color='#00C000'> G</font>: "+G+"<font color='#E6E600'> T</font>: "+T;
        } else if (mCurrentOp == TRANSCRIBE_DNA) {
            mResultHeader.setText(R.string.rna_strand_header);
            mResult = input.replaceAll("T", "U");
        } else {
            mResultHeader.setText(R.string.complement_header);
            StringBuilder builder = new StringBuilder(input);
            for (int i = 0; i < input.length(); i++) {
                if (input.substring(i, i+1).equalsIgnoreCase("A")) {
                    builder.replace(i, i+1, "T");
                } else if (input.substring(i, i+1).equalsIgnoreCase("T")) {
                    builder.replace(i, i+1, "A");
                } else if (input.substring(i, i+1).equalsIgnoreCase("G")) {
                    builder.replace(i, i+1, "C");
                } else {
                    builder.replace(i, i+1, "G");
                }
            }
            mResult = builder.reverse().toString();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mResultView.setText(Html.fromHtml(mResult, Html.FROM_HTML_MODE_LEGACY));
        } else {
            mResultView.setText(Html.fromHtml(mResult));
        }


    }

    public boolean validate() {
        boolean valid = true;

        String email = mInputString.getText().toString();

        if (email.isEmpty()) {
            mInputString.setError(getString(R.string.invalid_input_error));
            valid = false;
        }

        return valid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}

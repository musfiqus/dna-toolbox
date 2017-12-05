package me.mushfiqussalehin.bioinformaticsassignment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.transition.Explode;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private static final String TRANSITION_KEY = "toolbar_text_transition";

    @BindView(R.id.appbar_layout_main)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    //Buttons
    @BindView(R.id.count_nucleotides_button)
    Button mCountButton;
    @BindView(R.id.transcribe_button)
    Button mTranscribeButton;
    @BindView(R.id.complement_button)
    Button mComplementButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind views
        ButterKnife.bind(this);

        mContext = this;
        setOnClickListeners();

        mToolBar.setTitle(getString(R.string.app_name));

        // set an exit transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.transition.Explode explode = new android.transition.Explode();
            explode.excludeTarget(R.id.string_input_layout, true);
            getWindow().setExitTransition(explode);
        }
    }

    private void setOnClickListeners() {
        mCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CalculationActivity.class);
                intent.putExtra(CalculationActivity.EXTRA_OPERATION_NAME, CalculationActivity.CALCULATE_COUNT);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, (View) mCountButton, TRANSITION_KEY);
                startActivity(intent, options.toBundle());
            }
        });

        mTranscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CalculationActivity.class);
                intent.putExtra(CalculationActivity.EXTRA_OPERATION_NAME, CalculationActivity.TRANSCRIBE_DNA);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, (View) mTranscribeButton, TRANSITION_KEY);
                startActivity(intent, options.toBundle());
            }
        });
        mComplementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CalculationActivity.class);
                intent.putExtra(CalculationActivity.EXTRA_OPERATION_NAME, CalculationActivity.COMPLEMENT_DNA);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, (View) mComplementButton, TRANSITION_KEY);
                startActivity(intent, options.toBundle());
            }
        });
    }
}

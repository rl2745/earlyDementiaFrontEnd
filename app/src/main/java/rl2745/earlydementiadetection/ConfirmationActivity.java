package rl2745.earlydementiadetection;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Richard Lopez on 5/26/2016.
 */
public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_confirmation);

        Intent intent = getIntent();
        String userName = intent.getStringExtra(LoginActivity.USER_NAME);
        String introduction = "Thank you " + userName + " for registering." +
                " Your doctor's information has been saved and with your confirmation, we will" +
                " notify your doctor if we detect signs of early dementia.";
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setTextColor(Color.WHITE);
        textView.setText(introduction);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.confirmation);
        layout.addView(textView);

    }



    /**
     * Called when the user clicks the Confirm button
     */
    public void sendConfirm(View view) throws Exception {
        
        //Intent to the next view
        Intent submitConfirm = new Intent(this, AnalyzingActivity.class); 
        // NEED TO MAKE ANALYZING ACTIVITY CLASS

        startActivity(submitConfirm);
        finish();
    }
}



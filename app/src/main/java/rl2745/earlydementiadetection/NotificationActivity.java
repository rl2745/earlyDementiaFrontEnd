package rl2745.earlydementiadetection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by Richard Lopez on 5/26/2016.
 */
public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_notification);

        Intent intent = getIntent();
        String notificationText = "Anomalous Behavior Detected - Your Doctor Has Been Notified";
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setText(notificationText);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.notification);
        layout.addView(textView);

    }



}
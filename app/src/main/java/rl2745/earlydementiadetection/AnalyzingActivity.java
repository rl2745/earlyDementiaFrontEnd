package rl2745.earlydementiadetection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * Created by Richard Lopez on 5/26/2016.
 */
public class AnalyzingActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static final String TAG = "AnalyzingActivity";
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_analyzing);

        Intent intent = getIntent();
        String standardMessage = "Analyzing Habits - Run App in Background";
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setTextColor(Color.WHITE);
        textView.setText(standardMessage);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.standardMessage);
        layout.addView(textView);
        backEndProcesses();


    }

    public void backEndProcesses() {
        //connect to backend here

    }

    /**Sends SMS message to doctor*/
    public void sendSMS() {
        //http://www.mkyong.com/android/how-to-send-sms-message-in-android/

        Intent intent = getIntent();
        String userName = intent.getStringExtra(LoginActivity.USER_NAME);
        String userDOB = intent.getStringExtra(LoginActivity.DOB);
        String doctorName = intent.getStringExtra(LoginActivity.DOCTOR_NAME);
        String doctorNumber = intent.getStringExtra(LoginActivity.DOCTOR_NUMBER);
        String sms = "Hello Dr. " + doctorName + ", your patient " + userName + " " +
                "whose date of birth is " + userDOB + " is suspected of having dementia " +
                "based on the anomalous behavior tracked on the Early Detection Dementia App.";


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(doctorNumber, null, sms, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public String[] readfromCSV() {
        try {
            InputStreamReader is;
            BufferedReader reader;
            is = new InputStreamReader(getAssets().open("test.csv"));
            reader = new BufferedReader(is);
            reader.readLine();
            String[] lines = new String[20];


            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, line);
                lines[i] = line;
                i = i + 1;

            }
        } catch (Exception e) {
            Log.d(TAG, "error in CSV reading");

        }
        return null;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }
        if (mLastLocation != null) {
            Log.d(TAG, mLastLocation.getLatitude() + " In OnConnected");
            Log.d(TAG, mLastLocation.getLongitude() + "In OnConnected");
        } else {
            Log.d(TAG, "Location was null in onConnected");
            createLocationRequest();
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        readfromCSV();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
        else{
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mLastLocation != null) {
            Log.d(TAG, mLastLocation.getLatitude() + "");
            Log.d(TAG, mLastLocation.getLongitude() + "");
        }
    }
    /**
     * Called when the Back-End spots an anomaly
     */
    public void sendAnalyze(View view) throws Exception {

        //Intent to the next view
        Intent submitAnalyze = new Intent(this, NotificationActivity.class);
        startActivity(submitAnalyze);

    }
}
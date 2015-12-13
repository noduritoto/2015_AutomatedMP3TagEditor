package com.example.noduritoto.noduritoto2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Hashtable;

import edu.gvsu.masl.echoprint.AudioFingerprinter;

public class Test extends AppCompatActivity implements AudioFingerprinter.AudioFingerprinterListener {

    boolean recording, resolved;
    AudioFingerprinter fingerprinter;
    TextView status;
    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btn = (Button) findViewById(R.id.recordButton);

        status = (TextView) findViewById(R.id.status);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if (recording) {
                    fingerprinter.stop();
                } else {
                    if (fingerprinter == null)
                        fingerprinter = new AudioFingerprinter(Test.this);

                    fingerprinter.fingerprint(15);
                }
            }
        });
    }

    public void didFinishListening() {
        btn.setText("Start");

        if (!resolved)
            status.setText("Idle...");

        recording = false;
    }

    public void didFinishListeningPass() {
    }

    public void willStartListening() {
        status.setText("Listening...");
        btn.setText("Stop");
        recording = true;
        resolved = false;
    }

    public void willStartListeningPass() {
    }

    public void didGenerateFingerprintCode(String code) {
        status.setText("Will fetch info for code starting:\n" + code.substring(0, Math.min(50, code.length())));
        Log.e("CODE :", "Will fetch info for code starting:\n" + code.substring(0, Math.min(50, code.length())));
    }

    public void didFindMatchForCode(final Hashtable<String, String> table,
                                    String code) {
        resolved = true;
        status.setText("Match: \n" + table);
    }

    public void didNotFindMatchForCode(String code) {
        resolved = true;
        status.setText("No match for code starting with: \n" + code.substring(0, Math.min(50, code.length())));
    }

    public void didFailWithException(Exception e) {
        resolved = true;
        status.setText("Error: " + e);
    }
}

package com.example.noduritoto.noduritoto2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        /*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        */

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);

                Log.d("intent", " intro intent" + intent);
                // 뒤로가기 했을경우 안나오도록 없애주기 >> finish!!
                finish();
            }
        }, 2000);

    }
}

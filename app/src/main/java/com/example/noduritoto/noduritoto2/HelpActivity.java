package com.example.noduritoto.noduritoto2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ImageButton sendMailBtn = (ImageButton) findViewById(R.id.sendmail);

        sendMailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Intent.ACTION_SEND);
                it.setType("plain/text");

                // 수신인 주소 - tos배열의 값을 늘릴 경우 다수의 수신자에게 발송됨
                String[] tos = {"noduritoto@gmail.com"};
                it.putExtra(Intent.EXTRA_EMAIL, tos);

                startActivity(it);
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }

}

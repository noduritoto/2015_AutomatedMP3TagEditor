package com.example.noduritoto.noduritoto2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;

public class TagInfoActivity extends AppCompatActivity {

    String path;
    Mp3File mp3file;
    EditText songEditText;
    EditText artistEditText;
    EditText albumEditText;
    EditText lyricsEditText;
    ImageView img;

    ID3v2 id3v2Tag;
    Button.OnClickListener mClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.editButton_1:
                    Log.d("OnClickListener", "click edit button_1");
                    //Log.d("sjenfl", "sjenfl " + intent.getStringExtra("pathname"));

                    //onActivityResult(RESULT_OK, 1, getIntent());
                    // 액티비티 실행
                    setEditText(id3v2Tag);
                    finish();

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_info);

        /*
        findViewById(R.id.tagInfoMainScrollView);
        findViewById(R.id.lyricsEditText);
        */

        // 스크롤 뷰 안에 에딧텍스트 스크롤하게 하기

        EditText EtOne = (EditText) findViewById(R.id.lyricsEditText);
        EtOne.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.lyricsEditText) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        path = getIntent().getStringExtra("selectedPath");
        Log.d("intent", " " + getIntent());
        Log.d("selectedPathIntent", path);
        //Log.i("selectedPath", getIntent().getStringExtra("pathName"));

        // mp3 리
        setImageButtons();

        try {
            mp3file = new Mp3File(path);
            /*
            Log.d("noduri mp3", "Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds" );
            Log.d("noduri mp3", "Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)") );
            Log.d("noduri mp3", "Sample rate: " + mp3file.getSampleRate() + " Hz");
            Log.d("noduri mp3", "Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
            Log.d("noduri mp3", "Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
            Log.d("noduri mp3", "Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
            */

        } catch (IOException e) {
            Log.e("Exception", "IOException");
        } catch (UnsupportedTagException e) {
            Log.e("Exception", "UnsupportedTagException");
        } catch (InvalidDataException e) {
            Log.e("Exception", "InvalidDataException");
        }

        id3v2Tag = mp3file.getId3v2Tag();
        //Log.d("title_noduricoco", id3v2Tag.getTitle());

        getEditText();
        readTag(id3v2Tag);


    }

    public void getEditText() {
        songEditText = (EditText) findViewById(R.id.songEditText);
        artistEditText = (EditText) findViewById(R.id.artistEditText);
        albumEditText = (EditText) findViewById(R.id.albumEditText);
        lyricsEditText = (EditText) findViewById(R.id.lyricsEditText);
        img = (ImageView) findViewById(R.id.coverForTaginfo);

    }

    public void readTag(ID3v2 id3v2Tag) {
        songEditText.setText(id3v2Tag.getTitle());

        artistEditText.setText(id3v2Tag.getArtist());
        albumEditText.setText(id3v2Tag.getAlbum());
        //lyricsEditText.setText(id3v2Tag.get);
        img.setImageBitmap(byteArrayToBitmap(id3v2Tag.getAlbumImage()));
    }

    public Bitmap byteArrayToBitmap(byte[] $byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray($byteArray, 0, $byteArray.length);
        return bitmap;
    }

    public void setEditText(ID3v2 id3v2Tag) {

        id3v2Tag.setTitle(songEditText.getText().toString());
        id3v2Tag.setAlbum(albumEditText.getText().toString());
        id3v2Tag.setArtist(artistEditText.getText().toString());
    }

    public void setImageButtons() {

        findViewById(R.id.editButton_1).setOnClickListener(mClickListener);
    }

    /*
    EditText.OnTouchListener eTextTouchListener = new View.OnTouchListener() {
        ScrollView scv = (ScrollView) findViewById(R.id.tagInfoMainScrollView);

        public boolean onTouch(View v , MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                scv.requestDisallowInterceptTouchEvent(false);
            else
                scv.requestDisallowInterceptTouchEvent(true);

            return false;
        }

    };
    */

    @Override
    public void onBackPressed() {
        finish();
    }
}




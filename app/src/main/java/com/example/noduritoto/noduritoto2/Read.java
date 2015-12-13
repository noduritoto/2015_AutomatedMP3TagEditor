package com.example.noduritoto.noduritoto2;

import android.util.Log;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;


/**
 * Created by noduritoto on 15. 12. 10.
 */
public class Read {

    private static final String TAG = "TagEditor";
    String path;
    //String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();

    public void readTest(String path) {
        try {
            Mp3File mp3file = new Mp3File(path);
            Log.d("noduri mp3", "Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
            Log.d("noduri mp3", "Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
            Log.d("noduri mp3", "Sample rate: " + mp3file.getSampleRate() + " Hz");
            Log.d("noduri mp3", "Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
            Log.d("noduri mp3", "Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
            Log.d("noduri mp3", "Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));

        } catch (IOException e) {
            Log.e("Exception", "IOException");
        } catch (UnsupportedTagException e) {
            Log.e("Exception", "UnsupportedTagException");
        } catch (InvalidDataException e) {
            Log.e("Exception", "InvalidDataException");
        }


    }
}



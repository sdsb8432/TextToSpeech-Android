package com.sdsb8432.texttospeech;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sdsb8432.texttospeech.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainBinding mainBinding;



    private TTS tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        tts = new TTS(this, Locale.KOREAN);

        mainBinding.buttonTextToSpeech.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        tts.speak(mainBinding.editText.getText().toString());
    }

    @Override
    protected void onDestroy() {

        if(tts != null && tts.isSpeaking()) {
            tts.stop();
        }

        if(tts != null) {
            tts.shutdown();
        }

        tts = null;

        super.onDestroy();
    }
}

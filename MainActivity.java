package com.ciel.webview;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import org.apache.cordova.*;
import java.util.Locale;

public class MainActivity extends CordovaActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUrl(launchUrl);
        tts = new TextToSpeech(this, this);
        appView.getSettings().setJavaScriptEnabled(true);
        appView.addJavascriptInterface(new BottoNativeTTS(this), "BottoNativeTTS");
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.getDefault());
        }
    }

    public class BottoNativeTTS {
        Context mContext;
        BottoNativeTTS(Context c) { mContext = c; }

        @JavascriptInterface
        public void speak(String text) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }
}

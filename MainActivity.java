package com.ciel.webview;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
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
        appView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        appView.addJavascriptInterface(new BottoNativeTTS(this), "BottoNativeTTS");

        appView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());
            }
        });

        appView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(android.net.Uri.parse(url));
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            android.app.DownloadManager dm = (android.app.DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            dm.enqueue(request);
        });
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

        @JavascriptInterface
        public void stop() {
            if (tts != null) tts.stop();
        }

        @JavascriptInterface
        public boolean isAvailable() {
            return tts != null;
        }
    }
}

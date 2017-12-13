package com.example.yellowsoft.phonecase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by yellowsoft on 2/12/17.
 */

public class PaymentPage extends Activity {
    private WebView wv1;
    ProgressBar progress;
    String amount;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);
        wv1=(WebView) findViewById(R.id.webView);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.addJavascriptInterface(new WebAppInterface(this),"app");
        wv1.setWebViewClient(new WebViewClient());
        wv1.setWebChromeClient(new MyWebViewClient());
        if (getIntent()!=null && getIntent().hasExtra("amount")){
            amount =  getIntent().getStringExtra("amount");
            Log.e("amount",amount);
        }
        wv1.loadUrl(Session.PAYMENT_URL + "member_id=" + Session.GetUserId(this) + "&amount=" + amount);
        // wv1.loadUrl(Settings.PAY_URL + "amount=" + amount);
        //Log.e("pay_url",Settings.PAY_URL + "amount=" +amount);
        Log.e("payment_url",Session.PAYMENT_URL + "member_id=" + Session.GetUserId(this) + "&amount=" + amount);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(100);
        progress.setProgress(0);

    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void send_message(String toast,Boolean success) {
            Log.e("toast",toast);
            if(toast.equals("success")){
                Intent intent = new Intent();
                intent.putExtra("message",toast);
                Log.e("message",toast);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }

            else {
                Log.e("tost", toast);
                if (toast.equals("failure")){
                    Intent intent = new Intent();
                    intent.putExtra("message",toast);
                    Log.e("message",toast);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        }
    }


    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            PaymentPage.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);

        }
    }




    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }
}

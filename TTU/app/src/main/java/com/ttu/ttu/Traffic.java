package com.ttu.ttu;

/**
 * Created by wind1209 on 2016/12/7.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ttu.ttu.login.Helper;
import com.ttu.ttu.menu.Menu;

public class Traffic extends Activity {
    private Activity instance;
    private String loadURL;
    private WebView webView;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.traffic_brower);
        findViewIDs();

        loadURL = Helper.getURL(instance) + "traffic.php";
        setupWebView();
    }

    private void findViewIDs() {
        webView = (WebView) findViewById(R.id.webview);
    }

    private void setupWebView() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); //關閉webview中緩存
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(progress == null || !progress.isShowing()) {
                    progress = ProgressDialog.show(
                            instance,
                            getString(R.string.PROGRESS_DIALOG_TITLE),
                            getString(R.string.PROGRESS_DIALOG_MESSAGE),
                            true,
                            true);
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Ignore SSL certificate errors
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                Toast.makeText(instance, getString(R.string.ERROR_NO_INTERNET), Toast.LENGTH_LONG).show();
                if(progress.isShowing()) {
                    progress.dismiss();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(progress.isShowing()) {
                    progress.dismiss();
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        webView.loadUrl(loadURL);
    }

    @Override
    public void onBackPressed() {
        webView.stopLoading();
        Intent intent = new Intent(Traffic.this, Menu.class);
        Traffic.this.startActivity(intent);
        Traffic.this.finish();

    }
}
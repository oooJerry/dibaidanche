package com.wuwutongkeji.dibaidanche.common.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.widget.ProgressBar;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/3/12.
 */

public class BrowserActivity extends BaseToolbarActivity {

    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_DATA = "KEY_DATA";

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webview)
    WebView webview;

    private String url;


    @Override
    protected int getLayoutId() {
        return R.layout.layout_widget_activity_browser;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setLoadWithOverviewMode(true);

        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                } else if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

        });
        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                showDialog(message, result);
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                showDialog(message, result);
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                progressBar.setProgress(newProgress);
                progressBar.setVisibility(newProgress == 100 ? View.INVISIBLE : View.VISIBLE);
            }
        });
    }

    private void showDialog(String message, final JsResult result) {
        new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        result.confirm();
                    }
                })
                .show();
    }

    @Override
    protected void initData() {
        webview.loadUrl(url = getIntent().getStringExtra(KEY_DATA));
        setTitle(getIntent().getStringExtra(KEY_TITLE));

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }
}

package app.munc.munccoordinator.fragment.mainbili.page1.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import app.munc.munccoordinator.R;
import app.munc.munccoordinator.content.CommonContent;
import app.munc.munccoordinator.util.WebSetting;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendWebView extends AppCompatActivity {

    @BindView(R.id.mWebView)
    WebView mWebView;
    @BindView(R.id.activity_recommend_web_view)
    RelativeLayout activityRecommendWebView;
    @BindView(R.id.my_ProgressBar)
    ProgressBar my_ProgressBar;
    private WebViewClientBase mWebViewClientBase = new WebViewClientBase();
    private WebChromeClientBase mWebChromeClientBase = new WebChromeClientBase();
    private String web_url;
    private int ProgressPosition = 0;
    private String web_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_web_view);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        web_url = intent.getStringExtra(CommonContent.WEBVIEW);
        web_title = intent.getStringExtra(CommonContent.WEBVIEW_TITLE);
        init();
    }

    private void init() {
        WebSetting.register(mWebView);
        mWebView.getSettings().setBuiltInZoomControls(true); // 原网页基础上缩放
        mWebView.setWebViewClient(mWebViewClientBase);
        mWebView.setWebChromeClient(mWebChromeClientBase);
        mWebView.loadUrl(web_url);
        Log.e("biliUrl", web_url);
//        mWebView.loadUrl("https://www.bilibili.com/blackboard/activity-HJttrKEHf.html");
    }

    private class WebViewClientBase extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                return false;
            }
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } catch (Exception e) {
                // 防止没有安装的情况
                e.printStackTrace();
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            my_ProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            my_ProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url,
                                           boolean isReload) {
            // TODO Auto-generated method stub
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    }

    private class WebChromeClientBase extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100 && ProgressPosition <= 2) {
                ProgressPosition++;
            } else if (newProgress >= 80 && ProgressPosition == 3) {
                my_ProgressBar.setVisibility(View.INVISIBLE);
            }
            my_ProgressBar.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }
}

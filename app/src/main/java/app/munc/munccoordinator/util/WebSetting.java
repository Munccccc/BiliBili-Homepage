package app.munc.munccoordinator.util;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by GD on 2017/11/29.
 */

public class WebSetting {

    public static void register(WebView webview) {
        WebSettings webSettings = webview.getSettings();
        webview.removeJavascriptInterface("searchBoxJavaBredge_");// 解决Android4.2以下的安全漏洞
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setSupportZoom(true);//支持缩放
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setPluginState(WebSettings.PluginState.ON);//支持插件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setDomStorageEnabled(true);
        //拿到android UserAgent
//        String userAgentString = webSettings.getUserAgentString();
//        webSettings.setUserAgentString(userAgentString + "gdapp");//添加UA,  “app/XXX”：是与h5商量好的标识，h5确认UA为app/XXX就认为该请求的终端为App
        webview.getSettings().setLightTouchEnabled(true);
        //设置加载进来的页面自适应手机屏幕
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setAllowFileAccess(true); // 允许访问文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }
}

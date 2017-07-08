package com.winter.thunder.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.winter.thunder.R;
import static com.winter.thunder.ui.DetailActivity.form1Info;


public class Info_Fragment extends Fragment {

    public Info_Fragment() {}

    WebView webView;
    public String url = "file:///android_asset/index.html";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        webView = (WebView) v.findViewById(R.id.webViewInfo);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setDomStorageEnabled(true);
        //webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        final MyJavaScriptInterface myJavaScriptInterface
                = new MyJavaScriptInterface(getActivity());
        webView.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                Log.d("onPageFinished", form1Info);
                webView.loadUrl("javascript:FillForm(\""+form1Info+"\")");
            }
        });
        return v;
    }

    public class MyJavaScriptInterface {
        Context mContext;

        MyJavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void callAsync() {
            Toast.makeText(mContext, "INSIDE CALLASYNC ANDROID", Toast.LENGTH_SHORT).show();
        }
    }
}

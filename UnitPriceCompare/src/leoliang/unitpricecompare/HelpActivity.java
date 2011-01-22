package leoliang.unitpricecompare;

import java.util.Locale;

import android.os.Bundle;
import android.webkit.WebView;

public class HelpActivity extends BaseActivity {

    private String getHelpUrl() {
        String prefix = "file:///android_asset/";
        String languageCode = Locale.getDefault().getLanguage();
        if (languageCode.equals("zh")) {
            return prefix + "help.zh.html";
        }
        return prefix + "help.html";
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        WebView browser = (WebView) findViewById(R.id.webView);
        browser.loadUrl(getHelpUrl());
    }

}

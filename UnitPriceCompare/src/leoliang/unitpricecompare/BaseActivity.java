package leoliang.unitpricecompare;

import leoliang.util.Analytics;
import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

    protected static final String LOG_TAG = "UnitPriceCompare";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Analytics.onStartSession(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Analytics.onStartSession(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Analytics.onEndSession(this);
    }

}

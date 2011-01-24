package leoliang.unitpricecompare;

import leoliang.util.Analytics;
import android.app.Activity;

public class BaseActivity extends Activity {

    protected static final String LOG_TAG = "UnitPriceCompare";

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

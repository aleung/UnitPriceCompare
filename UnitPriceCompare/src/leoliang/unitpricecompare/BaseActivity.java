package leoliang.unitpricecompare;

import leoliang.util.Analytics;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity {

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

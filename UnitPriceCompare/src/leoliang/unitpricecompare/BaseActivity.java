package leoliang.unitpricecompare;

import android.app.Activity;

public class BaseActivity extends Activity {

    protected static final String LOG_TAG = "UnitPriceCompare";

    @Override
    public void onStart() {
        super.onStart();
        //        FlurryAgent.onStartSession(this, "5Q82B7WVG6DAIHNFF649");
    }

    @Override
    public void onStop() {
        super.onStop();
        //        FlurryAgent.onEndSession(this);
    }

}

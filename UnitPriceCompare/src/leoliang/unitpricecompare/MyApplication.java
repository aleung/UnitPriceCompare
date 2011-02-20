package leoliang.unitpricecompare;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

@ReportsCrashes(formKey = "dG5PY0dRaXRGQmpLeERCcTZTMWdrc0E6MQ")
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        // The following line triggers the initialization of ACRA
        ACRA.init(this);
        super.onCreate();
    }
}

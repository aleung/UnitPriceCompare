package leoliang.android.widget;

import leoliang.unitpricecompare.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class DigitKeyboard extends LinearLayout {

    public DigitKeyboard(Context context) {
        this(context, null);
    }

    public DigitKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.digit_keyboard, this, true);

        // TODO Auto-generated constructor stub
    }

}

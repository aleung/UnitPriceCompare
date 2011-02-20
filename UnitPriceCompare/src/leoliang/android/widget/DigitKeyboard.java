package leoliang.android.widget;

import java.util.ArrayList;
import java.util.Collection;

import leoliang.unitpricecompare.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DigitKeyboard extends LinearLayout {

    private int[] nonDigitKeys = new int[] { R.id.key_division, R.id.key_multiply, R.id.key_plus, R.id.key_minus,
            R.id.key_left_parenthesis, R.id.key_right_parenthesis };

    public DigitKeyboard(Context context) {
        this(context, null);
    }

    public DigitKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.digit_keyboard, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomInput);
        final int inputFieldId = a.getResourceId(R.styleable.CustomInput_inputField, -1);
        final Activity activity = (Activity) context;

        a = context.obtainStyledAttributes(attrs, R.styleable.DigitKeyboard);
        final boolean digitOnly = a.getBoolean(R.styleable.DigitKeyboard_digitOnly, false);

        Collection<View> children = getLeafChildrenView(this);
        for (final View child : children) {
            if (child instanceof ImageButton) {
                if (child.getId() == R.id.key_back) {
                    child.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(@SuppressWarnings("unused") View v) {
                            TextView inputField = (TextView) activity.findViewById(inputFieldId);
                            String text = inputField.getText().toString();
                            if (text.length() > 0) {
                                inputField.setText(text.subSequence(0, text.length() - 1));
                            }
                        }
                    });
                }
                if (child.getId() == R.id.key_clear) {
                    child.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(@SuppressWarnings("unused") View v) {
                            TextView inputField = (TextView) activity.findViewById(inputFieldId);
                            inputField.setText("");
                        }
                    });
                }
            }
            if (child instanceof Button) {
                if (digitOnly) {
                    if (!isDigitKey(child.getId())) {
                        ((Button) child).setText("");
                    }
                }
                child.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused") View v) {
                        TextView inputField = (TextView) activity.findViewById(inputFieldId);
                        inputField.append(((Button) child).getText());
                    }
                });
            }
        }
    }

    private Collection<View> getLeafChildrenView(ViewGroup viewGroup) {
        Collection<View> children = new ArrayList<View>();
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = viewGroup.getChildAt(i);
            if (child == null) {
                continue;
            }
            if (child instanceof ViewGroup) {
                children.addAll(getLeafChildrenView((ViewGroup) child));
            } else {
                children.add(child);
            }
        }
        return children;
    }

    private boolean isDigitKey(int id) {
        for (int keyId:nonDigitKeys) {
            if (id == keyId) {
                return false;
            }
        }
        return true;
    }

}

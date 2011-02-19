package leoliang.android.widget;

import java.util.HashSet;
import java.util.Set;

import leoliang.unitpricecompare.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * Compound multiple <code>RadioGroup</code>s into one group. Check one <code>RadioButton</code> will cause all other
 * groups clear check.
 * 
 * Must not call <code>setOnCheckedChangeListener()</code> on a <code>RadioGroup</code> which is added to a
 * <code>CompoundRadioGroup</code>.
 */
public class CompoundRadioGroup extends LinearLayout {

    private Activity activity;
    private Set<RadioGroup> groups = new HashSet<RadioGroup>();
    private boolean switchingGroup = false;
    private int inputFieldId;
    private boolean disableCheckedChangeListener;
    private OnCheckedChangeListener onCheckedChangeListener = null;

    private OnCheckedChangeListener groupSwitcher = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // switchingGroup: Workaround for Android issue 4785, http://code.google.com/p/android/issues/detail?id=4785
            if ((checkedId == -1) || switchingGroup || disableCheckedChangeListener) {
                return;
            }
            switchingGroup = true;
            clearGroupsOtherThan(group);
            switchingGroup = false;

            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(group, checkedId);
            }

            updateInputField(checkedId);
        }
    };

    public CompoundRadioGroup(Context context) {
        this(context, null);
    }

    public CompoundRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomInput);
        inputFieldId = a.getResourceId(R.styleable.CustomInput_inputField, -1);
        activity = (Activity) context;
    }

    private void addRadioGroup(View child) {
        if (child instanceof RadioGroup) {
            RadioGroup group = (RadioGroup) child;
            groups.add(group);
            group.setOnCheckedChangeListener(groupSwitcher);
        }
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        addRadioGroup(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        addRadioGroup(child);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        addRadioGroup(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        addRadioGroup(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        addRadioGroup(child);
    }

    /**
     * Sets the selection to the radio button whose identifier is passed in parameter.
     * 
     * Note: <code>OnCheckedChangeListener</code> will not be invoked.
     * 
     * @param id
     */
    public void check(int id) {
        disableCheckedChangeListener = true;
        for (RadioGroup g : groups) {
            g.check(id);
        }
        updateInputField(id);
        disableCheckedChangeListener = false;
    }

    private void clearGroupsOtherThan(RadioGroup group) {
        for (RadioGroup g : groups) {
            if (g != group) {
                g.clearCheck();
            }
        }
    }

    public int getCheckedRadioButtonId() {
        int id = -1;
        for (RadioGroup g : groups) {
            id = g.getCheckedRadioButtonId();
            if (id != -1) {
                break;
            }
        }
        return id;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    private void updateInputField(int checkedId) {
        if (inputFieldId != -1) {
            TextView inputField = (TextView) activity.findViewById(inputFieldId);
            RadioButton checked = (RadioButton) findViewById(checkedId);
            inputField.setText(checked.getText());
        }
    }
}

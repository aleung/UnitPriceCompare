package leoliang.unitpricecompare;

import java.util.HashMap;
import java.util.Map;

import leoliang.android.widget.CompoundRadioGroup;
import leoliang.unitpricecompare.model.Quantity;
import leoliang.unitpricecompare.model.ShoppingItem;
import leoliang.util.Analytics;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

/**
 * UI for input shopping item information.
 */
public class ShoppingItemActivity extends BaseActivity {

    public static final String EXTRA_SHOPPING_ITEM = "shoppingItem";
    public static final String EXTRA_SHOPPING_ITEM_INDEX = "shoppingItemIndex";

    private static final String TAB_UNIT = "unit";
    private static final String TAB_QUANTITY = "quantity";
    private static final String TAB_PRICE = "price";

    private ShoppingItem shoppingItem;
    private CompoundRadioGroup radioGroups;
    private TabHost tabs;
    private Map<Integer, String> buttons;

    private int getUnitButtonId(String unitName) {
        for (int id : buttons.keySet()) {
            if (buttons.get(id).equals(unitName)) {
                return id;
            }
        }
        Log.w(LOG_TAG, "getUnitButtonId(): Unknow unit: " + unitName);
        return -1;
    }

    private String getUnitFromButtonId(int id) {
        return buttons.get(id);
    }

    private void initializeUnitButtonMap() {
        buttons = new HashMap<Integer, String>();

        buttons.put(R.id.ItemDialog_Unit_mm, "mm");
        buttons.put(R.id.ItemDialog_Unit_cm, "cm");
        buttons.put(R.id.ItemDialog_Unit_m, "m");
        buttons.put(R.id.ItemDialog_Unit_inch, "inch");
        buttons.put(R.id.ItemDialog_Unit_ft, "ft");
        buttons.put(R.id.ItemDialog_Unit_yd, "yd");

        buttons.put(R.id.ItemDialog_Unit_g, "g");
        buttons.put(R.id.ItemDialog_Unit_kg, "kg");
        buttons.put(R.id.ItemDialog_Unit_oz, "oz");
        buttons.put(R.id.ItemDialog_Unit_lb, "lb");

        buttons.put(R.id.ItemDialog_Unit_ml, "ml");
        buttons.put(R.id.ItemDialog_Unit_cl, "cl");
        buttons.put(R.id.ItemDialog_Unit_l, "L");
        buttons.put(R.id.ItemDialog_Unit_floz, "fl.oz");
        buttons.put(R.id.ItemDialog_Unit_gal, "gal");

        buttons.put(R.id.ItemDialog_Unit_none, "");
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeUnitButtonMap();

        // init UI components

        setContentView(R.layout.item);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tabs = (TabHost) findViewById(R.id.TabHost);
        tabs.setup();

        TabSpec tabSpecPrice = tabs.newTabSpec(TAB_PRICE);
        View tabIndicatorPrice = inflater.inflate(R.layout.price_tab, null);
        tabIndicatorPrice.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 3.0f));
        tabSpecPrice.setIndicator(tabIndicatorPrice);
        tabSpecPrice.setContent(R.id.tabContent_price);
        tabs.addTab(tabSpecPrice);

        TabSpec tabSpecQuantity = tabs.newTabSpec(TAB_QUANTITY);
        View tabIndicatorQuantity = inflater.inflate(R.layout.quantity_tab, null);
        tabIndicatorQuantity.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 5.0f));
        tabSpecQuantity.setIndicator(tabIndicatorQuantity);
        tabSpecQuantity.setContent(R.id.tabContent_quantity);
        tabs.addTab(tabSpecQuantity);

        TabSpec tabSpecUnit = tabs.newTabSpec(TAB_UNIT);
        View tabIndicatorUnit = inflater.inflate(R.layout.unit_tab, null);
        tabIndicatorUnit.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 2.0f));
        tabSpecUnit.setIndicator(tabIndicatorUnit);
        tabSpecUnit.setContent(R.id.tabContent_unit);
        tabs.addTab(tabSpecUnit);

        final Button okButton = (Button) findViewById(R.id.ItemDialog_ok);
        okButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(@SuppressWarnings("unused") View view) {
                if (updateShoppingItem()) {
                    Intent intent = getIntent();
                    intent.putExtra(EXTRA_SHOPPING_ITEM, shoppingItem);
                    ShoppingItemActivity.this.setResult(RESULT_OK, intent);
                    ShoppingItemActivity.this.finish();
                }
            }
        });
        Button cancelButton = (Button) findViewById(R.id.ItemDialog_cancel);
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(@SuppressWarnings("unused") View view) {
                ShoppingItemActivity.this.setResult(RESULT_CANCELED);
                ShoppingItemActivity.this.finish();
            }
        });

        final TextView priceField = (TextView) findViewById(R.id.ItemDialog_PriceField);
        final TextView quantityField = (TextView) findViewById(R.id.ItemDialog_QuantityField);
        radioGroups = (CompoundRadioGroup) findViewById(R.id.tabContent_unit);

        // init values

        shoppingItem = (ShoppingItem) getIntent().getSerializableExtra(EXTRA_SHOPPING_ITEM);
        if (shoppingItem != null) {
            priceField.setText(Double.toString(shoppingItem.getPrice()));
            Quantity quantity = shoppingItem.getQuantity();
            quantityField.setText(quantity.getValueExpression());

            int unitButtonId = getUnitButtonId(quantity.getUnitName());
            radioGroups.check(unitButtonId);
        } else {
            shoppingItem = new ShoppingItem();
        }

        Map<String, String> eventParameters = new HashMap<String, String>();
        eventParameters.put("screenOrientation", String.valueOf(getResources().getConfiguration().orientation));
        Analytics.onEvent("openShoppingItemActivity", eventParameters);
    }

    /**
     * @return false if input is invalid
     */
    protected boolean updateShoppingItem() {

        TextView priceField = (TextView) findViewById(R.id.ItemDialog_PriceField);
        String price = priceField.getText().toString();
        try {
            shoppingItem.setPrice(Double.parseDouble(price));
        } catch (NumberFormatException e) {
            tabs.setCurrentTabByTag(TAB_PRICE);
            Toast.makeText(getApplicationContext(), R.string.invalid_input_price, Toast.LENGTH_LONG).show();
            return false;
        }

        TextView quantityField = (TextView) findViewById(R.id.ItemDialog_QuantityField);
        try {
            shoppingItem.getQuantity().setValue(quantityField.getText().toString());
        } catch (ArithmeticException e) {
            tabs.setCurrentTabByTag(TAB_QUANTITY);
            Toast.makeText(getApplicationContext(), R.string.invalid_input_quantity, Toast.LENGTH_LONG).show();
            return false;
        }

        int checkedRadioButtonId = radioGroups.getCheckedRadioButtonId();
        String unit = getUnitFromButtonId(checkedRadioButtonId);
        if (unit == null) {
            Log.w(LOG_TAG, "updateShoppingItem(): Unknown button ID: " + checkedRadioButtonId);
            tabs.setCurrentTabByTag(TAB_UNIT);
            Toast.makeText(getApplicationContext(), R.string.invalid_input_unit, Toast.LENGTH_LONG).show();
            return false;
        }
        shoppingItem.getQuantity().setUnit(unit);
        shoppingItem.setEnabled(true);

        return true;
    }
}

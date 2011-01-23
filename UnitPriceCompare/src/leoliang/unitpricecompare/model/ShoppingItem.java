package leoliang.unitpricecompare.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class ShoppingItem implements Serializable {

    private double price;
    private Quantity quantity;
    private boolean enabled = true;

    public ShoppingItem() {
        quantity = new Quantity();
    }

    public ShoppingItem(JSONObject jsonObject) throws JSONException {
        setPrice(jsonObject.getDouble("price"));
        setEnabled(jsonObject.getBoolean("enabled"));
        quantity = new Quantity(jsonObject.getJSONObject("quantity"));
    }

    public double getPrice() {
        return price;
    }

    public double getPricePerUnit() {
        return price / quantity.getQuantity();
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public double getPricePerBasicUnit() {
        return price / quantity.getQuantityInBasicUnit();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isValid() {
        return ((price > 0) && quantity.isValid());
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public JSONObject toJson() throws JSONException {
        return new JSONObject().put("price", price).put("quantity", quantity.toJson()).put("enabled", enabled);
    }
}

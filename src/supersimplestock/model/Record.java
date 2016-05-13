package supersimplestock.model;

import java.util.Date;

/**
 *
 * @author sreale
 */
public class Record {

    private String id;
    private Date timestamp;
    private int quantity;
    private BuySell indicator;
    private double price;

    public Record(String id, Date timestamp, int quantity, BuySell indicator, double price) {
        this.id = id;
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.indicator = indicator;
        this.price = price;
    }

    public String getId() {
        return id;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BuySell getIndicator() {
        return indicator;
    }

    public void setIndicator(BuySell indicator) {
        this.indicator = indicator;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
package supersimplestock.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sreale
 */
public class Stock {

    private String id;
    private double lastDividend;
    private double parValue;
    private double price;
    private List<Record> records;

    public Stock(String id, double lastDividend, double parValue, double price) {
        this.id = id;
        this.lastDividend = lastDividend;
        this.parValue = parValue;
        this.price = price;
        this.records = new ArrayList<Record>();
    }

    public String getId() {
        return id;
    }

    public double getLastDividend() {
        return lastDividend;
    }

    public void setLastDividend(double lastDividend) {
        this.lastDividend = lastDividend;
    }

    public double getParValue() {
        return parValue;
    }

    public void setParValue(double parValue) {
        this.parValue = parValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Record> getRecords() {
        return records;
    }

}

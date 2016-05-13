package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import supersimplestock.model.BuySell;
import supersimplestock.model.Record;
import supersimplestock.model.Stock;

/**
 *
 * @author sreale
 */
public class SuperSimpleStockServiceImpl implements SuperSimpleStockService {

    //Simulate the local stock data.
    private Map<String, Stock> dataStock;

    public SuperSimpleStockServiceImpl() {
        this.dataStock = new HashMap<String, Stock>();
    }

    public Map<String, Stock> getDataStock() {
        return dataStock;
    }

    /**
     *
     * @param id of the stock
     * @param price to calculate
     * @return the Dividend Yield
     * @throws Exception
     */
    @Override
    public double getDividendYield(String id, double price) throws Exception {

        double result = 0;
        Stock stock = dataStock.get(id);

        if (stock != null) {

            if (price <= 0) {
                throw new Exception("Price must be > 0");
            } else {
                result = stock.getLastDividend() / price;
            }
        } else {
            throw new Exception("Stock not found");
        }
        return result;
    }

    /**
     *
     * @param id of stock
     * @param price to calculate the P/E Ratio
     * @return the P/E Ratio
     * @throws Exception
     */
    @Override
    public double getPERatio(String id, double price) throws Exception {
        Stock stock = dataStock.get(id);
        double result = 0;

        if (stock != null) {

            if (price <= 0) {
                throw new Exception("Price must be > 0");
            } else {
                result = price / stock.getLastDividend();
            }
        } else {
            throw new Exception("Stock not found");
        }

        return result;
    }

    /**
     *
     * @param id of stock
     * @param timestamp when record the new trade
     * @param quantity of inserted trade
     * @param indicator that specified the sell or buy
     * @param price trade
     * @throws Exception
     */
    @Override
    public void recordTrade(String id, Date timestamp, int quantity, BuySell indicator, double price) throws Exception {
        Stock stock = dataStock.get(id);

        if (stock != null) {
            if (price > 0 && quantity > 0) {
                Record record = new Record(id, timestamp, quantity, indicator, price);
                stock.getRecords().add(record);
            } else {
                throw new Exception("Error: price and quantity must be > 0");
            }
        } else {
            throw new Exception("Stock not found");
        }

    }

    /**
     *
     * @param id of stock
     * @param minutes to search
     * @return
     * @throws Exception
     */
    @Override
    public double getVolumeWeightedStockPriceForMinutes(String id, int minutes) throws Exception {

        Stock stock = dataStock.get(id);
        double result = 0;

        if (stock != null) {
            List<Record> records = getRecordsByTime(stock, minutes);
            if (records.isEmpty()) {
                return result;
            }

            double priceSum = 0;
            int quantitySum = 0;

            for (Record record : records) {
                double price = record.getPrice();
                if (price > 0) {
                    double quatity = (record.getQuantity());
                    if (quatity > 0) {
                        priceSum += price * quatity;
                        quantitySum += record.getQuantity();
                    }

                }

            }
            result = priceSum / quantitySum;
        } else {
            throw new Exception("Stock not found");
        }

        return result;

    }

    private List<Record> getRecordsByTime(Stock stock, int minutes) {
        List<Record> result = new ArrayList<Record>();
        Date currentTime = new Date();
        for (Record record : stock.getRecords()) {
            if (currentTime.getTime() - record.getTimestamp().getTime() <= minutes * 60 * 1000) {
                result.add(record);
            }
        }

        return result;

    }

    @Override
    public double getGBCEAllShareIndex() throws Exception {
        double accumulate = 1;
        double x = accumulate;
        if (!dataStock.isEmpty()) {

            int size = dataStock.size();

            for (Stock stock : dataStock.values()) {
                double price = stock.getPrice();
                if (price > 0) {
                    accumulate = accumulate * price;
                }
            }
// Calculate geometric mean

            double temp = 0;
            double e = 0.1;

            do {
                temp = x;
                x = x + (accumulate - Math.pow(x, size)) / (size * Math.pow(x, (size - 1)));
            } while (Math.abs(x - temp) > e);

        } else {
            throw new Exception("Data stock empty");
        }

        return x;
    }

}

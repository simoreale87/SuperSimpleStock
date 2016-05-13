package services;

import java.util.Date;
import java.util.Map;
import supersimplestock.model.BuySell;
import supersimplestock.model.Stock;

/**
 *
 * @author sreale
 */
public interface SuperSimpleStockService {
    
    public Map<String, Stock> getDataStock();

    public double getDividendYield(String id, double price) throws Exception;

    public double getPERatio(String id, double price) throws Exception;
    
    public void recordTrade(String id, Date timestamp, int quantity, BuySell indicator, double price) throws Exception;

    public double getVolumeWeightedStockPriceForMinutes(String id, int minutes) throws Exception;

    public double getGBCEAllShareIndex() throws Exception;
}

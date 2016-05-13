package supersimplestock.serice.test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import services.SuperSimpleStockService;
import services.SuperSimpleStockServiceImpl;
import supersimplestock.model.BuySell;
import supersimplestock.model.Record;
import supersimplestock.model.Stock;

/**
 *
 * @author sreale
 */
public class SuperSimpleStockServiceTest {

    private SuperSimpleStockService service;

    public SuperSimpleStockServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        //Stubs
        service = new SuperSimpleStockServiceImpl();
        service.getDataStock().put("1", new Stock("s1", 10, 20, 30));

        service.getDataStock().get("1").getRecords().add(new Record("r1", Timestamp.from(Instant.MIN), 3, BuySell.BUY, 10));
        service.getDataStock().get("1").getRecords().add(new Record("r2", Timestamp.from(Instant.MIN), 5, BuySell.BUY, 15));

        service.getDataStock().put("2", new Stock("s2", 8, 100, 30));

        service.getDataStock().get("2").getRecords().add(new Record("r3", Timestamp.from(Instant.MIN), 7, BuySell.BUY, 15));
        service.getDataStock().get("2").getRecords().add(new Record("r4", Timestamp.from(Instant.MIN), 9, BuySell.BUY, 20));
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void getDividendYield() throws Exception {

        assertNotNull(service);
        double value = service.getDividendYield("1", 20);
        assertEquals(value, 0.5, 0);

        value = service.getDividendYield("2", 20);
        assertEquals(value, 0.4, 0);
    }

    @Test
    public void getGBCEAllShareIndex() throws Exception {

        assertNotNull(service);
        double value = service.getGBCEAllShareIndex();
        assertEquals(value, 30, 0.001); // 0.001 of tolerance for geometric average
    }

    @Test
    public void getPERatio() throws Exception {
        double value = service.getPERatio("1", 30);
        assertEquals(value, 3, 0);

        value = service.getPERatio("2", 30);
        assertEquals(value, 3.75, 0);
    }

    @Test
    public void recordTrace() throws Exception {

        Timestamp time = Timestamp.from(Instant.MIN);
        service.recordTrade("1", time, 2, BuySell.BUY, 3);
        //double volume = service.getVolumeWeightedStockPriceForMinutes("1", 5);
        Stock toTest = service.getDataStock().get("1");
        List<Record> records = toTest.getRecords();
        assertEquals(records.size(), 3); // 2 existent + 1 added now
        assertEquals(records.get(2).getTimestamp(), time); // verify latest element inserted

        //assertEquals(volume, 3, 0);
    }

    @Test
    public void getVolumeWeightedStockPriceForMinutes() throws Exception {
        double value = service.getVolumeWeightedStockPriceForMinutes("1", 15); // the test of exercise
        assertEquals(value, 13.125, 0);
    }
}

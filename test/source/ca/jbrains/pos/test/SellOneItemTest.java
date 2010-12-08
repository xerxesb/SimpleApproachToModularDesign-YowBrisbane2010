package ca.jbrains.pos.test;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.junit.*;
import org.junit.runner.*;

@RunWith(JMock.class)
public class SellOneItemTest {
    public interface Catalog {
        int findPriceByBarcode(String barcode);
    }

    public interface Display {
        void displayPrice(int priceInCents);
    }

    private JUnit4Mockery mockery = new JUnit4Mockery();
    private Display display = mockery.mock(Display.class);
    private Catalog catalog = mockery.mock(Catalog.class);

    @Test
    public void productFound() throws Exception {
        mockery.checking(new Expectations() {
            {
                allowing(catalog).findPriceByBarcode(with(any(String.class)));
                will(returnValue(950));
            }
        });

        mockery.checking(new Expectations() {
            {
                oneOf(display).displayPrice(950);
            }
        });

        onBarcode("123");
    }

    private void onBarcode(String barcode) {
        display.displayPrice(catalog.findPriceByBarcode(barcode));
    }
}

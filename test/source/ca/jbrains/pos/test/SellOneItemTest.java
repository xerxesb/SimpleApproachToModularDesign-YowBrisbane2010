package ca.jbrains.pos.test;

import static org.junit.Assert.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.junit.*;
import org.junit.runner.*;

@RunWith(JMock.class)
public class SellOneItemTest {
    public interface Catalog {
        int findPriceByBarcode(String barcode);

        boolean hasBarcode(String barcode);
    }

    public interface Display {
        void displayPrice(int priceInCents);

        void displayProductNotFoundMessage(String barcode);
    }

    private JUnit4Mockery mockery = new JUnit4Mockery();
    private Display display = mockery.mock(Display.class);
    private Catalog catalog = mockery.mock(Catalog.class);

    @Test
    public void productFound() throws Exception {
        mockery.checking(new Expectations() {
            {
                allowing(catalog).hasBarcode(with(any(String.class)));
                will(returnValue(true));

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

    @Test
    public void productNotFound() throws Exception {
        mockery.checking(new Expectations() {
            {
                allowing(catalog).hasBarcode(with(any(String.class)));
                will(returnValue(false));
            }
        });

        mockery.checking(new Expectations() {
            {
                oneOf(display).displayProductNotFoundMessage("123");
            }
        });

        onBarcode("123");
    }

    private void onBarcode(String barcode) {
        if (catalog.hasBarcode(barcode))
            display.displayPrice(catalog.findPriceByBarcode(barcode));
        else
            display.displayProductNotFoundMessage(barcode);
    }
}

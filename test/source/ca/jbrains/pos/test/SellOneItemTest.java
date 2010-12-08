package ca.jbrains.pos.test;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.*;

public class SellOneItemTest {
    public static class Sale {
        private StringWriter canvas;
        private Map<String, String> pricesByBarcode;

        public Sale(StringWriter canvas, Map<String, String> pricesByBarcode) {
            this.canvas = canvas;
            this.pricesByBarcode = pricesByBarcode;
        }

        public void onBarcode(String barcode) {
            // SMELL Seems like I should move this out a new
            // client that validates the barcodes. Not sure.
            if ("".equals(barcode)) {
                canvas.write("Scanning error: empty barcode");
                return;
            }

            if (pricesByBarcode.containsKey(barcode))
                displayPrice(findPriceByBarcode(barcode));
            else
                displayProductNotFoundMessage(barcode);
        }

        private void displayPrice(String price) {
            canvas.write(price);
        }

        private String findPriceByBarcode(String barcode) {
            return pricesByBarcode.get(barcode);
        }

        private void displayProductNotFoundMessage(String barcode) {
            canvas.write("No product found for " + barcode);
        }
    }

    @Test
    public void productFound() throws Exception {
        StringWriter canvas = new StringWriter();
        Sale sale = new Sale(canvas, new HashMap<String, String>() {
            {
                put("123", "$9.50");
            }
        });

        sale.onBarcode("123");

        assertEquals("$9.50", canvas.toString());
    }

    @Test
    public void anotherProductFound() throws Exception {
        StringWriter canvas = new StringWriter();
        Sale sale = new Sale(canvas, new HashMap<String, String>() {
            {
                put("456", "$17.26");
            }
        });

        sale.onBarcode("456");

        assertEquals("$17.26", canvas.toString());
    }

    @Test
    public void productNotFound() throws Exception {
        StringWriter canvas = new StringWriter();
        Sale sale = new Sale(canvas, Collections.<String, String> emptyMap());

        sale.onBarcode("999");

        assertEquals("No product found for 999", canvas.toString());
    }

    @Test
    public void emptyBarcode() throws Exception {
        StringWriter canvas = new StringWriter();
        Sale sale = new Sale(canvas, null);

        sale.onBarcode("");

        assertEquals("Scanning error: empty barcode", canvas.toString());
    }
}

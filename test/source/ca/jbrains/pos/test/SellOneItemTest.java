package ca.jbrains.pos.test;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.*;

public class SellOneItemTest {
    public static class Sale {
        private final StringWriter canvas;

        public Sale(StringWriter canvas) {
            this.canvas = canvas;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode)) {
                canvas.write("Scanning error: empty barcode");
                return;
            }

            Map<String, String> pricesByBarcode = new HashMap<String, String>() {
                {
                    put("123", "$9.50");
                    put("456", "$17.26");
                }
            };

            if (pricesByBarcode.containsKey(barcode))
                canvas.write(pricesByBarcode.get(barcode));
            else
                canvas.write("No product found for " + barcode);
        }
    }

    @Test
    public void productFound() throws Exception {
        StringWriter canvas = new StringWriter();
        Sale sale = new Sale(canvas);

        sale.onBarcode("123");

        assertEquals("$9.50", canvas.toString());
    }

    @Test
    public void anotherProductFound() throws Exception {
        StringWriter canvas = new StringWriter();
        Sale sale = new Sale(canvas);

        sale.onBarcode("456");

        assertEquals("$17.26", canvas.toString());
    }

    @Test
    public void productNotFound() throws Exception {
        StringWriter canvas = new StringWriter();
        Sale sale = new Sale(canvas);

        sale.onBarcode("999");

        assertEquals("No product found for 999", canvas.toString());
    }

    @Test
    public void emptyBarcode() throws Exception {
        StringWriter canvas = new StringWriter();
        Sale sale = new Sale(canvas);

        sale.onBarcode("");

        assertEquals("Scanning error: empty barcode", canvas.toString());
    }
}

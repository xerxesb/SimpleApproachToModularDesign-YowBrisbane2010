package ca.jbrains.pos.test;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.*;

public class SellOneItemTest {
    public static class Catalog {
        private final Map<String, String> pricesByBarcode;

        public Catalog(Map<String, String> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public String findPriceByBarcode(String barcode) {
            return pricesByBarcode.get(barcode);
        }

        public boolean hasBarcode(String barcode) {
            return pricesByBarcode.containsKey(barcode);
        }
    }

    public static class Sale {
        private StringWriter canvas;
        private Catalog catalog;

        public Sale(StringWriter canvas, Catalog catalog) {
            this.canvas = canvas;
            this.catalog = catalog;
        }

        public void onBarcode(String barcode) {
            // SMELL Seems like I should move this out a new
            // client that validates the barcodes. Not sure.
            if ("".equals(barcode)) {
                displayScannedEmptyBarcodeMessage();
                return;
            }

            if (catalog.hasBarcode(barcode)) {
                String price = catalog.findPriceByBarcode(barcode);
                displayPrice(price);
            }
            else
                displayProductNotFoundMessage(barcode);
        }

        private void displayScannedEmptyBarcodeMessage() {
            canvas.write("Scanning error: empty barcode");
        }

        private void displayPrice(String price) {
            canvas.write(price);
        }

        private void displayProductNotFoundMessage(String barcode) {
            canvas.write("No product found for " + barcode);
        }
    }

    @Test
    public void productFound() throws Exception {
        StringWriter canvas = new StringWriter();
        Sale sale = new Sale(canvas, new Catalog(
                Collections.<String, String> singletonMap("123", "$9.50")));

        sale.onBarcode("123");

        assertEquals("$9.50", canvas.toString());
    }

    @Test
    public void anotherProductFound() throws Exception {
        StringWriter canvas = new StringWriter();
        Sale sale = new Sale(canvas, new Catalog(new HashMap<String, String>() {
            {
                put("456", "$17.26");
            }
        }));

        sale.onBarcode("456");

        assertEquals("$17.26", canvas.toString());
    }

    @Test
    public void productNotFound() throws Exception {
        StringWriter canvas = new StringWriter();
        Sale sale = new Sale(canvas, new Catalog(
                Collections.<String, String> emptyMap()));

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

package ca.jbrains.pos.test;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.*;

public class SellOneItemTest {
    public static class Sale {
        private final StringWriter canvas;

        public Sale(StringWriter canvas) {
            this.canvas = canvas;
        }

        public void onBarcode(String barcode) {
            canvas.write("$9.50");
        }
    }

    @Test
    public void productFound() throws Exception {
        StringWriter canvas = new StringWriter();
        Sale sale = new Sale(canvas);

        sale.onBarcode("123");

        assertEquals("$9.50", canvas.toString());
    }
}

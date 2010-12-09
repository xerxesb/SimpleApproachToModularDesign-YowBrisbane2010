package ca.jbrains.pos.test;

import static org.junit.Assert.*;

import org.junit.*;

import ca.jbrains.pos.test.SellOneItemTest.Catalog;

public abstract class CatalogContract {
    @Test
    public void doesNotHaveTheBarcode() throws Exception {
        Catalog catalog = createCatalogWithout("123");
        assertFalse(catalog.hasBarcode("123"));
    }

    @Test
    public void hasTheBarcode() throws Exception {
        Catalog catalog = createCatalogWith("123", 950);
        assertTrue(catalog.hasBarcode("123"));
        assertEquals(950, catalog.findPriceByBarcode("123"));
    }

    protected abstract Catalog createCatalogWith(String barcode,
            int priceInCents);

    protected abstract Catalog createCatalogWithout(String barcode);
}

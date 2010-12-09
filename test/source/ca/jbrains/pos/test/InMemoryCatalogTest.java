package ca.jbrains.pos.test;

import java.util.*;

import ca.jbrains.pos.test.SellOneItemTest.*;

public class InMemoryCatalogTest extends CatalogContract {

    public static class InMemoryCatalog implements Catalog {

        private final Map<String, Integer> pricesByBarcode;

        public InMemoryCatalog(Map<String, Integer> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        @Override
        public int findPriceByBarcode(String barcode) {
            return pricesByBarcode.get(barcode);
        }

        @Override
        public boolean hasBarcode(String barcode) {
            return pricesByBarcode.containsKey(barcode);
        }

    }

    @Override
    protected Catalog createCatalogWith(String barcode, int priceInCents) {
        return new InMemoryCatalog(Collections.<String, Integer> singletonMap(
                barcode, priceInCents));
    }

    @Override
    protected Catalog createCatalogWithout(String barcode) {
        return new InMemoryCatalog(Collections.<String, Integer> singletonMap(
                barcode + "alksdjfh", -762));
    }

}

package com.shop.data;

import com.shop.model.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public final class ProductRepository {

    private static final List<Product> PRODUCTS;

    static {
        List<Product> list = new ArrayList<Product>();
        list.add(new Product(1, "Wireless Mouse", "Electronics", 799.00, "2.4 GHz wireless mouse with silent clicks"));
        list.add(new Product(2, "Mechanical Keyboard", "Electronics", 3499.00, "Blue-switch keyboard with backlight"));
        list.add(new Product(3, "Running Shoes", "Fashion", 2599.00, "Lightweight shoes for daily running"));
        list.add(new Product(4, "Cotton T-Shirt", "Fashion", 499.00, "100% cotton, regular fit"));
        list.add(new Product(5, "Stainless Steel Bottle", "Home", 649.00, "1 litre insulated bottle"));
        list.add(new Product(6, "LED Desk Lamp", "Home", 1199.00, "Adjustable brightness desk lamp"));
        PRODUCTS = Collections.unmodifiableList(list);
    }

    private ProductRepository() { }

    public static List<Product> findAll() {
        return PRODUCTS;
    }

    public static List<Product> findByCategory(String category) {
        List<Product> result = new ArrayList<Product>();
        for (Product p : PRODUCTS) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                result.add(p);
            }
        }
        return result;
    }

    public static List<String> categories() {
        TreeSet<String> set = new TreeSet<String>();
        for (Product p : PRODUCTS) {
            set.add(p.getCategory());
        }
        return new ArrayList<String>(set);
    }
}

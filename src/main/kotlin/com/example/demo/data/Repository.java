package com.example.demo.data;

import java.util.List;

public interface Repository {

    List<Product> getAll();

    Product getById(int id);

    boolean addProduct(Product car);

    boolean deleteProduct(int id);

    List<Product> getAllByCategory(String category);

    List<Product> getAllByInStock(String inStock);
}

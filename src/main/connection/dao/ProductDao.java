package connection.dao;

import entities.Product;

import java.util.List;

public interface ProductDao {
  Product getProdById(Long id);
  void insertProd(Product prod);
  void deleteProd(Long codprod);
  List<Product> getAll(String date, String userInput);
  List<Product> getAllWithoutDate(String userInput);
}

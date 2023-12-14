package HW.models.basket;

import HW.models.enums.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Basket {
  private ConcurrentHashMap<Product, Float> products = new ConcurrentHashMap<>();

  public synchronized void alterContent (Product product, float change) {
    if (products.containsKey(product)) {
      float newValue = products.get(product) + change;
      if (newValue < 0) {
        throw new IllegalArgumentException("Basket content can't be negative");
      }
      products.put(product, newValue);
    } else {
      products.put(product, change);
    }
  }

  public synchronized void clearBasket () {
    products.clear();
  }

  public Map<Product, Float> getProducts () {
    return  Map.copyOf(products);
  }
}

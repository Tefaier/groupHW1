package HW.models.basket;

import HW.models.enums.Product;

import java.util.HashMap;
import java.util.Map;

public interface BasketRepository {
  void changeBasketContent (long id, Product product, float change);
  void tryOrderBasket (long id);
  void removeBasket(long id);
  Map<Long, Basket> getContent();
}

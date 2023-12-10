package HW.models.basket;

import HW.models.enums.Product;

import java.util.function.Predicate;

public interface BasketRepository {
  void changeBasketContent (long id, Product product, float change);
  void tryOrderBasket (long id);
}

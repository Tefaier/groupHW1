package HW.models.basket;

import HW.models.enums.Product;

public interface BasketRepository {
  void changeBasketContent (long id, Product product, float change);
  void tryOrderBasket (long id);
  void removeBasket(long id);
}

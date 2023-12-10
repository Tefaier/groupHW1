package HW.models.basket;

import HW.models.enums.Product;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class BasketRepositoryInMemory implements BasketRepository{
  private static final Logger logger = Logger.getLogger(BasketRepositoryInMemory.class.getSimpleName());
  private final ConcurrentHashMap<Long, Basket> baskets = new ConcurrentHashMap<>();
  // private final Sclad;

  @Override
  public void changeBasketContent(long id, Product product, float change) {
    logger.info("Basket with id " + id + " was changed: product->" + product.name() + " change->" + change);
    synchronized (Long.valueOf(id)) {
      if (!baskets.containsKey(id)) {
        baskets.put(id, new Basket());
      }
      try {
        baskets.get(id).alterContent(product, change);
      } catch (IllegalArgumentException e) {
        logger.info("Change to basket with id " + id + " wasn't made");
      }
    }
  }

  @Override
  public void tryOrderBasket(long id) {
    logger.info("Basket with id " + id + " was ordered");
    synchronized (Long.valueOf(id)) {
      try {
        // baskets.get(id).getProducts();
        // request to sklad
        baskets.get(id).clearBasket();
      } catch (RuntimeException e) {
        logger.info("Basket with id " + id + " was ordered but failed!");
      }
    }
  }
}

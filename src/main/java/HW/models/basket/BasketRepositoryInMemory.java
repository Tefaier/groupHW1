package HW.models.basket;

import HW.models.enums.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BasketRepositoryInMemory implements BasketRepository{
  private static final Logger logger = Logger.getLogger(BasketRepositoryInMemory.class.getSimpleName());
  private final ConcurrentHashMap<Long, Basket> baskets = new ConcurrentHashMap<>();
  // private final Sclad;

  @Override
  public void changeBasketContent(long id, Product product, float change) {
    synchronized (Long.valueOf(id)) {
      logger.info("Basket with id " + id + " was changed: product->" + product.displayName + " change->" + change + " " + product.unitName);
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
    synchronized (Long.valueOf(id)) {
      logger.info("Basket with id " + id + " was ordered");
      try {
        // baskets.get(id).getProducts();
        // request to sklad
        baskets.get(id).clearBasket();
      } catch (RuntimeException e) {
        logger.info("Basket with id " + id + " was ordered but failed!");
      }
    }
  }

  @Override
  public void removeBasket(long id) {
    synchronized (Long.valueOf(id)) {
      logger.info("Basket with id " + id + " was removed");
      baskets.remove(id);
    }
  }

  @Override
  public Map<Long, Basket> getContent() {
    return baskets.entrySet().stream()
        .map(entry -> Map.entry(entry.getKey(), entry.getValue().getCopy()))
        .collect(Collectors.toMap(val -> val.getKey(), val -> val.getValue()));
  }
}

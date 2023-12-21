package HW.models.storage;

import HW.models.enums.Product;

import java.util.HashMap;
import java.util.Map;

public class Storage{
  private Map<Product, Float> content;

  public Storage() {
    this.content = new HashMap<Product, Float>();
  }

  public Storage(Map<Product, Float> content) {
    this.content = content;
  }

  public synchronized void takeAway(Map<Product, Float> basketContent)
      throws UnavailablePurchaseException {
    for (Product product : basketContent.keySet()) {
      if (this.content.containsKey(product)) {
        if (this.content.get(product) < basketContent.get(product)) {
          throw new UnavailablePurchaseException("Shortage of product!",
              product + " не в наличии или его недостаточно для осуществления заказа");
        }
      } else {
        throw new UnavailablePurchaseException("Unavailable product!",
            product + " не хранится на складе");
      }
    }

    for (Product product : basketContent.keySet()) {
      this.content.put(product, this.content.get(product) - basketContent.get(product));
    }
  }
}

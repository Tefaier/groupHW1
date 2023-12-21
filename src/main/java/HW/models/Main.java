package HW.models;

import HW.models.basket.BasketRepositoryInMemory;
import HW.models.enums.Product;
import HW.models.parser.CommandParserTXT;
import HW.models.storage.Storage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Main {
  private static final Logger logger = Logger.getLogger(Main.class.getSimpleName());

  public static void main(String[] args) {
    Map<Product, Float> storageContent = new HashMap<Product, Float>();
    storageContent.put(Product.Bread, 100f);
    storageContent.put(Product.Flour, 100f);
    storageContent.put(Product.Water, 100f);
    Storage storage = new Storage(storageContent);
    BasketRepository basketRepository = new BasketRepositoryInMemory(storage);
    String path = "commands/list1.txt";
    try {
      CommandParserTXT parser = new CommandParserTXT(path, basketRepository);
      parser.applyAllCommands();
    } catch (IOException e) {
      logger.warning("Failed while executing commands from txt file: " + e.getMessage());
    }
  }
}

package HW.models.parser;

import HW.models.DefaultIOOperations;
import HW.models.basket.BasketRepository;
import HW.models.enums.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class CommandParserTXT {
  private static final Logger logger = Logger.getLogger(CommandParserTXT.class.getSimpleName());
  private final String sourcePath;
  private final BasketRepository basketRepository;
  private final BufferedReader reader;

  public CommandParserTXT(String sourcePath, BasketRepository basketRepository) throws IOException {
    this.basketRepository = basketRepository;
    if (!sourcePath.contains(".txt")) {
      throw new IllegalArgumentException("File for CommandParserTXT has to be of type txt");
    }
    this.sourcePath = sourcePath;
    this.reader = new BufferedReader(new FileReader(DefaultIOOperations.getPath(sourcePath)));
  }

  public boolean applyNextCommand () throws IOException {
    String newLine = reader.readLine();
    if (newLine == null) {
      return false;
    }
    applyCommand(newLine);
    return true;
  }

  public void applyAllCommands () throws IOException {
    reader.lines().forEachOrdered(this::applyCommand);
    reader.close();
  }

  private void applyCommand (String command) {
    String[] parts = command.split(" ");
    if (parts[0].equalsIgnoreCase("get")) {
      try {
        long id = Long.parseLong(parts[1]);
        Product product = Product.valueOf(parts[2]);
        float change = Float.parseFloat(parts[3]);
        basketRepository.changeBasketContent(id, product, change);
      } catch (IllegalArgumentException e) {
        logger.info("Unknown product: " + parts[2]);
      }
    } else if (parts[0].equalsIgnoreCase("order")) {
      long id = Long.parseLong(parts[1]);
      basketRepository.tryOrderBasket(id);
    } else if (parts[0].equalsIgnoreCase("remove")) {
      long id = Long.parseLong(parts[1]);
      basketRepository.removeBasket(id);
    } else {
      logger.info("Unknown command: " + parts[0]);
    }
  }
}

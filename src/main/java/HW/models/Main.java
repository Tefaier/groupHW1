package HW.models;

import HW.models.basket.BasketRepository;
import HW.models.basket.BasketRepositoryInMemory;
import HW.models.parser.CommandParserTXT;

import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    BasketRepository basketRepository = new BasketRepositoryInMemory();
    String path = "commands/list1.txt";
    try {
      CommandParserTXT parser = new CommandParserTXT(path, basketRepository);
      parser.applyAllCommands();
    } catch (IOException e) {
      System.out.println("Failed while executing commands from txt file: " + e.getMessage());
    }
  }
}

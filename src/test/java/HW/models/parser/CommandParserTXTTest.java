package HW.models.parser;

import HW.models.basket.Basket;
import HW.models.basket.BasketRepository;
import HW.models.basket.BasketRepositoryInMemory;
import HW.models.enums.Product;
import HW.models.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTXTTest {
  Map<Product, Float> storageContent;
  Storage storage;
  BasketRepository basketRepository;

  @BeforeEach
  public void setUp() {
    this.storageContent = new HashMap<>();
    this.storageContent.put(Product.Bread, 10f);
    this.storageContent.put(Product.Water, 5f);
    this.storage = new Storage(storageContent);
    this.basketRepository = new BasketRepositoryInMemory(this.storage);
  }

  @Test
  public void testIllegalOrders() {
    //  На складе недостаточно воды и нет муки. Корзина 3 не была создана.
    String path = "commands/testIllegal.txt";
    try {
      CommandParserTXT parser = new CommandParserTXT(path, this.basketRepository);
      parser.applyAllCommands();
      Map<Product, Float> storageContent = this.storage.getContent();
      Map<Long, Basket> baskets = this.basketRepository.getContent();
      //  Тразакции не совершены. Корзины сохранились в памяти неизменными в изначальном количестве.
      assertEquals(10f, storageContent.get(Product.Bread));
      assertEquals(5f, storageContent.get(Product.Water));
      assertEquals(2, baskets.size());
      assertEquals(5, baskets.get(1l).getProducts().get(Product.Bread));
      assertEquals(8, baskets.get(1l).getProducts().get(Product.Water));
      assertEquals(4, baskets.get(2l).getProducts().get(Product.Flour));
    } catch (IOException e) {}
  }

  @Test
  public void testOverall() {
    String path = "commands/testOverall.txt";
    // Заказ второй корзины уменьшает количество воды ниже требований первой. Заказ третьей прошёл
    try {
      CommandParserTXT parser = new CommandParserTXT(path, this.basketRepository);
      parser.applyAllCommands();
      Map<Product, Float> storageContent = this.storage.getContent();
      Map<Long, Basket> baskets = this.basketRepository.getContent();
      // Корзины 2 и 3 заказаны и опустошены. Корзина один не была заказана и сохранилась в памяти.
      assertEquals(6f, storageContent.get(Product.Bread));
      assertEquals(1f, storageContent.get(Product.Water));
      assertEquals(3, baskets.size());
      assertEquals(0, baskets.get(2l).getProducts().size());
      assertEquals(0, baskets.get(3l).getProducts().size());
      assertEquals(2f, baskets.get(1l).getProducts().get(Product.Water));
      assertEquals(8f, baskets.get(1l).getProducts().get(Product.Bread));
    } catch (IOException e) {}
  }
}
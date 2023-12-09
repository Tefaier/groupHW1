package HW.models.enums;

public enum Product {
  Water("Вода", "л"),
  Bread("Хлеб", "шт"),
  Flour("Мука", "кг");

  public final String displayName;
  public final String unitName;

  Product(String displayName, String unitName) {
    this.displayName = displayName;
    this.unitName = unitName;
  }
}

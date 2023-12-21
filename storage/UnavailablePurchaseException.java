package HW.models.storage;

public class UnavailablePurchaseException extends RuntimeException{
  public final String reasonDescription;

  public UnavailablePurchaseException(String message, String reasonDescription) {
    super(message);
    this.reasonDescription = reasonDescription;
  }
}

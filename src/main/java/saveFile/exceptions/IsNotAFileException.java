package saveFile.exceptions;

public class IsNotAFileException extends Exception {

  public IsNotAFileException(String m) {
    super("IsNotAFileException: " + m);
  }
}

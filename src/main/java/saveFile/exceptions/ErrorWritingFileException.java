package saveFile.exceptions;

public class ErrorWritingFileException extends Exception {

  public ErrorWritingFileException(String m) {
    super("ErrorWritingFileException: " + m);
  }
}

package loadFile.exceptions;

public class ErrorReadingFileException extends Exception {

    public ErrorReadingFileException(String m) {
        super("ErrorReadingFileException: " + m);
    }
}

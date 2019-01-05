package loadFile.exceptions;

public class CanNotReadException extends Exception {

    public CanNotReadException(String m) {
        super("CanNotReadException: " + m);
    }
}

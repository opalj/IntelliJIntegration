package loadFile.exceptions;

public class FileDoesNotExistException extends Exception {

    public FileDoesNotExistException(String m) {
        super("FileDoesNotExistException: " + m);
    }
}

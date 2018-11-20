package saveFile.exceptions;

public class NotEnoughRightsException extends Exception {

    public NotEnoughRightsException(String m) {
        super("NotEnoughRightsException: " + m);
    }

}

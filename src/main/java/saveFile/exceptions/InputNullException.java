package saveFile.exceptions;

public class InputNullException extends Exception {

    public InputNullException(String m) {
        super("InputNullException: "+m);
    }

}
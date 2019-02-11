package runCommand;

public class ErrorRunningCommandException extends Exception {

    public ErrorRunningCommandException(String m) {
        super("ErrorRunningCommandException: "+m);
    }

}

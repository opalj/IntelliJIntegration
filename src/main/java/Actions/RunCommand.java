package Actions;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import java.io.IOException;
import java.util.EventListener;
import java.util.concurrent.TimeUnit;

/**
 *  RunCommand exist for commandline injections
 */
public class RunCommand {

  /**
   * run the java compiler(version 1.8) per commandline for given path of a java file
   * @param path the path
   * @param jar  the jarpaths
   * @return boolean value if the command hat executed<
   */
  public static final boolean runJavaC(String path, String jar) {
    String command = ("javac --release 8 -cp \"" + jar + "/*\" \"" + path + "\"");
    Process process = null;
    Notifications.Bus.notify(new Notification("OpalPlugin", "OpalPlugin", "creating a new class file", NotificationType.INFORMATION));
    try {
      process = Runtime.getRuntime().exec(command);
    } catch (IOException e0) {
      return false;
    }
    while(process.isAlive()){
      try {
        TimeUnit.MICROSECONDS.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return true;
  }
}

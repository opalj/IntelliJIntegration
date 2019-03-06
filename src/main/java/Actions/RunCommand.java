package Actions;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import java.io.IOException;
import java.util.EventListener;
import java.util.concurrent.TimeUnit;

public class RunCommand {

  public static final boolean runJavaC(String path, String jar) {
    String command = ("cmd /c javac --release 8 -cp \"" + jar + "/*\" \"" + path + "\"");
    Process process = null;
    try {
      process = Runtime.getRuntime().exec(command);
    } catch (IOException e0) {
      return false;
    }
    if(process.isAlive() ){
      Notifications.Bus.notify(new Notification("OpalPlugin", "Working", "creating a new class file", NotificationType.INFORMATION));
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

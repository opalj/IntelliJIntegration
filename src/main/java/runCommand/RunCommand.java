package runCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunCommand {

  private RunCommand() {}

  public static final String runJavap(String path) throws ErrorRunningCommandException {
    Runtime rt = Runtime.getRuntime();
    String command = ("javap -p -constants -c " + path);
    Process process = null;
    try {
      process = rt.exec(command);
    } catch (IOException e0) {
      throw new ErrorRunningCommandException("IOException: " + e0.getMessage());
    }

    BufferedReader input = null;
    input = new BufferedReader(new InputStreamReader(process.getInputStream()));

    String tempString = null;
    String result = null;
    while (true) {
      try {
        tempString = input.readLine();
      } catch (IOException e0) {
        throw new ErrorRunningCommandException("IOException: " + e0.getMessage());
      }
      if (tempString == null) {
        break;
      }

      if (result == null) {
        result = ("" + tempString);
      } else {
        result = (result + "\n" + tempString);
      }
    }

    return result;
  }
}

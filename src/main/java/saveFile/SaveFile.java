package saveFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import saveFile.exceptions.ErrorWritingFileException;
import saveFile.exceptions.InputNullException;
import saveFile.exceptions.IsNotAFileException;
import saveFile.exceptions.NotEnoughRightsException;

public class SaveFile {

  private SaveFile() {}

  public static final void saveFile(String data, String path)
      throws InputNullException, NotEnoughRightsException, IsNotAFileException,
          ErrorWritingFileException {
    if (path == null) {
      throw new InputNullException("The given parameter \"path\" is null.");
    }

    if (data == null) {
      throw new InputNullException("The given parameter \"data\" is null.");
    }

    File file = new File(path);

    if (file.exists() && (!file.canRead() || !file.canWrite())) {
      throw new NotEnoughRightsException("Can not read and write the given file \"" + path + "\".");
    }

    if (file.exists() && !file.isFile()) {
      throw new IsNotAFileException("\"" + path + "\" is not a file.");
    }

    BufferedWriter out = null;

    try {
      out = new BufferedWriter(new FileWriter(path));

      out.write(data);
      out.flush();
    } catch (IOException e0) {
      throw new ErrorWritingFileException("IOException: " + e0.getMessage());
    }
    try {
      out.close();
    } catch (IOException e0) {
    }
  }
}

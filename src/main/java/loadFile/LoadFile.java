package loadFile;

import loadFile.exceptions.*;

import java.io.*;
import java.util.ArrayList;

public class LoadFile {

    private static final int BUFFER_SIZE = 524288;

    private LoadFile() {}

    public static final byte [] loadFile(String path) throws FileDoesNotExistException,InputNullException,CanNotReadException,
            IsNotAFileException,ErrorReadingFileException {
        if( path == null ) {
            throw new InputNullException("The given parameter \"path\" is null.");
        }

        File file = new File(path);
        if( !file.exists() ) {
            throw new FileDoesNotExistException("The given file (\""+path+"\") does not exist.");
        }

        if( !file.canRead() ) {
            throw new CanNotReadException("The given file (\""+path+"\") can not be read.");
        }

        if( !file.isFile() ) {
            throw new IsNotAFileException("\""+path+"\" is not a file.");
        }

        ArrayList<byte []> bytes = new ArrayList<byte []>();
        try {
            DataInputStream dsi = new DataInputStream(new FileInputStream(file));

            while( true ) {
                byte [] buffer = new byte[BUFFER_SIZE];
                int amount = dsi.read(buffer);

                if( amount < BUFFER_SIZE ) {
                    if( amount > 0 ) {
                        byte [] tempBytes = new byte[amount];
                        for(int i = 0;i < amount;i++) {
                            tempBytes[i] = buffer[i];
                        }
                        bytes.add(tempBytes);
                    }
                    break;
                } else {
                    bytes.add(buffer);
                }
            }

            dsi.close();
        } catch (FileNotFoundException e0) {
            throw new ErrorReadingFileException("FileNotFoundException: "+e0.getMessage());
        } catch (IOException e1) {
            throw new ErrorReadingFileException("IOException: "+e1.getMessage());
        }

        int totalAmount = ((BUFFER_SIZE * (bytes.size() - 1)) + bytes.get(bytes.size() - 1).length);
        byte [] entireData = new byte[totalAmount];
        int counter = 0;

        for(int i = 0;i < bytes.size();i++) {
            for(int k = 0;k < bytes.get(i).length;k++) {
                entireData[counter] = bytes.get(i)[k];
                counter = (counter + 1);
            }
        }
        return entireData;
    }

}

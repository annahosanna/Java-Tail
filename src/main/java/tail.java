package example;
 
import java.io.*;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.commons.lang3.StringUtils;

class ReadFile {
//     inPipe = new DataInputStream(new PipedInputStream(pipedOutputStream));
//    outPipe = new DataOutputStream(new PipedOutputStream(pipedInputStream));

  public static void main (String args []) {
    // Send log messages to stout
    BasicConfigurator.configure();
    String filename = args[0];
    File file = new File(filename);
    long filePointer = 0;
    boolean keepChecking = true;
    while (keepChecking) {
      long fileLength = file.length();
      if (fileLength < filePointer) {
        keepChecking = false;
        logger.error("The file might have been renamed or truncated!");
      } else if (fileLength > filePointer) {
        RandomAccessFile raf = null;
        FileInputStream fis = null;
        try {
          raf = new RandomAccessFile(file.getCanonicalPath(), "r");
          raf.seek(filePointer);
          try {
            fis = new FileInputStream(raf.getFD());
            BufferedReader in = null;
            try {
              in = new BufferedReader(new InputStreamReader(fis));

              // Do something usefull

              filePointer = raf.getFilePointer();
            } catch (Exception g) {
              logger.error("Could not create BufferedReader");
              g.printStackTrace();
            } finally {
              if (in != null) {
                in.close();
                in = null;
              }
            }
          } catch (Exception f) {
            logger.error("Could not create FileInputStream (and therefore also BufferedReader)");
            f.printStackTrace();
          } finally {
            if (fis != null) {
              fis.close();
            }
          }
        } catch (Exception h) {
          logger.error("Could not create RandomAccessFile");
        } finally {
          if (raf != null) {
            raf.close();
          }
        }
      }
    }
  }
}

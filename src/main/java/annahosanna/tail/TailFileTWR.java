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
        try (RandomAccessFile raf = new RandomAccessFile(file.getCanonicalPath(), "r");) {
          raf.seek(filePointer);
          try (FileInputStream fis = new FileInputStream(raf.getFD());) {
            try (
              InputStreamReader isrf = new InputStreamReader(fis);
              BufferedReader in = new BufferedReader(isrf);) {              

              // Do something usefull

              filePointer = raf.getFilePointer();
            } catch (Exception g) {
              logger.error("Could not create BufferedReader");
              g.printStackTrace();
            }
          } catch (Exception f) {
            logger.error("Could not create FileInputStream (and therefore also BufferedReader)");
            f.printStackTrace();
          } 
        } catch (Exception h) {
          logger.error("Could not create RandomAccessFile");
        } 
      }
    }
  }
}

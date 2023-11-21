
package examples.io;

import java.io.*;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.commons.lang3.StringUtils;

class ReadStdin {
  public static void main (String args []) {
    // Send log messages to stout
    BasicConfigurator.configure();
    try {
      // The file name to monitor is missing so use stdin
      InputStream is = System.in;
      BufferedReader br = null;
      try {
        br = new BufferedReader(new InputStreamReader(is));
        String line = new String();
        while ((br != null) && (line = br.readLine()) != null) {
          // Do something with line
        }
      } catch (Exception e) {
        try {
          if (br != null) {
            br.close();
          }
        } catch (Exception f) {
          logger.error("Could not close temporary BufferedReader for StdIn");
        }
        logger.error("Could not create a BufferedReader for StdIn");
        e.printStackTrace();
      }

    } catch(Exception g) {
      logger.info("There was a problem trying to start the program: " + e.getMessage());
    }
  }
}

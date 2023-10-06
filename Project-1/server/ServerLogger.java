package server;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerLogger {

  private static Logger logger = Logger.getLogger(ServerLogger.class.getName());

  public static void main(String[] args) {

    // Create a console handler
    ConsoleHandler consoleHandler = new ConsoleHandler();
    logger.addHandler(consoleHandler);

    // Create a file handler
    try {
      FileHandler fileHandler = new FileHandler("mylog.log");
      logger.addHandler(fileHandler);
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Set the logging levels
    consoleHandler.setLevel(Level.ALL);
    logger.setLevel(Level.ALL);

    // Log some messages
//    logger.finest("This is a finest message");
//    logger.finer("This is a finer message");
//    logger.fine("This is a fine message");
//    logger.info("This is an info message");
//    logger.warning("This is a warning message");
//    logger.severe("This is a severe message");
  }

  public void Info(String msg) {
    logger.info(msg);
  }

  public void Warning(String msg) {
    logger.warning(msg);
  }

  public void Severe(String msg) {
    logger.severe(msg);
  }
}


package client;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;

public class ClientLogger {
  private static Logger logger = Logger.getLogger(ClientLogger.class.getName());

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
  }

  public static void Info(String msg) {
    logger.info(msg);
  }

  public static void Warning(String msg) {
    logger.warning(msg);
  }

  public static void Severe(String msg) {
    logger.severe(msg);
  }
}


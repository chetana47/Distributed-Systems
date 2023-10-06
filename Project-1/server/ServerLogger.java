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


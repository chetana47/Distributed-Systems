package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerApp {
  // Logger for server application logging.
  private static ServerInterface server = null;
  private static ServerLogger serverLogger = new ServerLogger();
  // A map to store key-value pairs.
  private static Map<String, Integer> keyValueStore = new HashMap<String, Integer>();
  // A reference to the network gateway interface for communication.

  ServerApp() {
    keyValueStore.put("x", 1);
    keyValueStore.put("y", 2);
    keyValueStore.put("z", 3);
  }

  private static void processCommand(String operation, String key, String value, String packetId) throws IOException {
    try {
      switch (operation) {
        case "PUT":
          PutKey(key, Integer.parseInt(value), packetId);
          break;
        case "GET":
          GetKey(key, packetId);
          break;
        case "DELETE":
          DeleteKey(key, packetId);
          break;
        default:
          String msg = "Invalid operation provided by user. #" + packetId;
          System.out.println(msg);
          serverLogger.Severe(msg);
          server.send(msg);
      }
    } catch (Exception e) {
      String msg = operation + " Operation terminated with Exception, packet_id: " + packetId;
      serverLogger.Severe(msg);
      server.send(msg);
    }
  }


  private static void PutKey(String key, int value, String packetId) throws IOException {
    serverLogger.Info("Server: Inserting Key - " + key + " Value - " + value);
//    serverLogger.Info("Performing put operation InetAddress: " + inetAddress);
    keyValueStore.put(key, value);
//    logMessage("Put Key Value Successful" + " packet_id: " + packetId + " InetAddress: " + inetAddress);
    serverLogger.Info("Server: Insertion of Key Value Successful");
    server.send("Insertion of Key Value Success " + packetId);
  }

  private static void GetKey(String key, String packetId) throws IOException {
    serverLogger.Info("Server: Getting Key - " + key);
    int keyValue = keyValueStore.get(key);
    serverLogger.Info("Server: Get Key Value Successful" + "Key: " + key + " Value: " + keyValue);
    server.send(keyValue + " " + packetId);
  }

  private static void DeleteKey(String key, String packetId) throws IOException {
    if (keyValueStore.containsKey(key)) {
      serverLogger.Info("Server: Deleting Key - " + key);
      keyValueStore.remove(key);
      serverLogger.Info("Delete Key Successful");
      server.send("Deletion success " + packetId);
    } else {
      String msg = "Invalid Key provided by user." + "Key " + key + " #" + packetId;
      System.out.println(msg);
      serverLogger.Severe(msg);
      server.send(msg);
    }
  }

  /**
   * Main method to start the server and handle client requests.
   *
   * @param args Command line arguments. Expects the server port as an argument.
   * @throws IOException If there is an issue with network communication.
   */
  public static void main(String args[]) throws IOException,IllegalArgumentException {

    if (args.length == 2) {
      int port = Integer.valueOf(args[0]);
      String type = (args[1]);
//      String hostAddress = "";

      switch (type.toUpperCase()) {
        case "TCP":
          server = new TcpHandler();
          break;
        case "UDP":
          server = new UdpHandler();
          break;
        default:
          throw new IllegalArgumentException("Invalid server type: " + type);
      }
      server.startServer(port);

      while (true) {
          String msgFromClient = server.receive();

          if (msgFromClient != null) {
            String[] commands = msgFromClient.split(" ");
//            hostAddress = server.getClientIp();

            if (commands.length == 3) {
              processCommand(commands[0], commands[1], null, commands[2]);
            } else if (commands.length == 4) {
              processCommand(commands[0], commands[1], commands[2], commands[3]);
            }
           else {
              String msg = "Invalid operation provided by user. #" + msgFromClient.split("#")[1];
              System.out.println(msg);
              serverLogger.Severe(msg);
              server.send(msg);
            }
          }
      }
    } else {
      System.out.println("Please give Valid Input");
    }
  }
}

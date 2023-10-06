package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ServerApp {
  private static Map<String, Integer> keyValueStore = new HashMap<String, Integer>();
  private static ServerInterface server = null;
  private static ServerLogger serverLogger = new ServerLogger();

  /**
   * Processes a command received from the client and performs the corresponding operation.
   *
   * @param operation The operation to be performed (PUT, GET, DELETE).
   * @param key       The key associated with the operation.
   * @param value     The value associated with the operation (for PUT).
   * @param packetId  The identifier associated with the command.
   * @throws IOException if an I/O error occurs during the processing of the command.
   */
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

  /**
   * Inserts a key-value pair into the key-value store and sends a response to the client.
   *
   * @param key      The key to be inserted.
   * @param value    The value associated with the key.
   * @param packetId The identifier associated with the insertion request.
   * @throws IOException if an I/O error occurs while interacting with the key-value store or sending the response.
   */
  private static void PutKey(String key, int value, String packetId) throws IOException {
    serverLogger.Info("Server: Inserting Key - " + key + " Value - " + value);
    keyValueStore.put(key, value);
    serverLogger.Info("Server: Insertion of Key Value Successful");
    server.send("Insertion of Key Value Success " + packetId);
  }

  /**
   * Retrieves the value associated with a key from the key-value store
   * and sends the value as a response to the client.
   *
   * @param key      The key for which to retrieve the value.
   * @param packetId The identifier associated with the retrieval request.
   * @throws IOException if an I/O error occurs while interacting with the key-value store or sending the response.
   */
  private static void GetKey(String key, String packetId) throws IOException {
    serverLogger.Info("Server: Getting Key - " + key);
    int keyValue = keyValueStore.get(key);
    serverLogger.Info("Server: Get Key Value Successful" + "Key: " + key + " Value: " + keyValue);
    server.send(keyValue + " " + packetId);
  }

  /**
   * Deletes a key from the key-value store and sends a response to the client.
   *
   * @param key      The key to be deleted.
   * @param packetId The identifier associated with the deletion request.
   * @throws IOException if an I/O error occurs while interacting with the key-value store or sending the response.
   */
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


  public static void main(String args[]) throws IOException, IllegalArgumentException {

    if (args.length == 2) {
      int port = Integer.valueOf(args[0]);
      String type = (args[1]);
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
          if (commands.length == 3) {
            processCommand(commands[0], commands[1], null, commands[2]);
          } else if (commands.length == 4) {
            processCommand(commands[0], commands[1], commands[2], commands[3]);
          } else {
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

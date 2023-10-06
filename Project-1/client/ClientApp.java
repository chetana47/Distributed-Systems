package client;

import java.io.IOException;
import java.util.Scanner;

public class  ClientApp {
  private static ClientLogger clientLogger = new ClientLogger();
  private static ClientInterface client = null;
  /**
   * Receives and logs a response from the server.
   *
   * @param currentTime The timestamp associated with the original message.
   * @param isInitial   Indicates whether this is the initial response after prepopulating values.
   * @throws IOException if an I/O error occurs while receiving or logging the response.
   */
  private static void receiveAndLogResponse(long currentTime, Boolean isInitial) throws IOException{
    long timeout = 6000;
    long startTime = System.currentTimeMillis();

    while (System.currentTimeMillis() - startTime <= timeout) {
      try {
        String reply = client.receiveMessage();
        if (reply != null) {
          String[] messages = reply.split("#");
          System.out.println(reply);
          if(isInitial){
            clientLogger.Info("Prepopulated Values");
            break;
          }
            if (messages.length == 2) {
              if (Long.valueOf(messages[1]).equals(currentTime)) {
                clientLogger.Info(reply);
              } else {
                clientLogger.Severe("Client: Unexpected datagram packet from server: " + reply);
              }
            }
            else{
              clientLogger.Severe("Client: Invalid Message from Server: " + reply);
            }
            break;
          }

      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
  /**
   * Prepopulates values by sending messages with timestamps and receiving and logging responses.
   *
   * @throws IOException          if an I/O error occurs while sending or receiving messages.
   * @throws InterruptedException if the thread is interrupted while sleeping.
   */
  private static void prepopulateValues() throws IOException, InterruptedException {
    long currentTime = 0;
    String[] values = {"a 1", "b 2", "c 3"};
    for (String value : values) {
      currentTime = System.currentTimeMillis();
      sendMessageWithTimestamp("PUT " + value,currentTime);
      receiveAndLogResponse(currentTime,true);
    }
  }
  /**
   * Sends a message with a timestamp to the server.
   *
   * @param message    The message to be sent.
   * @param currentTime The timestamp associated with the message.
   * @throws IOException if an I/O error occurs while sending the message.
   */
  private static void sendMessageWithTimestamp(String message,long currentTime) throws IOException {
    client.sendMessage(message + " #" + String.valueOf(currentTime));
  }
  public static void main(String args[]) throws IOException, InterruptedException {

    if (args.length == 3) {
      String ipaddress = (args[0]);
      int portno = Integer.valueOf(args[1]);
      String type = (args[2]);

      switch (type.toUpperCase()) {
        case "TCP":
          client = new TcpClientSocket();
          break;
        case "UDP":
          client = new UdpClientSocket();
          break;
        default:
          throw new IllegalArgumentException("Invalid Server Type: " + type);
      }
      client.connectToServer(ipaddress, portno);
      prepopulateValues();
      while (true) {
          long currentTime = System.currentTimeMillis();
          Scanner sc = new Scanner(System.in);
          System.out.println("Please Enter PUT/GET/DELETE Key Value");
          String inputCommand = sc.nextLine();
          if (inputCommand == null) {
            System.out.println("Please Enter the Input");
          }
          else {
            clientLogger.Info("Client: " + inputCommand + " " + String.valueOf(currentTime));
            sendMessageWithTimestamp(inputCommand,currentTime);
            receiveAndLogResponse(currentTime,false);
          }
      }
    } else {
      System.out.println("Not a valid Arg");
    }
  }




}

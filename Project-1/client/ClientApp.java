package client;

import java.io.IOException;
import java.util.Scanner;

import server.TcpHandler;
import server.UdpHandler;

public class  ClientApp {
  private static ClientLogger clientLogger = new ClientLogger();
  private static ClientInterface client = null;

  private static void receiveAndLogResponse(long currentTime) throws IOException{
    long timeout = 6000;
    long startTime = System.currentTimeMillis();

    while (System.currentTimeMillis() - startTime <= timeout) {
      try {
        String reply = client.receiveMessage();
        if (reply != null){
        String[] messages = reply.split("#");
        System.out.println(reply);
        if (messages.length == 2) {
          if (Long.valueOf(messages[1]).equals(currentTime)) {
            // Log a message when the operation is performed and received correctly.
            clientLogger.Info(reply);
          } else {
            // Log an error message when an unexpected datagram packet is received.
            clientLogger.Severe("Client: Unexpected datagram packet from server: " + reply);
          }
        } else {
          // Log an error message when an invalid response is received.
          clientLogger.Severe("Client: Invalid Message from Server: " + reply);
        }
          break;
        }

      } catch (IOException e) {
        // Handle IOException if necessary, e.g., log or rethrow
      }
    }
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
            client.sendMessage(inputCommand + " #" + String.valueOf(currentTime));

            receiveAndLogResponse(currentTime);
          }
      }
    } else {
      System.out.println("Not a valid Arg");
    }
  }
}

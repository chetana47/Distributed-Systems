package client;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public interface ClientInterface {


  void sendMessage(String message) throws IOException;

  String receiveMessage() throws IOException;
  void connectToServer(String hostname, int port) throws SocketException, UnknownHostException;

  void closeConnection();
}

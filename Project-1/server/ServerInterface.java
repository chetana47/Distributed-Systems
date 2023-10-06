package server;

import java.io.IOException;

public interface ServerInterface {
  void closeConnection() throws IOException;

  String startServer(int port) throws IOException;

  void send(String message) throws IOException;

  String getClientIp();

  String receive() throws IOException;
}


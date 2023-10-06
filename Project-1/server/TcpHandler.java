package server;

import java.io.IOException;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;


public class TcpHandler implements ServerInterface {
  private Socket tcpSocket = null;
  private ServerSocket serverSocket = null;
  private DataInputStream serverIn = null;
  private DataOutputStream serverOut = null;

  public TcpHandler() {
  }

  @Override
  public String startServer(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    tcpSocket = serverSocket.accept();
    return tcpSocket.getInetAddress().toString();
  }

  @Override
  public void closeConnection() throws IOException {
    serverIn.close();
    serverOut.close();
    tcpSocket.close();
  }

  @Override
  public void send(String message) throws IOException {
    serverOut = new DataOutputStream(tcpSocket.getOutputStream());
    serverOut.writeUTF(message);
  }

  @Override
  public String receive() throws IOException {
    serverIn = new DataInputStream(tcpSocket.getInputStream());
    String line = serverIn.readUTF();
    return line;
  }

  @Override
  public String getClientIp() {
    return tcpSocket.getInetAddress().toString();
  }
}


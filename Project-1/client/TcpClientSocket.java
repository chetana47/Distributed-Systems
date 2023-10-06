package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class TcpClientSocket implements ClientInterface{
    private Socket socket = null;
    private DataInputStream clientIn = null;
    private DataOutputStream clientOut = null;

    public TcpClientSocket() {}

  @Override
  public void sendMessage(String message) throws IOException {
    clientOut = new DataOutputStream(socket.getOutputStream());
    clientOut.writeUTF(message);
  }

  @Override
  public String receiveMessage() throws IOException {
    clientIn = new DataInputStream(socket.getInputStream());
    return clientIn.readUTF();
  }

    @Override
    public void connectToServer(String ipaddress, int port){
      try {
        socket = new Socket(ipaddress, port);
      } catch (IOException e) {
        System.out.println(e);
      }
    }

    @Override
    public void closeConnection() {
      try {
        socket.close();
        clientIn.close();
        clientOut.close();
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }

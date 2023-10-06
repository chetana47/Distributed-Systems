package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpHandler implements ServerInterface {
  private DatagramSocket udpSocket;
  private int clientPort;
  private byte[] dataGramPacket = new byte[512];
  private InetAddress address;

  public UdpHandler() throws SocketException {
  }

  public static void main(String args[]) throws IOException {
  }

  @Override
  public String startServer(int port) throws IOException {
    udpSocket = new DatagramSocket(port);
    return "";
  }

  @Override
  public void send(String message) throws IOException {
    byte[] newSendingMessageBytes = new byte[512];
    newSendingMessageBytes = message.getBytes();
    DatagramPacket packet = new DatagramPacket(newSendingMessageBytes, newSendingMessageBytes.length,
            address, clientPort);
    udpSocket.send(packet);
  }

  @Override
  public String receive() throws IOException {
    DatagramPacket packet
            = new DatagramPacket(dataGramPacket, dataGramPacket.length);
    udpSocket.receive(packet);
    address = packet.getAddress();
    clientPort = packet.getPort();
    return new String(packet.getData(), 0, packet.getLength());
  }

  @Override
  public void closeConnection() throws IOException {
    udpSocket.close();
  }

  @Override
  public String getClientIp() {
    String clientIPaddr = address.toString();
    return clientIPaddr;
  }
}


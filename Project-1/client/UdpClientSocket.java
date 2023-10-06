package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class UdpClientSocket implements ClientInterface {
  private DatagramSocket udpSocket;
  private DatagramPacket dtgPacket;
  private InetAddress address;
  private String ipaddress;
  private int portno;
  private byte[] dataGramPacket;


  /**
   * Receives a message from the connected server using UDP.
   * Creates a DatagramPacket to receive data, waits for incoming data on the UDP socket,
   * and extracts the received message from the packet's byte array.
   *
   * @return The received message from the server.
   * @throws IOException if an I/O error occurs while receiving the message.
   */
  @Override
  public String receiveMessage() throws IOException {
    // Prepare a byte array to store the received data
    dataGramPacket = new byte[512];

    // Create a DatagramPacket to receive data
    dtgPacket = new DatagramPacket(dataGramPacket, dataGramPacket.length);

    // Receive data from the server
    udpSocket.receive(dtgPacket);

    // Convert the received data into a String and return it
    return new String(dtgPacket.getData(), 0, dtgPacket.getLength());
  }


  /**
   * Sends a message to the connected server using UDP.
   * Converts the provided message into a byte array, creates a DatagramPacket,
   * and sends it to the server's IP address and port number.
   *
   * @param message The message to be sent to the server.
   * @throws IOException if an I/O error occurs while sending the message.
   */
  @Override
  public void sendMessage(String message) throws IOException {
    // Convert the message into a byte array
    dataGramPacket = message.getBytes();
    // Create a DatagramPacket with the message data, server IP, and port
    dtgPacket = new DatagramPacket(dataGramPacket, dataGramPacket.length,
            InetAddress.getByName(this.ipaddress), this.portno);
    // Send the DatagramPacket to the server
    udpSocket.send(dtgPacket);
  }


  /**
   * Establishes a connection to the server using UDP.
   * Initializes a new DatagramSocket for communication with the server
   * and sets the destination IP address and port number.
   *
   * @param ipaddress The IP address of the server.
   * @param portno The port number on which the server is listening.
   * @throws SocketException if an error occurs while creating the DatagramSocket.
   * @throws UnknownHostException if the provided server IP address is invalid.
   */
  @Override
  public void connectToServer(String ipaddress, int portno) throws SocketException, UnknownHostException {
    // Create a new DatagramSocket for UDP communication
    udpSocket = new DatagramSocket();
    // Set the port number and IP address for the server connection
    this.portno = portno;
    this.ipaddress = ipaddress;
    address = InetAddress.getByName(ipaddress);
  }


  /**
   * Closes the connection to the server by closing the UDP socket.
   * After calling this method, the client will no longer be able to send
   * or receive messages through the UDP connection.
   * It is recommended to call this method when the client is done using
   * the UDP connection to release associated resources.
   */
  @Override
  public void closeConnection() {
    udpSocket.close();
  }

}

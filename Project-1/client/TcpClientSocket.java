package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TcpClientSocket implements ClientInterface{
    private Socket socket = null;
    private DataInputStream clientIn = null;
    private DataOutputStream clientOut = null;


  /**
   * Sends a message to the server through the output stream.
   * Initializes a DataOutputStream with the underlying socket's output stream
   * and writes the specified message as a UTF-8 encoded string.
   *
   * @param message The message to be sent to the server.
   * @throws IOException if an I/O error occurs while writing the message.
   */
  @Override
  public void sendMessage(String message) throws IOException {
    clientOut = new DataOutputStream(socket.getOutputStream());
    clientOut.writeUTF(message);
  }


  /**
   * Receives a message from the server through the input stream.
   * Initializes a DataInputStream with the underlying socket's input stream
   * and reads a UTF-8 encoded string from it.
   *
   * @return The received message from the server.
   * @throws IOException if an I/O error occurs while reading the message.
   */
  @Override
  public String receiveMessage() throws IOException {
    clientIn = new DataInputStream(socket.getInputStream());
    // Read a UTF-8 encoded string from the input stream and return it
    return clientIn.readUTF();
  }

  /**
   * Establishes a connection to the server with the specified IP address and port.
   * Creates a new socket to connect to the server.
   *
   * @param ipaddress The IP address of the server.
   * @param port The port number on which the server is listening.
   * @throws IOException if an I/O error occurs while creating the socket or connecting to the server.
   */
  @Override
  public void connectToServer(String ipaddress, int port) {
    try {
      // Create a new socket and connect to the server
      socket = new Socket(ipaddress, port);
    } catch (IOException e) {
      System.out.println("An error occurred while connecting to the server: " + e.getMessage());
    }
  }


  /**
   * Closes the connection to the server by closing the underlying socket,
   * input stream, and output stream.
   * It is recommended to call this method when the client is done using
   * the connection to release associated resources.
   */
  @Override
  public void closeConnection() {
    try {
      // Close the socket
      socket.close();
      clientIn.close();
      clientOut.close();
    } catch (IOException e) {
      System.out.println("An error occurred while closing the connection: " + e.getMessage());
    }
  }

}

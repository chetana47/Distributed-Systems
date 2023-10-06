## Project 1

### Constraints on Store Key and Values 
- Ensure that the key provided for the store is a string and contains no spaces.
- The value entered for the store should be an integer.

### Client-Server Interaction Time to Live (TTL)  
- The client, expecting a response from the server, has a Time to Live (TTL) of 6 seconds.

### Handling Unique Message ID 
- The client sends a unique message ID appended to the request message to manage unrequested packets.
- The server responds to the request with the unique client ID sent by the client, followed by `#`.

### Communication Protocols - TCP/UDP
- Communication between the client and server should occur through the same protocol, either TCP or UDP.

### User Input Commands Format
- We pass the protocol type as the parameter (TCP/UDP) while running the respective side (Client/Server)
- All user input commands should be in uppercase (e.g., PUT/DELETE/GET).
- The server will respond with "Invalid command" if the user enters a command otherwise.
### Starting the Server Application
- To start the server application, provide the command line argument `<port_number> <protocol-type>`.

### Starting the Client Application
- To start the client application, provide the command line arguments `<host_name> <port_number> <protocol-type>`.

### Store Prepopulation from Client
- The key store is prepopulated with the following values from the client side:
   ```plaintext
    a 1
    b 2
    c 3

### Usage Steps
1. After each packet is sent or received, logs will be displayed
2. Enter user commands in the client application terminal. Example commands:
   ```plaintext
   GET a
   PUT k 22
   PUT l 23
   GET l
   GET k
   DELETE k
   DELETE l

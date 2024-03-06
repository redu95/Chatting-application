# Chatting Application

The Chatting Application is a simple chat program built using Java Socket Programming. It allows multiple users to connect to a server and communicate with each other in real-time through text-based messages.

## Features

The Chatting Application includes the following features:

- Server-Client Architecture:
  - The application follows a client-server architecture where multiple clients connect to a central server.
  - The server is responsible for accepting client connections and relaying messages between clients.

- Real-time Chatting:
  - Connected clients can send and receive text messages in real-time.
  - Messages typed by one client are instantly delivered to all other connected clients.

- Multiple Client Support:
  - The server can handle multiple client connections simultaneously.
  - Each client can communicate with other clients through the server.

## Prerequisites

Before running the Chatting Application, ensure that you have the following installed:

- Java Development Kit (JDK)

## Getting Started

To get started with the Chatting Application, follow these steps:

1. Clone the repository or download the source code files.
2. Compile the Java files to generate the class files.
3. Start the server by running the `Server.java` file.
4. Start multiple client instances by running the `Client.java` file on different machines or on different terminals on the same machine.
5. Enter the IP address or hostname of the server when prompted by the client.
6. Begin chatting with other connected clients.

## Usage

1. Start the server before starting any client instances. The server will listen for incoming client connections.
2. Launch multiple client instances and provide the server's IP address or hostname when prompted.
3. Once connected, clients can send messages that will be broadcasted to all other connected clients.
4. Clients can receive messages from other connected clients in real-time.
5. To disconnect from the server, clients can simply close the application window.

## Contributing

Contributions to the project are welcome. If you find any issues or have suggestions for improvement, please create an issue or submit a pull request.

package Socket;

import sample.DbConnection.DbConnection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;


public class Server {
    private ServerSocket server = null;
    private Socket socket = null;
    static DbConnection connections = new DbConnection();
    static int time = 0;

    public static void main(String[] args) {
        String SQL = "SELECT * FROM item";
        try (Connection connect = connections.getConnection();
             PreparedStatement stmt = connect.prepareStatement(SQL)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                time = rs.getInt("length_of_time");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Counts down the time left for bidding on items
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                while (true) {
                    time--;
                }
            }
        }, 0, 1000);

        //Start the server
        new Server().start();


        /**
         * Listens for socket connections, then creates a thread to handle them
         */

    }

    private void start () {

        new Thread(() -> {
            try {  // Create a server socket
                @SuppressWarnings("resource")
                ServerSocket serverSocket = new ServerSocket(6000);

                while (true) {

                    //Listen for a new connection request
                    Socket socket = serverSocket.accept();

                    // Create and start a new thread for the connection
                    new Thread(new HandleAClient(socket)).start();

                    System.out.println("got a client connection");
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }


        // Define the thread class for handling
        static class HandleAClient implements Runnable {
            private Socket socket; // A connected socket
            public String username;
            public String password;

            /** Construct a thread */
            public HandleAClient(Socket socket) {
                this.socket = socket;
            }
            /** Run a thread */
            public void run() {
                try (
                    // Create data input and output streams
                    DataInputStream inputFromClient = new DataInputStream( socket.getInputStream());
                    DataOutputStream outputToClient = new DataOutputStream( socket.getOutputStream());){

                    String username;
                    String password;

                    username = inputFromClient.readUTF();
                    password = inputFromClient.readUTF();



                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
}



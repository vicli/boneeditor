package backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.text.DefaultStyledDocument;

/**
 * Server for the realtime collaborative editor
 * 
 * Thread-safe argument
 * --------------------
 * ~Something about queues and global Documents~
 *
 */
public class Server {
    private final ServerSocket serverSocket;
    private static Map<String, ServerDocument> docList  = new HashMap<String, ServerDocument>();

    /**
     * Makes Server that listens for connections on port.
     * @param port port number, requires 0 <= port <= 65535
     * @throws IOException 
     */
    public Server (int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        
    }

    /**
     * Run the server, listening for client connections and handling them.
     * Never returns 
     * @author User
     *
     */
    public void serve() throws IOException {
        while (true) {
            // block until a client connects
            final Socket socket = serverSocket.accept();
            // makes threads
            Thread thread = new Thread(new Runnable() {
                public void run() {
                        try {
                            handleConnection(socket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    
                }
            });
            thread.start();
        }
    }

    /**
     * Handles a single client connection. Returns when client disconnects.
     * @param socket socket where the client is connected
     * @throws IOException if connection has an error or terminates unexpectedly
     */
    private void handleConnection(Socket socket) throws IOException {
        // TODO: stuff that happens on start up of client connection
        // TODO: stuff that happens aftern start up of client
        String input = ""; //get this from the GUI somehow
        String output = handleRequest(input);
    }

    /**
     * Handler for client input
     * 
     * make requested mutations on game state if applicable, then return
     * appropriate message to user
     * @param input
     *
     */
    private static String handleRequest(String input) {
        // TODO: define regex of protocol from user to server
        String regex = "";
        if(!input.matches(regex)) {
            //invalid input
            return null;
        }
        String[] tokens = input.split(" ");
        // TODO: if/else statement dealing with inputs from user
        
        //pseudocode for if/else statement
        if (tokens[0].equals("NewDoc")) { 
            //if makenew
            String title = tokens[1];
            docList.put(title, new ServerDocument(title));
            return "current doc: " + title;
        } else if (tokens[0].equals("Open")) {
            String title = tokens[1];
            return "current doc: " + title;
        } else if (tokens[0].equals("Go") && tokens[1].equals("back")) {
            //if back
            return "Go back one screen";
        } else if (tokens[0].equals("Cursor")) {
            //if movecursor
            
            return "Cursor move recognized";
        } else if (tokens[0].equals("Edit")) {
            //if edit
            
        } else {
            return "Invalid input";
        }
        
        // Should never get here--make sure to return in each of the valid cases above.
        throw new UnsupportedOperationException();
    }

    /**
     * Start a Server running on the default port (4444).
     * 
     // TODO: state and explain creation of server here
     * 
     */
    public static void main(String[] args) {
        // We parse the command-line arguments for you. Do not change this method.
        
        // TODO: figure out how ports work for running things across multiple computers
        final int port = 4444;
        try {
            runMinesweeperServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Start a Server running on the specified port
     * @param port The network port on which the server should listen.
     */
    public static void runMinesweeperServer(int port) throws IOException
    {
        Server server = new Server(port);
        server.serve();
    }
    
    /**
     * Returns the titles of all the documents
     * @return The titles of the documents
     */
    public static ArrayList<String> getDocs() {           
            Set<String> keys = docList.keySet();
            ArrayList<String> titleList = new ArrayList<String>(keys);

//            
//            System.out.println("keys are" + keys);
//            int size = keys.size();
//            String[] toReturn = new String[keys.size()];
//            int i = 0;
//            for (String k : keys) {
//                toReturn[i] = k;
//            }
            return titleList;
    }
    
    public static boolean docListEmptyCheck(){
        if (docList == null){
            return true;
        }
        return false;
    }
    
    public static void addDocument(String title){
        docList.put(title, new ServerDocument(title));
        System.out.println("doclist after adding" + docList.toString());
    }
    /**
     * Returns the document with the given title or null if there is nothing with that title.
     * @param title The title
     */
    public static ServerDocument getDocument(String title) {
        return docList.get(title);
    }
}

package backend;

import gui.DocGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
    private int numUsers;
    private final EditController editCont;

    private static Map<String, ServerDocument> docList  = new HashMap<String, ServerDocument>();

    /**
     * Makes Server that listens for connections on port.
     * @param port port number, requires 0 <= port <= 65535
     * @throws IOException 
     */
    public Server (int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        numUsers = 0;
        editCont = new EditController();
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
            Thread clientThread = new Thread(new Runnable() {
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
                /**
                 * Handles a single client connection. Returns when client disconnects.
                 * @param socket socket where the client is connected
                 * @throws IOException if connection has an error or terminates unexpectedly
                 */
                private void handleConnection(Socket socket) throws IOException {
                    //handles closing client connection, and thats it. everything else seems
                    // to be handled in handleRequest, as everything is client input. 
                    
                    // TODO: finish this
                    
                    numUsers++;
                    // We create a new instance of the Document GUI for each client
                    DocGUI clientGUI = new DocGUI();
                    
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    try {
                        for (String line =in.readLine(); line!=null; line=in.readLine()) {
                            String output = handleRequest(line);
                            //remember to use debug here
                            if(output != null) {
                                out.print(output);
                                out.flush();
                                if (output.equals("Exit")) {
                                    numUsers--;
                                    return;
                                }
                            } 
                        }
                    } finally {   
                        out.close();
                        in.close();
                    }
                }

                /**
                 * Handler for client input
                 * 
                 * make requested mutations on game state if applicable, then return
                 * appropriate message to user
                 * @param input
                 *
                 */
                private String handleRequest(String input) {
                 // Create a caret and a key listener, and listen to client.
                    // sent client input as an EDIT message, to Edit queue
                    // edit queue will handle processing 
                    
                    // TODO: define regex of protocol from user to server
                    String regex = "";
                    if(!input.matches(regex)) {
                        //invalid input
                        return null;
                    }
                    String[] tokens = input.split(" ");
                    // TODO: if/else statement dealing with inputs from user
                    
                    //pseudocode for if/else statement
                    if (tokens[1].equals("NewDoc")) { 
                        String title = "";
                        for (int i = 2; i < tokens.length; i++) {
                            title += tokens[i];
                            title += " ";
                        }
                        title = title.substring(0, title.length() - 1);
                        if (docList.containsKey(title)) {
                            return "Invalid Doc Title";
                        } else {
                            docList.put(title, new ServerDocument(title));
                            return "Update doc";
                        }
                    } else if (tokens[2].equals("Insert")) {
                        return editCont.insert(input);
                    } else if (tokens[2].equals("Remove")) {
                        return editCont.remove(input);
                    } else if (tokens[2].equals("SpaceEntered")) {
                        return editCont.endEdit(input);
                    } else {
                        return "Invalid input";
                    }
                }

            });
            clientThread.start();
        }
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
            runServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Start a Server running on the specified port
     * @param port The network port on which the server should listen.
     */
    public static void runServer(int port) throws IOException
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

package backend;

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
import java.util.concurrent.BlockingQueue;

/**
 * Server for the realtime collaborative editor.
 * 
 * Thread-safe argument
 * --------------------
 * Edits are are sent over sockets to the server and are added to the
 * EditQueue so that they are dealt with one by one. The Edits are not
 * inserted using indexes; instead, they are inserted using the Cursor's
 * location for that client. This means that an edit will always be 
 * inserted correctly into the ServerDocument even if its index changes 
 * in between when it is sent to the server and when it is actually added
 * to the ServerDocument. Additionally, Edits belong to the original 
 * client until that client moves the Cursor or presses space or newline.
 * This helps narrow down the case where clients will try to edit right on
 * top of each other. They still type right next to each other,
 *
 */
public class Server {
    private ServerSocket serverSocket = null;
    private int numUsers;
    private final EditController editCont;
    private static Map<String, ServerDocument> docList  = new HashMap<String, ServerDocument>();

    /**
     * Makes Server that listens for connections on port.
     * @param port port number, requires 0 <= port <= 65535
     * @throws IOException 
     */
    public Server (int port) throws IOException {
        serverSocket = new ServerSocket(port);
        numUsers = 0;
        editCont = new EditController(new EditQueue(), docList);
    }

    /**
     * Run the server, listening for client connections and handling them.
     * Never returns 
     * @author User
     *
     */
    private Socket socket;
    public void serve() throws IOException {
        while (true) {
            System.out.println("youre before socket");
            // block until a client connects
            System.out.println(serverSocket.toString());
            socket = serverSocket.accept();
            System.out.println("youve accepted the socket");
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
                                serverSocket.close();
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
                    numUsers++;                   
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("Welcome! There are currently "+numUsers+" other clients connected.");
                    out.flush();
                    try {
                        for (String line =in.readLine(); line!=null; line=in.readLine()) {
                            out.println("success");
                            out.flush();
                            String output = handleRequest(line);
//                            if(output != null) {
//                                out.print(output);
//                                out.flush();
//                                if (output.equals("Exit")) {
//                                    numUsers--;
//                                    return;
//                                } 
//                            } 
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
                    String[] tokens = input.split(" ");
                    if (tokens.length > 1 && tokens[1].equals("NewDoc")) { 
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
                    } else {
                        if (editCont.putOnQueue(input)) 
                            return "success";
                        else 
                            return "invalid";
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
        // Always uses the same port. Clients connect their GUIs
        // to this port and the host's IP address.
        final int port = 4444;
        try {
            Server server = new Server(port);
            server.serve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the titles of all the documents
     * @return The titles of the documents
     */
    public static ArrayList<String> getDocs() { 
        ArrayList<String> titleList = new ArrayList<String>();
        Set<String> keys = docList.keySet();
        if (!keys.isEmpty()){
            titleList = new ArrayList<String>(keys);
        }
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

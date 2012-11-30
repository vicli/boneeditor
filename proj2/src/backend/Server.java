package backend;

/**
 * Server for the realtime collaborative editor
 * 
 * Thread-safe argument
 * --------------------
 * ~Something about queues and global Documents~
 *
 */
public class Server {
    private final ServerSocket serverSocket
    private ArrayList<ServerDocument> docList;

    /**
     * Makes Server that listens for connections on port.
     * @param port port number, requires 0 <= port <= 65535
     */
    public Server (int port) {
        serverSocket = new ServerSocket(port);
        docList = new ArrayList<ServerDocument>();
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
    }

    /**
     * handler for client input
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
        MinesweeperServer server = new MinesweeperServer(port);
        server.serve();
    }
}

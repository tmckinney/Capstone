package edu.wcu.cs.cs495.capstonecardgame.network;

import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.util.Log;

/**
 * This class is the client that attempt to link with the finger protocol with
 * user inputed arguments.
 * 
 * @author Michael Penland
 * @version 25.09.2012
 */
public class CardGameClient {

    /** The default port if none is provided by user. */
    private final static String DEFAULT_PORT = "8888";

    /** Default value to use if the user did not provide a user name. <CRLF>. */
        // Carriage Return. Line Feed.
    	private final static String DEFAULT_STRING = "\r\n";

    /** A String called when there is a connection error. */
    	private final static String ERROR_MSG = "Error occured while "
    			+ "creating socket to port: ";

    /** A string that is an error message for incorrect command line args. */
    	private final static String USAGE_ERROR = "Usage: FingerTCP "
    			+ "<hostname> [<port>] [<user>]";

    /** The int entered by the user to specify the port to connect to. */
    private int port;

    /** The socked used to connect with the server. */
    private Socket clientSocket; // Socket with a server

    /** A String entered by user as command line argument as the host. */
    private String host;

    /** The String entered by the user to indicate the item to search for. */
    private String command;

    /**
     * Creates client with a socket connected to a waiting server
     * 
     * @param host
     *            hostname where the server lives.
     * @param port
     *            port where the server is listening
     * 
     * @throws IOException
     *             if we cannot create a socket.
     * @throws NumberFormatException
     *             if the port String does not contain an integer.
     * @throws SecurityException
     *             in the case permission to connect to the proxy is denied
     */
    public CardGameClient(String host, String port, String command) throws IOException,
           SecurityException, NumberFormatException {
               this.command = command;
               this.port = checkForInt(DEFAULT_PORT);
               this.host = host;
               clientSocket = new Socket(host, this.port);
           }

    public CardGameClient() throws IOException, 
            SecurityException, NumberFormatException {
        try {
            clientSocket = new Socket("192.168.1.12", 8888);
        } catch (Exception ex) {
        	Log.d("CLIENT", Log.getStackTraceString(ex));
        	Log.d("CLIENT", ex.getMessage());
        }
    }

    public String pull() {
        this.command = "p~" + 1 + "~" + 1+ "~" +  this.command;
        return this.go(this.command);
    }

    public String newGame(String gameSlot) {
        return this.go("n~");
    }

    public String verifyUser(String user, String pass) {
        this.command = "v~" + user + "~" + pass + "~";
        return this.go(this.command);
    }
    /**
     * Sends to and recieves infromation from finger and displays it to the
     * console.
     * 
     * @throws IOException
     *             if something goes wrong with the out socket.
     */
    private String go(String outString) {
        String recdLine = ""; // data received from the server
        try {
            StringBuilder recieved = new StringBuilder();

            DataOutputStream toServer = new DataOutputStream(
                clientSocket.getOutputStream());
            Scanner clientIn = new Scanner(clientSocket.getInputStream());

            toServer.writeBytes(outString + "\n");

            recdLine = clientIn.next();

            recieved.append(recdLine + "\n");
            clientSocket.close();
        } catch (IOException ioe) {
            System.out.println("error");
        }
        return recdLine;
    }

    /**
     * The main method that will handle expectional behavior and initiate
     * FingerTCP's go() method.
     * 
     * @param args
     *            - The command line arguments passed by the user, format <host>
     *            <port> <user> must be either one, two, or three and order must
     *            follow the format above.
     */
  /*  public static void main(String[] args) { 
        try { 
            Client client = new Client();
            client.go("p~1");

        } catch (IOException ioe ) {
            System.out.print(ERROR_MSG);
        } catch (SecurityException se) {
            System.out.println("Connection denied by Proxy.");

        } catch (NumberFormatException nfe) { 
            System.out.println(USAGE_ERROR);
            System.out.print("Entered: "); for (int i = 0; i < args.length; i++) {
            System.out.print("<" + args[i] + "> "); } System.out.println();
        }
    }   
*/
    /**
     * This method will return the integer from the passed String or throw an
     * excpetion in case the string contains no integer.
     * 
     * @param toCheck
     *            - the String to extract an Integer from.
     * @throws NumberFormatException
     *             - in the event the passed String contains no integer.
     */
    private int checkForInt(String toCheck) throws NumberFormatException {
        return (int) java.lang.Integer.parseInt(toCheck);
    }
}

package SocialServer;

import java.io.*;
import java.net.*;
//import java.util.*;
//import com.google.gson.*;

public class PeerClient {

	public static Socket connect(String nodeIP, int port)
			throws UnknownHostException, IOException {
		Socket clientSocket = null;
		InetAddress nodeAddr = null;
		InetAddress serverAddr = null;

		try {
			nodeAddr = InetAddress.getByName(nodeIP);
		} catch (UnknownHostException e) {
			System.err.println("[CLIENT] ERROR: Unknown Host: " + nodeIP);
			throw e;
		}

		try {
			System.out.println("[CLIENT] Connecting to Server: "
					+ PeerUtils.getIPstr(nodeAddr) + ":" + port);
			clientSocket = new Socket(nodeIP, port);
			clientSocket.setSoTimeout(15000);// Set Socket Timeout for no response from server.
			serverAddr = clientSocket.getInetAddress();

		} catch (UnknownHostException e) {
			System.err.println("[CLIENT] ERROR: Unable to find IP Address: "
					+ nodeAddr.toString());
			throw e;
		} catch (IOException e) {
			System.err.println("[CLIENT] ERROR: I/O Exception for connection to "
							+ nodeAddr.toString());
			throw e;
		}

		System.out.println("[CLIENT] Connection Established to Server: "
				+ PeerUtils.getIPstr(serverAddr));
		return clientSocket;
	}

	public static void disconnect(Socket clientSocket) throws IOException {
		clientSocket.close();
	}

	public static String sendRequest(Socket clientSocket, String request)
			throws IOException {
		PrintWriter out = null;
		BufferedReader in = null;
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		String serverOutput = "";

		if ((serverOutput = in.readLine()) != null) {
			System.out.println("[CLIENT] SERVER-INFO: " + serverOutput);
		}
		out.println(request);

		try {
			serverOutput = in.readLine();
			if (serverOutput != null) {
				// System.out.println("SERVER-RESPOSNSE: " + serverOutput);
			}
		} catch (SocketTimeoutException e) {
			System.err.println("TIMEOUT: Timeout reached waiting for server to respond. CLosing Connection.");

		}
		out.close();
		in.close();

		return serverOutput;
	}

}
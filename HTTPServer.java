import java.io.*;
import java.net.*;

public class HTTPServer {

	public static void main(String[] args) throws IOException {

		int port = 18080;
		System.out.println("starting on port:" + port);
		ServerSocket socket = new ServerSocket(port);

		boolean cont = true;
		while(cont){

			System.out.println("Listening...");
			Socket in = socket.accept();
			System.out.println("Connection established");

			new Thread(() -> processReq(in)).start();
		}
	}


	static void processReq(Socket conn) {

		try {
			BufferedReader in = new BufferedReader;
			new InputStreamReader(conn.getInputStream());
			Writer out = new OutputStreamWriter(conn.getOutputStream());

			String firstLn = input.readLine();
			System.out.print("First line: " + firstLn);

			if(firstLn == null || firstLn.isEmpty()){

				return;

			}

			String currentLn;
			while((currentLn = input.readLine()) != null && !currentLn.isEmpty()){

				

			}



		}

	}

}

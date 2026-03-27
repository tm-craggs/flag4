void main() throws IOException {
	int portNumber = 18080;
	IO.println("Starting server on port " + portNumber);
	try (ServerSocket socket = new ServerSocket(portNumber)) {

		while (true) {
			IO.println("Awaiting incoming connection...");
			Socket incoming = socket.accept();
			IO.println("Connection established!");
			new Thread(() -> processRequest(incoming)).start();
		}
	}
}

static void processRequest(Socket connection) {
	try {
		BufferedReader input = new BufferedReader(
				new InputStreamReader(connection.getInputStream())
		);
		Writer output = new OutputStreamWriter(
				connection.getOutputStream()
		);

		String firstLine = input.readLine();
		IO.println("Received: " + firstLine);

		if (firstLine == null || firstLine.isEmpty()) return;

		String current;
		while ((current = input.readLine()) != null && !current.isEmpty()) {
			IO.println("Header: " + current);
		}

		if (firstLine.startsWith("GET")) {
			String[] tokens = firstLine.split(" ");
			String requestedPath = tokens[1];

			String responseBody;
			String statusLine;

			if (requestedPath.equals("/") || requestedPath.equals("/index.html")) {
				statusLine = "HTTP/1.1 200 OK";
				responseBody = "<html><body><h1>Welcome to my Web Server</h1></body></html>";
			} else {
				statusLine = "HTTP/1.1 404 Not Found";
				responseBody = "<html><body><h1>Page Not Found</h1></body></html>";
			}

			output.write(statusLine + "\r\n");
			output.write("Content-Type: text/html\r\n");
			output.write("Content-Length: " + responseBody.length() + "\r\n");
			output.write("Connection: close\r\n");
			output.write("\r\n");
			output.write(responseBody);
		}

		output.flush();
		connection.close();

	} catch (IOException ex) {
		System.err.println("Connection error: " + ex.getMessage());
	}
}
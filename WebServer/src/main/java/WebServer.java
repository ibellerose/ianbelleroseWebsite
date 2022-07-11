
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

class WebServer {
    public static void main(String args[]) {
        WebServer server = new WebServer(443);
    }

    public WebServer(int port) {
        ServerSocket server = null;
        Socket sock = null;
        InputStream in = null;
        OutputStream out = null;

        try {
            server = new ServerSocket(port);
            while (true) {
                sock = server.accept();
                out = sock.getOutputStream();
                in = sock.getInputStream();
                byte[] response = createResponse(in);
                out.write(response);
                out.flush();
                in.close();
                out.close();
                sock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sock != null){
                try{
                    server.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public byte[] createResponse(InputStream inStream) {

        byte[] response = null;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));

            String request = null;

            boolean done = false;
            while (!done) {
                String line = in.readLine();

                System.out.println("Received: " + line);

                if (line == null || line.equals("")) {
                    done = true;
                } else if (line.startsWith("GET")) {
                    int firstSpace = line.indexOf(" ");
                    int secondSpace = line.indexOf(" ", firstSpace + 1);

                    request = line.substring(firstSpace + 2, secondSpace);
                }
            }
            System.out.println("Finished Parsing Header");

            if(request == null){
                response = "<html>Illegal request: no GET</html>".getBytes();
            } else {
                StringBuilder builder = new StringBuilder();

                if (request.length() == 0){
                    // shows the default directory page

                    // opens the main.html file
                    String page = new String(readFileInBytes(new File("html/main.html")));
                    // performs a template replacement in the page
                    page = page.replace("${links}", buildFileList());

                    // Generate response
                    builder.append("HTTP/1.1 200 OK\n");
                    builder.append("Content-Type: text/html; charset=utf-8\n");
                    builder.append("\n");
                    builder.append(page);

                } 
                //TODO: add for another html page 
                //else if (request.equalsIgnoreCase(""))
                else {
                    builder.append("HTTP/1.1 400 Bad Request\n");
                    builder.append("Content-Type: text/html; charset=utf-8\n");
                    builder.append("\n");
                    builder.append("I am not sure what you want me to do...");
                }

                response = builder.toString().getBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
            response = ("<html>ERROR: " + e.getMessage() + "</html>").getBytes();
        }

        return response;
    }

    public static byte[] readFileInBytes(File f) throws IOException {

        FileInputStream file = new FileInputStream(f);
        ByteArrayOutputStream data = new ByteArrayOutputStream(file.available());
    
        byte buffer[] = new byte[512];
        int numRead = file.read(buffer);
        while (numRead > 0) {
          data.write(buffer, 0, numRead);
          numRead = file.read(buffer);
        }
        file.close();
    
        byte[] result = data.toByteArray();
        data.close();
    
        return result;
    }

    public static String buildFileList() {
        ArrayList<String> filenames = new ArrayList<>();
    
        // Creating a File object for directory
        File directoryPath = new File("html/");
        filenames.addAll(Arrays.asList(directoryPath.list()));
    
        if (filenames.size() > 0) {
          StringBuilder builder = new StringBuilder();
          builder.append("<ul>\n");
          for (var filename : filenames) {
            builder.append("<li>" + filename + "</li>");
          }
          builder.append("</ul>\n");
          return builder.toString();
        } else {
          return "No files in directory";
        }
      }
}

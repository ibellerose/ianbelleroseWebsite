
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Random;

// import org.apache.commons.net.ftp.FTP;
// import org.apache.commons.net.ftp.FTPClient;

class WebServer {
    public static void main(String args[]) {
        WebServer server = new WebServer(9000);
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

    private final static HashMap<String, String> _images = new HashMap<>() {
        {
          put("streets", "https://iili.io/JV1pSV.jpg");
          put("bread", "https://iili.io/Jj9MWG.jpg");
        }
      };
    
    private Random random = new Random();

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
            System.out.println("Finished Parsing Header\n");

            if(request == null){
                response = "<html>Illegal request: no GET</html>".getBytes();
            } else {
                StringBuilder builder = new StringBuilder();

                if (request.length() == 0){
                    // shows the default directory page

                    // opens the index.html file
                    String page = new String(readFileInBytes(new File("html/index.html")));
                    // performs a template replacement in the page
                    // page = page.replace("${links}", buildFileList());
                    // page = page.replace("/images/quote.png", buildFileList());
                    // String img = new String(readFileInBytes(new File("html/images/quote.png")));
                    // page = page.replace("/images/quote.png", img);

                    // Generate response
                    // builder.append("HTTP/1.1 200 OK\n");
                    // builder.append("Content-Type: image/png; charset=base64\n");
                    // builder.append("\n");
                    // builder.append(img);

                    // File file = new File("html/images/quote.png");
                    // byte[] bytes = new byte[(int)file.length()];
                    // String encodedFile = Base64.getEncoder().encodeToString(bytes);
                    // String img = "<img src=\"data:image/png;base64," + encodedFile + "\">";

                    // page = page.replace("images/quote.png", img);

                    builder.append("HTTP/1.1 200 OK\n");
                    builder.append("Content-Type: text/html; charset=utf-8\n");
                    builder.append("\n");
                    builder.append(page);

                } else if (request.equalsIgnoreCase("json")) {
                    // shows the JSON of a random image and sets the header name for that image
          
                    // pick a index from the map
                    int index = random.nextInt(_images.size());
          
                    // pull out the information
                    String header = (String) _images.keySet().toArray()[index];
                    String url = _images.get(header);
          
                    // Generate response
                    builder.append("HTTP/1.1 200 OK\n");
                    builder.append("Content-Type: application/json; charset=utf-8\n");
                    builder.append("\n");
                    builder.append("{");
                    builder.append("\"header\":\"").append(header).append("\",");
                    builder.append("\"image\":\"").append(url).append("\"");
                    builder.append("}");

                }  else if(request.equalsIgnoreCase("calculator")) {
                    // open the calculator.html
                    File file = new File("html/calculator.html");

                    // Generate response
                    builder.append("HTTP/1.1 200 OK\n");
                    builder.append("Content-Type: text/html; charset=utf-8\n");
                    builder.append("\n");
                    builder.append(new String(readFileInBytes(file)));
                    
                }  else if(request.equalsIgnoreCase("quote")) {
                    // open the calculator.html
                    File file = new File("html/quote.html");

                    // Generate response
                    builder.append("HTTP/1.1 200 OK\n");
                    builder.append("Content-Type: text/html; charset=utf-8\n");
                    builder.append("\n");
                    builder.append(new String(readFileInBytes(file)));

                }  else if(request.equalsIgnoreCase("clock25_5")) {
                    // open the calculator.html
                    File file = new File("html/clock25_5.html");

                    // Generate response
                    builder.append("HTTP/1.1 200 OK\n");
                    builder.append("Content-Type: text/html; charset=utf-8\n");
                    builder.append("\n");
                    builder.append(new String(readFileInBytes(file)));

                }  else if(request.contains("quote.png")) {
                    byte[] page = readFileInBytes(new File("html/images/quote.png"));
                    String quoteImg = new String(readFileInBytes(new File("html/images/quote.png")));
                    // String encodedString = Base64.getEncoder().withoutPadding().encodeToString(page.getBytes());
                    
                    // String abc = "<img src=\"data:image/png;base64,R0lGODlhCgAJAPcAAAAAAAAAMwAAZgAAmQAAzAAA/wArAAArMwArZgArmQArzAAr/wBVAABVMwBVZgBVmQBVzABV/wCAAACAMwCAZgCAmQCAzACA/wCqAACqMwCqZgCqmQCqzACq/wDVAADVMwDVZgDVmQDVzADV/wD/AAD/MwD/ZgD/mQD/zAD//zMAADMAMzMAZjMAmTMAzDMA/zMrADMrMzMrZjMrmTMrzDMr/zNVADNVMzNVZjNVmTNVzDNV/zOAADOAMzOAZjOAmTOAzDOA/zOqADOqMzOqZjOqmTOqzDOq/zPVADPVMzPVZjPVmTPVzDPV/zP/ADP/MzP/ZjP/mTP/zDP//2YAAGYAM2YAZmYAmWYAzGYA/2YrAGYrM2YrZmYrmWYrzGYr/2ZVAGZVM2ZVZmZVmWZVzGZV/2aAAGaAM2aAZmaAmWaAzGaA/2aqAGaqM2aqZmaqmWaqzGaq/2bVAGbVM2bVZmbVmWbVzGbV/2b/AGb/M2b/Zmb/mWb/zGb//5kAAJkAM5kAZpkAmZkAzJkA/5krAJkrM5krZpkrmZkrzJkr/5lVAJlVM5lVZplVmZlVzJlV/5mAAJmAM5mAZpmAmZmAzJmA/5mqAJmqM5mqZpmqmZmqzJmq/5nVAJnVM5nVZpnVmZnVzJnV/5n/AJn/M5n/Zpn/mZn/zJn//8wAAMwAM8wAZswAmcwAzMwA/8wrAMwrM8wrZswrmcwrzMwr/8xVAMxVM8xVZsxVmcxVzMxV/8yAAMyAM8yAZsyAmcyAzMyA/8yqAMyqM8yqZsyqmcyqzMyq/8zVAMzVM8zVZszVmczVzMzV/8z/AMz/M8z/Zsz/mcz/zMz///8AAP8AM/8AZv8Amf8AzP8A//8rAP8rM/8rZv8rmf8rzP8r//9VAP9VM/9VZv9Vmf9VzP9V//+AAP+AM/+AZv+Amf+AzP+A//+qAP+qM/+qZv+qmf+qzP+q///VAP/VM//VZv/Vmf/VzP/V////AP//M///Zv//mf//zP///wAAAAAAAAAAAAAAACH5BAEAAPwALAAAAAAKAAkAAAgkAPcJHLgPAEGCAAweLJhwYUOFCAdCLBhRYMKLDSkeNJgRIYCAADs=\">";

                    // byte[] fileContent = Files.readFileToByteArray(new File("html/images/quote.png"));
                    // String encodedfile = new String(Base64.encodeBase64(page), "UTF-8");
                    File file = new File("html/images/quote.png");

                    byte[] bytes = new byte[(int)file.length()];

                    FileInputStream fis = null;

                    try{

                        fis = new FileInputStream(file);

                        fis.read(bytes);
                    } finally{
                        if (fis !=null)
                            fis.close();
                    }

                    String encodedFile = Base64.getEncoder().encodeToString(bytes);
                    String img = "<img src=\"data:image/png;base64," + encodedFile + "\">";

                    // Generate response
                    builder.append("HTTP/1.1 200 OK\n");
                    // builder.append("accept-ranges: bytes\n");
                    // builder.append("access-control-allow-origin: *\n");
                    // builder.append("Content-Type: image/png;base64\n");
                    builder.append("Content-Type: text/html\n");
                    builder.append("\n");
                    builder.append(img);

                }  else if(request.contains("calculator.png")) {
                    // byte[] page = readFileInBytes(new File("html/images/quote.png"));
                    String calcImg = new String(readFileInBytes(new File("html/images/calculator.png")));
                    // String encodedString = Base64.getEncoder().withoutPadding().encodeToString(page.getBytes());
                    
                    // String abc = "<img src=\"data:image/gif;base64,R0lGODlhCgAJAPcAAAAAAAAAMwAAZgAAmQAAzAAA/wArAAArMwArZgArmQArzAAr/wBVAABVMwBVZgBVmQBVzABV/wCAAACAMwCAZgCAmQCAzACA/wCqAACqMwCqZgCqmQCqzACq/wDVAADVMwDVZgDVmQDVzADV/wD/AAD/MwD/ZgD/mQD/zAD//zMAADMAMzMAZjMAmTMAzDMA/zMrADMrMzMrZjMrmTMrzDMr/zNVADNVMzNVZjNVmTNVzDNV/zOAADOAMzOAZjOAmTOAzDOA/zOqADOqMzOqZjOqmTOqzDOq/zPVADPVMzPVZjPVmTPVzDPV/zP/ADP/MzP/ZjP/mTP/zDP//2YAAGYAM2YAZmYAmWYAzGYA/2YrAGYrM2YrZmYrmWYrzGYr/2ZVAGZVM2ZVZmZVmWZVzGZV/2aAAGaAM2aAZmaAmWaAzGaA/2aqAGaqM2aqZmaqmWaqzGaq/2bVAGbVM2bVZmbVmWbVzGbV/2b/AGb/M2b/Zmb/mWb/zGb//5kAAJkAM5kAZpkAmZkAzJkA/5krAJkrM5krZpkrmZkrzJkr/5lVAJlVM5lVZplVmZlVzJlV/5mAAJmAM5mAZpmAmZmAzJmA/5mqAJmqM5mqZpmqmZmqzJmq/5nVAJnVM5nVZpnVmZnVzJnV/5n/AJn/M5n/Zpn/mZn/zJn//8wAAMwAM8wAZswAmcwAzMwA/8wrAMwrM8wrZswrmcwrzMwr/8xVAMxVM8xVZsxVmcxVzMxV/8yAAMyAM8yAZsyAmcyAzMyA/8yqAMyqM8yqZsyqmcyqzMyq/8zVAMzVM8zVZszVmczVzMzV/8z/AMz/M8z/Zsz/mcz/zMz///8AAP8AM/8AZv8Amf8AzP8A//8rAP8rM/8rZv8rmf8rzP8r//9VAP9VM/9VZv9Vmf9VzP9V//+AAP+AM/+AZv+Amf+AzP+A//+qAP+qM/+qZv+qmf+qzP+q///VAP/VM//VZv/Vmf/VzP/V////AP//M///Zv//mf//zP///wAAAAAAAAAAAAAAACH5BAEAAPwALAAAAAAKAAkAAAgkAPcJHLgPAEGCAAweLJhwYUOFCAdCLBhRYMKLDSkeNJgRIYCAADs=\">";

                    //byte[] fileContent = Files.readFileToByteArray(new File("html/images/quote.png"));
                    // String encodedfile = new String(Base64.encodeBase64(page), "UTF-8");
                    File file = new File("html/images/calculator.png");

                    byte[] bytes = new byte[(int)file.length()];

                    FileInputStream fis = null;

                    try{

                        fis = new FileInputStream(file);

                        fis.read(bytes);
                    } finally{
                        if (fis !=null)
                            fis.close();
                    }

                    String encodedFile = Base64.getEncoder().encodeToString(bytes);
                    String img = "<img src=\"data:image/png;base64," + encodedFile + "\">";

                    // Generate response
                    builder.append("HTTP/1.1 200 OK\n");
                    builder.append("Content-Type: image/png\n");
                    // builder.append("Content-Type: text/html\n");

                    builder.append("\n");
                    builder.append(bytes);

                }  else if(request.contains("js/")) {
                    String jsScript = "";
                    if(request.contains("/randQuote.js")){
                        jsScript = new String(readFileInBytes(new File("html/js/randQuote.js")));
                    } else if(request.contains("/calc.js")){
                        jsScript = new String(readFileInBytes(new File("html/js/calc.js")));
                    } else if(request.contains("/clock25_5.js")){
                        jsScript = new String(readFileInBytes(new File("html/js/clock25_5.js")));
                    } else{
                        builder.append("HTTP/1.1 400 Bad Request\n");
                        builder.append("Content-Type: text/html; charset=utf-8\n");
                        builder.append("\n");
                        builder.append("That stylesheet does not exist");
                    }

                    if(!jsScript.equals("")){
                        // Generate response
                        builder.append("HTTP/1.1 200 OK\n");
                        builder.append("Content-Type: application/javascript\n");
                        builder.append("\n");
                        builder.append(jsScript);
                    }

                }  else if(request.contains("css/")) {
                    String styleSheet = "";
                    if(request.contains("/index.css")){
                        styleSheet = new String(readFileInBytes(new File("html/css/index.css")));
                    } else if(request.contains("/quote.css")){
                        styleSheet = new String(readFileInBytes(new File("html/css/quote.css")));
                    } else if(request.contains("/calc.scss")){
                        styleSheet = new String(readFileInBytes(new File("html/css/calc.scss")));
                    } else if(request.contains("/clock25_5.scss")){
                        styleSheet = new String(readFileInBytes(new File("html/css/clock25_5.scss")));
                    } else{
                        builder.append("HTTP/1.1 400 Bad Request\n");
                        builder.append("Content-Type: text/html; charset=utf-8\n");
                        builder.append("\n");
                        builder.append("That stylesheet does not exist");
                    }

                    if(!styleSheet.equals("")){
                        // Generate response
                        builder.append("HTTP/1.1 200 OK\n");
                        builder.append("Content-Type: text/css\n");
                        builder.append("\n");
                        builder.append(styleSheet);
                    }

                } else {
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

    public String fetchURL(String aUrl) {
        StringBuilder sb = new StringBuilder();
        URLConnection conn = null;
        InputStreamReader in = null;
        try {
          URL url = new URL(aUrl);
          conn = url.openConnection();
          if (conn != null)
            conn.setReadTimeout(20 * 1000); // timeout in 20 seconds
          if (conn != null && conn.getInputStream() != null) {
            in = new InputStreamReader(conn.getInputStream(), Charset.defaultCharset());
            BufferedReader br = new BufferedReader(in);
            if (br != null) {
              int ch;
              // read the next character until end of reader
              while ((ch = br.read()) != -1) {
                sb.append((char) ch);
              }
              br.close();
            }
          }
          in.close();
        } catch (Exception ex) {
          System.out.println("Exception in url request:" + ex.getMessage());
        }
        return sb.toString();
    }

}


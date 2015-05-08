import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

//”√ªß¿‡  
public class User {
    private String name;
    private String ip;
    private Socket socket;


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private BufferedReader reader = null;
    private PrintWriter writer = null;


    public User(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}  


import java.net.Socket;
import java.net.ServerSocket;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public final class EchoServer implements Runnable{

    private Socket socket;

    public EchoServer(Socket s){
        this.socket = s;
    }

    public static void main(String[] args) throws Exception{
        try(ServerSocket serverSocket = new ServerSocket(22222)){
            while(true){
                Thread thread = new Thread(new EchoServer(serverSocket.accept()));
                thread.start();
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void run(){
        try{
            String address = socket.getInetAddress().getHostAddress();
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String clientS = "";

            System.out.printf("Client connected: %s%n", address);
            while(!clientS.equals("exit")){
                clientS = br.readLine();
                out.println(clientS);
            }
            if(clientS.equals("exit")){
                System.out.println("Client disconnected: " + address);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
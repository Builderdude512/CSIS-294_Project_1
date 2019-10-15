import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class P2PUtil {

    public static String connectForOneMessage(String sIP, int iPort, String sMessage){

        private Socket socket            = null;
        private DataInputStream  in   = null;
        private DataOutputStream out     = null;

        socket = new Socket(sIP, iPort);
        System.out.println("Connected");
        in  = new DataInputStream(System.in);
        out    = new DataOutputStream(socket.getOutputStream());

        out.writeUTF(sMessage);

        in.close();
        out.close();
        socket.close();

    }

}

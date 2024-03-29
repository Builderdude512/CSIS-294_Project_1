import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class P2PServer implements Runnable {

    private int thisServerPort;

    public P2PServer(int iPort){
        thisServerPort = iPort;
    }


    public void run() {

        BlockchainUtil u = new BlockchainUtil();


        try (ServerSocket oServerSocket = new ServerSocket(thisServerPort)) {

            System.out.println("Server is listening on port " + thisServerPort);

            while(true) {
                Socket oSocket = oServerSocket.accept();
                u.p("[server]: New client connected: " + oSocket.getRemoteSocketAddress());

                InputStream input = oSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = oSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                // Get one time message from client.
                String sReceivedMessage = reader.readLine();

                u.p("[server]: Server received message: " + sReceivedMessage);

                if (sReceivedMessage.startsWith("mined")) {

                    u.p("[server]: Block has been mined by other node.");
                    Miner.bAbortPoW = true;

                    writer.println("Server received: " + sReceivedMessage);
                    writer.flush();
                }
                else {

                    P2PMessage oMessage = new P2PMessage();
                    oMessage.setMessage(sReceivedMessage);
                    Miner.oIncomingMessageQueue.enqueue(oMessage);

                    writer.println("Server queued: " + sReceivedMessage);
                    writer.flush();
                }
            }
        }
        catch (IOException ex) {
            u.p("[server]: Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}

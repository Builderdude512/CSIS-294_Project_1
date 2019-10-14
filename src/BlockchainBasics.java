public class BlockchainBasics {

    public static String sRemoteMinerIP = null;
    public static int iRemoteMinerPort = 0;

    public static void main(String[] args){

        BlockchainUtil u = new BlockchainUtil();


        String sUsername = u.promptUser("Username for this miner? ");
        Miner oMiner = new Miner();
        oMiner.sUsername = sUsername;
        Thread oMinerThread = new Thread(oMiner);
        oMinerThread.start();


        u.sleep(500);

        String sPort = u.promptUser("What port do you want to start server on? ");
        int iPort = Integer.parseInt(sPort);
        P2PServer oServer = new P2PServer(iPort);
        Thread oServerThread = new Thread(oServer);
        oServerThread.start();

        u.sleep(500);


        String sSend = u.promptUser("Send transactions to another miner (y,n)? ");

        if(sSend.equals("y")){
            sRemoteMinerIP = u.promptUser("Miner's IP address? " );
            String sTempPort = u.promptUser("Miner's Port? ");
            iRemoteMinerPort = Integer.parseInt(sTempPort);
        }

        while(true){

            String sTransaction = u.promptUser("Enter Transaction: ");

            if(sSend.equals("y")) {
                String sReply = P2PUtil.connectForOneMessage(sRemoteMinerIP, iRemoteMinerPort, sTransaction);
                u.p("[main] Reply from server: " + sReply);
            }

            Miner.lstTransactionPool.add(sTransaction);
        }
    }

}

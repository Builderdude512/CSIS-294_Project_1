import java.util.ArrayList;

public class Miner implements Runnable{

    public static volatile P2PMessageQueue oIncomingMessageQueue = new P2PMessageQueue();
    public static volatile boolean bAbortPoW = false;
    public static volatile ArrayList<String> lstTransactionPool = new ArrayList<>();
    int iBlockTxSize = 4;
    public String sUsername;

    public Boolean doProofofWork(Block oblock){

        String holderarg; //for oblock.getdifficulty
        String dummy = "0"

        while(dummy.length() > holderarg){
            dummy = dummy + "0";
        }


    }

    public void run() {

        BlockchainUtil u = new BlockchainUtil();

        u.p("Miner thread started.");


        // *****************************
        // *** Eternal Mining Loop *****
        // Because miner always checking for next block to immediately work on.
        while(true){

            u.sleep(500);

            while(oIncomingMessageQueue.hasNodes()){
                P2PMessage oMessage = oIncomingMessageQueue.dequeue();
                lstTransactionPool.add(oMessage.getMessage());
            }

            // Check if transaction pool full and lock if it is.
            if (lstTransactionPool.size() >= iBlockTxSize) {

                Block oBlock = new Block();
                oBlock.setMinerUsername(sUsername);
                oBlock.computeHash();
                String sMerkleRoot = oBlock.computeMerkleRoot(lstTransactionPool);
                oBlock.setMerkleRoot(sMerkleRoot);
                boolean bMined = doProofOfWork(oBlock);

                if(bMined){

                    // Notify connected node.
                    if(BlockchainBasics.sRemoteMinerIP != null){
                        P2PUtil.connectForOneMessage(BlockchainBasics.sRemoteMinerIP, BlockchainBasics.iRemoteMinerPort,
                                "mined");
                    }

                    u.p("");
                    u.p("***********");
                    u.p("BLOCK MINED");
                    u.p("nonce: " + oBlock.getNonce());
                    u.p("hash: " + oBlock.getHash());
                    u.p("");
                    u.p("Transactions:");
                    for(int x=0; x < lstTransactionPool.size(); x++){
                        u.p("Tx " + x + ": " + lstTransactionPool.get(x));
                    }
                    u.p("***********");
                }
                else{
                    u.p("[miner] Mining block failed.");
                }

                // Clear tx pool.
                lstTransactionPool.clear();
            }
        }
    }

}

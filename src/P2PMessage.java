public class P2PMessage {

    private String sMessage;
    public P2PMessage next = null;

    public void setMessage(String sMessage){

        this.sMessage = sMessage;
    }

    public String getMessage(){

        return sMessage;
    }

}

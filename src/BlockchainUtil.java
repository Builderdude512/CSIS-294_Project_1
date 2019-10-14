import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Scanner;

public class BlockchainUtil {

    public byte[] generateHash(String arg){

        MessageDigest message = MessageDigest.getInstance("SHA-256");

        return message.digest(arg.getBytes(StandardCharsets.UTF_8));
    }

    public String promptUser(String sQuestion){

        System.out.print(sQuestion);
        Scanner oCommandInput = new Scanner(System.in);

        return oCommandInput.nextLine();
    }

    public void p(String sMessage){
        System.out.println(sMessage);
    }

    public void sleep(long lMillis){

        try{
            Thread.sleep(lMillis);
        }
        catch(Exception ex){
            // do nothing.
        }
    }
}

import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;  

public class Client {  
    private Client() {}  
    public static void main(String[] args) {  
        try {  
            Registry registry = LocateRegistry.getRegistry(null); 
            IRemote stub = (IRemote) registry.lookup("bank-rmi");
            stub.testMsg();  
        } catch (Exception e) {
            System.err.println("Exception: " + e.toString()); 
            e.printStackTrace(); 
        } 
    } 
} 
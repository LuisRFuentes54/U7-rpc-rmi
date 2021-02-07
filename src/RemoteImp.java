import java.rmi.RemoteException;

public class RemoteImp implements IRemote {  

    public void testMsg() throws RemoteException {  
        System.out.println("Hello There");  
    }  
}
import java.rmi.Remote; 
import java.rmi.RemoteException;  

public interface IRemote extends Remote {
    boolean userExist(String ci) throws RemoteException;
}
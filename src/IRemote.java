import java.rmi.Remote; 
import java.rmi.RemoteException;  

public interface IRemote extends Remote {
    boolean userExist(String ci) throws RemoteException;
    void createUser(String ci, String name, String username, String password) throws RemoteException;
    boolean authUser(String ci, String username, String password) throws RemoteException;
    int createAccount(String ci, float deposit) throws RemoteException;
    boolean permitCreateAccount(String ci) throws RemoteException;
}
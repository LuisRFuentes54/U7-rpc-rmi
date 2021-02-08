import java.rmi.Remote; 
import java.rmi.RemoteException;  

public interface IRemote extends Remote {
    boolean userExist(String ci) throws RemoteException;
    void createUser(String ci, String name, String username, String password) throws RemoteException;
    boolean authUser(String username, String password) throws RemoteException;
    int createAccount(String ci, float deposit) throws RemoteException;
    boolean permitCreateAccount(String ci) throws RemoteException;
    String[] getAccounts(String username) throws RemoteException;
    String[] getTransactions(String account) throws RemoteException;
}
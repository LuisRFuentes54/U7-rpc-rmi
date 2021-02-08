import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class RemoteImp implements IRemote {

    private File users = new File("user.txt");
    private File accounts = new File("account.txt");
    private File deposits = new File("deposit.txt");

    @Override
    public boolean userExist(String ci) throws RemoteException {
        boolean getUser = false;
        try {
            System.out.println("Verifiying User....");
            Scanner S = new Scanner(this.users);
            while (S.hasNextLine() && !getUser) {
                String ciStored = S.nextLine();
                if (ciStored.equals(ci)) {
                    getUser = true;
                    break;
                } else if (!ciStored.equals("")) {
                    for (int i = 0; i <= 2; i++)
                        S.nextLine();
                }
            }
            S.close();
        } catch (IOException e) {
            System.out.println("Something was broke");
            e.printStackTrace();
        }
        return getUser;
    }

    @Override
    public void createUser(String ci, String name, String username, String password) throws RemoteException {
        try {
            FileWriter writer = new FileWriter(this.users, true);
            writer.write(ci + "\n");
            writer.write(name + "\n");
            writer.write(username + "\n");
            writer.write(password + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Something was broke");
            e.printStackTrace();
        }
    }

    @Override
    public boolean authUser(String ci, String username, String password) throws RemoteException {
        try {
            System.out.println("Auth User....");
            Scanner S = new Scanner(this.users);
            String usernameStored;
            String passwordStored;
            while (S.hasNextLine()) {
                String ciStored = S.nextLine();
                if (ciStored.equals(ci)) {
                    S.nextLine();
                    usernameStored = S.nextLine();
                    passwordStored = S.nextLine();
                    S.close();
                    if (usernameStored.equals(username) && passwordStored.equals(password))
                        return true;
                    else
                        return false;
                } else if (!ciStored.equals("")) {
                    for (int i = 0; i <= 2; i++)
                        S.nextLine();
                }
            }
            S.close();
        } catch (IOException e) {
            System.out.println("Something was broke");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int createAccount(String ci, float deposit) throws RemoteException {
        int account = (int)(Math. random()*10000+1);
        try {
            FileWriter writerAccount = new FileWriter(this.accounts, true);
            writerAccount.write(ci + "\n");
            writerAccount.write(account + "\n");
            writerAccount.close();
            FileWriter writerDeposit = new FileWriter(this.deposits, true);
            writerDeposit.write(account + "\n");
            writerDeposit.write(deposit + "\n");
            writerDeposit.write("Deposito inicial" + "\n");
            writerDeposit.close();
        } catch (IOException e) {
            System.out.println("Something was broke");
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public boolean permitCreateAccount(String ci) throws RemoteException {
        int count = 0;
        try {
            System.out.println("Verifiying User....");
            Scanner S = new Scanner(this.accounts);
            while (S.hasNextLine()) {
                String ciStored = S.nextLine();
                if (ciStored.equals(ci)) {
                    count++;
                    if (count == 3) {
                        S.close();
                        return false;
                    }
                } else if (ciStored.isEmpty())
                    S.nextLine();
            }
            S.close();
            return true;
        } catch (IOException e) {
            System.out.println("Something was broke");
            e.printStackTrace();
        }
        return false;
    }
}  
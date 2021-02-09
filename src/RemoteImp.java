import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.ArrayList;

public class RemoteImp implements IRemote {

    private File users = new File("user.txt");
    private File accounts = new File("account.txt");
    private File deposits = new File("deposit.txt");
    private File withdrawals = new File("withdrawal.txt");
    private File transferences = new File("transference.txt");

    private String getCI(String username) {
        try {
            Scanner S = new Scanner(this.users);
            while (S.hasNextLine()) {
                String ci = S.nextLine();
                if(!ci.isEmpty()){
                    S.nextLine();
                    String usernameStored = S.nextLine();
                    if (usernameStored.equals(username)){
                        S.close();
                        return ci;
                    }
                    else 
                        S.nextLine();
                }
            }
            S.close();
        } catch (FileNotFoundException e) {
            System.out.println("Something was broke");
            e.printStackTrace();
        }
        return "";
    }

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
    public boolean authUser(String username, String password) throws RemoteException {
        try {
            System.out.println("Auth User....");
            Scanner S = new Scanner(this.users);
            String usernameStored;
            String passwordStored;
            while (S.hasNextLine()) {
                if(!S.nextLine().isEmpty()){
                    S.nextLine();
                    usernameStored = S.nextLine();
                    passwordStored = S.nextLine();
                    if (usernameStored.equals(username) && passwordStored.equals(password)){
                        S.close();
                        return true;
                    }
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

    @Override
    public ArrayList<String> getAccounts(String username) throws RemoteException {
        ArrayList<String> accountsUser = new ArrayList<String>();
        String ci = getCI(username);
        System.out.println(ci);
        try {
            Scanner S = new Scanner(this.accounts);
            while (S.hasNextLine()){
                String ciStored = S.nextLine();
                if(ciStored.equals(ci)){
                    accountsUser.add(S.nextLine());
                }
                else if (!ciStored.isEmpty())
                    S.nextLine();
            }
            S.close();
            return accountsUser;
        } catch (FileNotFoundException e) {
            System.out.println("Something was broke");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<String> getTransactions(String account) throws RemoteException {
        ArrayList<String> transactions = new ArrayList<String>();
        try {
            Scanner depositScanner = new Scanner(this.deposits);
            Scanner withdrawalScanner = new Scanner(this.withdrawals);
            Scanner transferenceScanner = new Scanner(this.transferences);
            while (depositScanner.hasNextLine()){
                String accountStored = depositScanner.nextLine();
                if (accountStored.equals(account)){
                    String deposit = "Deposito - Monto: " + depositScanner.nextLine() + " - Descripción: "+ depositScanner.nextLine();
                    transactions.add(deposit);
                }
                else if (!accountStored.isEmpty()){
                    depositScanner.nextLine();
                    depositScanner.nextLine();
                }
            }
            while (withdrawalScanner.hasNextLine()){
                String accountStored = withdrawalScanner.nextLine();
                if (accountStored.equals(account)){
                    String withdrawal = "Retiro - Monto: " + withdrawalScanner.nextLine() + " - Descripción: "+ withdrawalScanner.nextLine();
                    transactions.add(withdrawal);
                }
                else if (!accountStored.isEmpty()){
                    withdrawalScanner.nextLine();
                    withdrawalScanner.nextLine();
                }
            }
            while (transferenceScanner.hasNextLine()){
                String accountStored = transferenceScanner.nextLine();
                if (accountStored.equals(account)){
                    String transference = "Transferencia - Monto: " + transferenceScanner.nextLine() + " - Descripción: "+ transferenceScanner.nextLine();
                    transactions.add(transference);
                }
                else if (!accountStored.isEmpty()){
                    transferenceScanner.nextLine();
                    transferenceScanner.nextLine();
                }
            }
            depositScanner.close();
            withdrawalScanner.close();
            transferenceScanner.close();
            return transactions;
        } catch (FileNotFoundException e) {
            System.out.println("Something was broke");
            e.printStackTrace();
        }
        return null;
    }
}  
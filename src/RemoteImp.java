import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class RemoteImp implements IRemote {

    private File users = new File("user.txt");

    @Override
    public boolean userExist(String ci) throws RemoteException {
        boolean getUser = false;
        try {
            Scanner S = new Scanner(users);
            while (S.hasNextLine() && !getUser) {
                if (S.nextLine().equals(ci)) {
                    getUser = true;
                } else {
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
}  
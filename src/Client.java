import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.ArrayList;

public class Client {
    private Client() {
    }

    private static void printMainMenu() {
        System.out.println("Por favor, seleccione una de estas opciones para continuar");
        System.out.println("1- Apertura de cuenta");
        System.out.println("2- Realizar Transacción");
        System.out.println("0- Salir");
        System.out.print("Ingrese aquí la opción de su preferencia: ");
    }

    private static void printTransactionMenu() {
        System.out.println("Por favor, seleccione una de estas opciones para continuar");
        System.out.println("1- Consulta de cuenta");
        System.out.println("2- Deposito a cuenta");
        System.out.println("3- Retiro de cuenta");
        System.out.println("4- Transferencia entre cuentas");
        System.out.print("Ingrese aquí la opción de su preferencia: ");
    }

    private static String authUser(IRemote stub, Scanner keyboard) {
        System.out.println("Menú de autenticación de usuario");
        System.out.print("Ingrese su username: ");
        String username = keyboard.next();
        System.out.print("Ingrese su password: ");
        String password = keyboard.next();
        try {
            if (stub.authUser(username, password))
                return username;
            else
                return "";
        } catch (RemoteException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static void main(String[] args) {  
        try {  
            Registry registry = LocateRegistry.getRegistry(null); 
            IRemote stub = (IRemote) registry.lookup("bank-rmi");
            Scanner keyboard = new Scanner(System.in);
            int option = 0;
            int option2 = 0;
            String ci;
            String name;
            String username;
            String password;
            float amount;
            int account;
            System.out.println("ATM Client");
            System.out.println("Bienvenido a Mini-Banco");
            while (true) {
                ci = "";
                name = "";
                username = "";
                password = "";
                amount = 0;
                account = 0;
                printMainMenu();
                option = keyboard.nextInt();
                switch (option) {
                    case 1:
                        System.out.print("Ingrese su CI: ");
                        ci = keyboard.next();
                        if (stub.userExist(ci)){
                            if (stub.permitCreateAccount(ci)) {
                                if (!authUser(stub, keyboard).isEmpty())
                                    System.out.println("Usuario autenticado");
                                else {
                                    System.out.println("Error en la autenticación del usuario");
                                    break;
                                }
                            } else {
                                System.out.println("Error creando la cuenta. La persona indicada llego al limite de cuentas");
                                break;
                            }
                        }
                        else{
                            keyboard.nextLine();
                            System.out.println("Menú de creación de usuario");
                            System.out.print("Ingrese su nombre: ");
                            name = keyboard.nextLine();
                            System.out.print("Ingrese su username: ");
                            username = keyboard.next();
                            System.out.print("Ingrese su password: ");
                            password = keyboard.next();
                            stub.createUser(ci, name, username, password);
                            System.out.println("Usuario creado");
                        }
                        System.out.print("Ingrese el monto del deposito inicial: ");
                        amount = keyboard.nextFloat();
                        account = stub.createAccount(ci, amount);
                        System.out.println("Cuenta creada. Nro de cuenta: " + account);
                        break;
                
                    case 2:
                        username = authUser(stub, keyboard);
                        String[] accounts;
                        if(!username.isEmpty()){
                            printTransactionMenu();
                            option2 = keyboard.nextInt();
                            switch (option2) {
                                case 1:
                                    ArrayList<String> accounts = stub.getAccounts(username);
                                    int i = 1;
                                    for (String acc : accounts){
                                        System.out.println(i + "- " + acc);
                                        i++;
                                    }
                                    System.out.print("Seleccione la cuenta a consultar: ");
                                    option2 = keyboard.nextInt() - 1;
                                    ArrayList<String> transactions = stub.getTransactions(accounts.get(option2));
                                    for (String trans : transactions){  
                                        System.out.println(trans);
                                    }
                                    break;
                            
                                
                                case 3:
                                    ArrayList<String> accountsForWithdrawal = stub.getAccounts(username);
                                    for (String acc : accountsForWithdrawal)
                                        if(acc != null)
                                            System.out.println(i+1 + "- " + acc);
                                    System.out.print("Seleccione la cuenta de la cual desea retirar: ");
                                    option2 = keyboard.nextInt() - 1;
                                    float availableAmount = stub.getAvailableAmount(accountsForWithdrawal.get(option2));
                                    System.out.println("Monto disponible:" + availableAmount);
                                    System.out.print("Selecciona la cantidad que desea retirar de la cuenta "+ accountsForWithdrawal.get(option2) + ": ");
                                    amount = keyboard.nextInt();
                                    Float retiro;
                                        if (availableAmount >= amount){
                                            retiro = stub.makeWithdrawal(accountsForWithdrawal.get(option2), amount, availableAmount);
                                            System.out.println("Se ha realizado su retiro con exito | Balance actual: " + retiro);
                                        }
                                        else 
                                            System.out.println("El monto es mayor al disponible en su cuenta");
                                        
                                    break;

                                default:
                                    break;

                            }
                        }
                        else {
                            System.out.println("Error en la autenticación del usuario");
                        }
                        break;

                    case 0:
                        System.out.println("Gracias por usar ATM Client de Mini-Banco");
                        break;

                    default:
                        break;
                }
                if (option == 0)
                    break;
            }
            keyboard.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.toString()); 
            e.printStackTrace(); 
        } 
    } 
} 
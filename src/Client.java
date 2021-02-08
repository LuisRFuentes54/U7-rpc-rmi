import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {  
    private Client() {}
    
    private static void printMenu() {
        System.out.println("Por favor, seleccione una de estas opciones para continuar");
        System.out.println("1- Apertura de cuenta");
        System.out.println("2- Consulta de cuenta");
        System.out.println("3- Deposito a una cuenta");
        System.out.println("4- Retiro de una cuenta");
        System.out.println("5- Transferencia a una cuenta");
        System.out.println("6- Salir");
        System.out.print("Ingrese aquí la opción de su preferencia: ");
    }
    public static void main(String[] args) {  
        try {  
            Registry registry = LocateRegistry.getRegistry(null); 
            IRemote stub = (IRemote) registry.lookup("bank-rmi");
            Scanner keyboard = new Scanner(System.in);
            int option = 0;
            String ci;
            System.out.println("ATM Client");
            System.out.println("Bienvenido a Mini-Banco");
            while (true) {
                printMenu();
                option = keyboard.nextInt();
                switch (option) {
                    case 1:
                        System.out.print("Ingrese su CI: ");
                        ci = keyboard.next();
                        if (stub.userExist(ci)){
                            System.out.println("El usuario existe");
                        }
                        else{
                            System.out.println("El usuario no existe");
                        }
                        break;
                
                    case 6:
                        System.out.println("Gracias por usar ATM Client de Mini-Banco");
                        break;

                    default:
                        break;
                }
                if (option == 6)
                    break;
            }
            keyboard.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.toString()); 
            e.printStackTrace(); 
        } 
    } 
} 
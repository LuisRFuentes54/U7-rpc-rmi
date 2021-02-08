public class User {
    private String ci;
    private String name;
    private String username;
    private String password;

    User(String ciC, String nameC, String usernameC, String passwordC){
        this.ci = ciC;
        this.name = nameC;
        this.username = usernameC;
        this.password = passwordC;
    }

    public void printInfo() {
        System.out.println(ci);
        System.out.println(name);
        System.out.println(username);
        System.out.println(password);
    }
}

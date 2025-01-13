import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        while(true) {
            System.out.print("$ ");
    
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            
            System.out.println(input + ": command not found");
        }
    }
}

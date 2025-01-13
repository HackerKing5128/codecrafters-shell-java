import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        while(true) {
            System.out.print("$ ");
    
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            
            if (input.startsWith("exit")) {
                int exitCode = Integer.parseInt(input.substring(5).trim()); // Extract from index 5, trim spaces, and parse

                System.exit(exitCode);

            } else {
                System.out.println(input + ": command not found");
            }
        }
    }
}

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        while(true) {
            String[] commands = {"echo", "exit", "type"};

            System.out.print("$ ");
    
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            
            if (input.startsWith("exit")) {
                // exit builtin

                int exitCode = Integer.parseInt(input.substring(5).trim()); // Extract from index 5, trim spaces, and parse

                System.exit(exitCode);

            } else if(input.startsWith("echo")) {
                // echo builtin

                String text = input.substring(5);
                System.out.println(text);

            } else if(input.startsWith("type")) {
                // type builtin

                String cmd = input.substring(5).trim();

                if(Arrays.asList(commands).contains(cmd)) {
                    System.out.println(cmd + " is a shell builtin");
                } else {
                System.out.println(input + ": not found");
                }

            } else {
                // invalid command
                System.out.println(input + ": command not found");
            }
        }
    }
}

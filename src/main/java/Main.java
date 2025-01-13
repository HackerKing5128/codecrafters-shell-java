import java.nio.file.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static String type_path(String input, String[] commands) {
        // type builtin - executable files v2.0
        
        String path = System.getenv("PATH");    // get environment variable PATH
        String[] directories = path.split(":"); // get all directories in PATH

        String cmd = input.substring(5);    // Extract the command after "type "

        //Search for the command in the PATH directories
        for (String dir : directories) {
            Path cmdPath = Paths.get(dir, cmd);
            if (Files.exists(cmdPath) && Files.isExecutable(cmdPath)) {
                return cmd + " is " + cmdPath.toString(); // Return the full path as a string
            } else if (Arrays.asList(commands).contains(cmd)) {
                return cmd + " is a shell builtin";   // functionality from type builtin v1.0
            }
        }

        return cmd + ": not found";

        
    }

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

                /*String cmd = input.substring(5).trim();

                if(Arrays.asList(commands).contains(cmd)) {
                    System.out.println(cmd + " is a shell builtin");
                } else {
                System.out.println(cmd + ": not found");
                } */

                System.out.println(type_path(input, commands));

            } else {
                // invalid command
                System.out.println(input + ": command not found");
            }
        }
    }

}

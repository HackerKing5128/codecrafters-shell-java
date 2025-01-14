import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static String type_path(String input, String[] commands) {
        // type builtin - executable files v2.0

        String path = System.getenv("PATH"); // get environment variable PATH
        String[] directories = path.split(":"); // get all directories in PATH

        String cmd = input.substring(5); // Extract the command after "type "

        // Search for the command in the PATH directories
        for (String dir : directories) {
            Path cmdPath = Paths.get(dir, cmd);
            if (Files.exists(cmdPath) && Files.isExecutable(cmdPath)) {
                return cmd + " is " + cmdPath.toString(); // Return the full path as a string
            } else if (Arrays.asList(commands).contains(cmd)) {
                return cmd + " is a shell builtin"; // functionality from type builtin v1.0
            }
        }

        return cmd + ": not found";

    }

    public static void execute(String[] inputParse) throws IOException {
        String path = System.getenv("PATH"); // get environment variable PATH
        String[] directories = path.split(":"); // get all directories in PATH

        // extract command
        String cmd = inputParse[0];

        // Search for the command in the PATH directories
        for (String dir : directories) {
            Path cmdPath = Paths.get(dir, cmd);

            if (Files.exists(cmdPath) && Files.isExecutable(cmdPath)) {
                // Execute the command with arguments using ProcessBuilder
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command(inputParse); // Add command & arguments
                processBuilder.redirectErrorStream(true); // Merge stdout & stderr

                Process process = processBuilder.start();

                // Read the output of the command (using BufferedReader)
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                try {
                    // Wait for the process to finish
                    process.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;

            }
        }

        // If command not found
        System.out.println(cmd + ": not found");

    }

    public static String pwd() {
        return System.getProperty("user.dir"); // return present working directory
    }

    public static void changeDirectory(String newDirectory) {
        // Specify the new working directory
        File newDir = new File(newDirectory);

        // Check if the directory exists & is valid
        if (newDir.exists() && newDir.isDirectory()) {
            // Update "user.dir" System property
            System.setProperty("user.dir", newDir.getAbsolutePath());

        } else {
            // if directory doesn't exist or invalid
            System.out.println("cd: " + newDirectory + ": No such file or directory");
        }

    }

    public static void main(String[] args) throws Exception {

        while (true) {
            String[] commands = { "echo", "exit", "type", "pwd", "cd" };

            System.out.print("$ ");

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            if (input.startsWith("exit")) {
                // exit builtin

                int exitCode = Integer.parseInt(input.substring(5).trim()); // Extract from index 5, trim spaces, and
                                                                            // parse
                System.exit(exitCode);

            } else if (input.startsWith("echo")) {
                // echo builtin

                String text = input.substring(5);
                System.out.println(text);

            } else if (input.startsWith("type")) {
                // type builtin

                System.out.println(type_path(input, commands));

            } else if (input.equals("pwd")) {
                // pwd command implementation

                System.out.println(pwd());

            } else if (input.startsWith("cd")) {
                // cd builtin

                String newDir = input.substring(3); // extract the path of new directory
                changeDirectory(newDir);

            } else {
                // check for external programs

                String[] inputParse = input.split("\\s+");

                // call function to check & execute external program
                execute(inputParse);

            }
        }
    }

}

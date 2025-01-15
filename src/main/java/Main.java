import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.regex.*;
import java.util.Scanner;

public class Main {

    public static String[] parseText(String inputStrings) {
        // parse argument for ECHO builtin

        ArrayList<String> tokens = new ArrayList<>(); // to store argument strings

        /*
         * Regex for finding text within :-
         * '([^']*)': Matches single-quoted strings.
         * (\\S+): Matches unquoted words.
         */
        Pattern pattern = Pattern.compile("'(.*?)'|(\\S+)");
        Matcher matcher = pattern.matcher(inputStrings);

        while (matcher.find()) {

            if (matcher.group(1) != null) {
                // single-quoted text
                tokens.add(matcher.group(1)); 
    
            } else if (matcher.group(2) != null) {
                tokens.add(matcher.group(2));  // unquoted text
            }
        }

        return tokens.toArray(new String[0]);

    }

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

    public static void changeDirectoryByPath(String newDirectory) {
        // Get the current working directory (pwd)
        String currDir = pwd();

        // Resolve the path (relative to the current directory if not absolute)
        File newDir = new File(newDirectory);
        if (!newDir.isAbsolute()) {
            newDir = new File(currDir, newDirectory);
        }

        try {
            // Normalize the path to resolve relative segments like "./" and "../"
            String canonicalPath = newDir.getCanonicalPath();

            // Check if the resolved directory exists and is valid
            File canonicalDir = new File(canonicalPath);
            if (canonicalDir.exists() && canonicalDir.isDirectory()) {
                // Update the "user.dir" system property
                System.setProperty("user.dir", canonicalPath);
            } else {
                System.out.println("cd: " + newDirectory + ": No such file or directory");
            }
        } catch (IOException e) {
            System.out.println("cd: Error resolving path: " + e.getMessage());
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

                // Parse arguments to handle quotes & concatenation
                String[] text = parseText(input.substring(5).trim());

                // Concatenate parsed arguments with single space
                String result = String.join("", text);
                System.out.println(result);

            } else if (input.startsWith("type")) {
                // type builtin

                System.out.println(type_path(input, commands));

            } else if (input.equals("pwd")) {
                // pwd command implementation

                System.out.println(pwd());

            } else if (input.startsWith("cd")) {
                // cd builtin

                String newDir = input.substring(3); // extract the path of new directory

                if (newDir.equals("~")) {
                    // if "~" is provided for HOME directory
                    String path = System.getenv("HOME"); // get environment variable HOME -> home directory path
                    changeDirectoryByPath(path);

                } else {
                    // if path is provided
                    changeDirectoryByPath(newDir);
                }

            } else {
                // check for external programs

                String[] inputParse = input.split("\\s+");

                // call function to check & execute external program
                execute(inputParse);

            }
        }
    }

}

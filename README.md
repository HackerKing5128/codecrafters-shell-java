# Build Your Own Shell - Java Solution

[![progress-banner](https://backend.codecrafters.io/progress/shell/f0aaff00-e494-4b62-b296-836ea06e6974)](https://app.codecrafters.io/users/codecrafters-bot?r=2qF)

This repository contains my solution for the ["Build Your Own Shell" Challenge](https://app.codecrafters.io/courses/shell/overview).

# Overview

In this challenge, I built a POSIX-compliant shell capable of interpreting shell commands, running external programs, and handling builtin commands like cd, pwd, echo, and more. Along the way, I learned about shell command parsing, REPLs, and the implementation of builtin commands.

### Core Shell Implementation

The base stages required building the core of the shell to support basic commands and functionality. <br>

### Features Implemented

I have successfully completed the implementation of the core functionality of the shell across multiple stages. This includes:

1. **Command Parsing**:
   - Parses and interprets user input effectively, supporting arguments and options.
   
2. **Builtin Commands**:
   - `exit`: Exit the shell with a specified status code.
   - `echo`: Print text to the terminal.
   - `type`: Identify commands as built-in or external.
   - `pwd`: Displays the current working directory.
   - `cd`: Supports navigation using:-
     - Absolute paths, e.g., `/usr/local/bin`
     - Relative paths, e.g., `./`, `../`, `./dir`
     - The `~` character, representing the user's home directory.

3. **External Program Execution**:
   - Runs programs located in directories specified by the `PATH` environment variable.

<br>

## Usage Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/HackerKing5128/codecrafters-shell-java.git 
2. Navigate to the project directory:
   ```bash
   cd codecrafters-shell-java
3. Run the shell program:
   ```bash
   ./your_program.sh
4. Test with commands:
   - Try `cd`, `pwd`, `echo Hello`, or `type ls`

<br><hr>
 You can view the entry point of the implementation in `src/main/java/Main.java`.
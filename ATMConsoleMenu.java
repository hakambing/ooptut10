import java.util.Scanner;

public class ATMConsoleMenu {

    private static final int IDLE_TIMEOUT = 10000; // 10 seconds in milliseconds
    private static final int EXIT_TIMEOUT = 20000; // 20 seconds in milliseconds
    private static volatile boolean userInputReceived = false;

    public static void main(String[] args) {
        System.out.println("Welcome to the ATM!");

        Thread inputThread = new Thread(() -> getUserInput());
        inputThread.start();

        // Display the main menu
        displayMenu();

        // Wait for the input thread to finish or timeout
        try {
            inputThread.join(IDLE_TIMEOUT);
            if (!userInputReceived) {
                System.out.println("\nThe system will be exited after 20 seconds if no input is made.");
                inputThread.join(EXIT_TIMEOUT);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // If the user didn't input anything, exit
        if (!userInputReceived) {
            System.out.println("No input received. Exiting the system.");
            System.exit(0);
        }
    }

    private static void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Deposit Money");
        System.out.println("4. Exit");
    }

    private static void getUserInput() {
        Scanner scanner = new Scanner(System.in);
        userInputReceived = false;

        System.out.print("\nEnter your choice: ");
        String userInput = scanner.nextLine();

        // Set the flag to indicate that user input has been received
        userInputReceived = true;

        // Process user input (you can add more logic here)
        switch (userInput) {
            case "1":
                System.out.println("Checking Balance...");
                break;
            case "2":
                System.out.println("Withdrawing Money...");
                break;
            case "3":
                System.out.println("Depositing Money...");
                break;
            case "4":
                System.out.println("Exiting ATM. Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
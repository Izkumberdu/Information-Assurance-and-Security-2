import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("Menu: \n1 -- Railfence\n2 -- Vigenere\n3 -- DES\n4 -- RC4\n5 -- Exit");
            System.out.print("Input: ");
            int input = 0;
            if (scan.hasNextInt()) {
                input = scan.nextInt();
                scan.nextLine(); // consume the newline
            } else {
                scan.nextLine(); // consume the non-integer input
                System.out.println("Invalid input. Please enter a number (1-5).");
                continue; // skip to the next iteration
            }

            switch (input) {
                case 1:
                    RailFence.main(args);
                    break;
                case 2:
                    Vigenere.main(args);
                    break;
                case 3:
                    DES.main(args);
                    break;
                case 4:
                    RC4.main(args);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scan.close();
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }
}

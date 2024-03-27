import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("Menu: \n1 -- Railfence\n2 -- Vigenere\n3 -- Exit");
            System.out.print("Input: ");

            if (scan.hasNextInt()) {
                int input = scan.nextInt();

                switch (input) {
                    case 1:
                        RailFence railFence = new RailFence();
                        railFence.main(args);
                        break;
                    case 2:
                        Vigenere vigenere = new Vigenere();
                        vigenere.main(args);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid input. Please try again.");
                }
            } else {
                scan.nextLine();
                System.out.println("Invalid input. Please enter a number (1-3).");
            }
        }
    }
}
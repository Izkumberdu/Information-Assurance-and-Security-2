import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String res;
        String input;
        do {
            System.out.println("\nMenu: \nA -- Railfence\nB -- Vigenere");
            System.out.print("Input: ");
            input = scan.nextLine();

            switch (input) {
                case "a":
                    RailFence railFence = new RailFence();
                    railFence.main(args);
                    // main(args);
                    break;

                case "b":
                    Vigenere vigenere = new Vigenere();
                    vigenere.main(args);
                    // main(args);
                    break;
                default:
                    System.out.print("Invalid input.");
                    break;
            }
            System.out.print("\n\nDo you want to proceed to menu? [y/n] ");
            res = scan.nextLine();
        } while (res.equalsIgnoreCase("y"));
    }

}

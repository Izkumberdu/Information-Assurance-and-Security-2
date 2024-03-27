import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println("Menu: \n1 -- Railfence\n2 -- Vigenere");
        System.out.print("Input:");
        int input = scan.nextInt();

        switch (input) {
            case 1:
                RailFence railFence = new RailFence();
                RailFence.main(args);
                main(args);
                break;

            case 2:
                Vigenere vigenere = new Vigenere();
                vigenere.main(args);
                main(args);
                break;
            default:
                break;
        }
    }

}

import java.util.Scanner;

public class AES {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        menu(scan);

    }

    public static void menu(Scanner scan) {
        System.out.println("-- Advanced Encryption Standard -- \n1 -- Encrypt\n2 -- Decrypt\n3 -- Exit");
        System.out.print("Input: ");
        int input = scan.nextInt();

        switch (input) {
            case 1:
                Encrypt(scan);
                menu(scan);
                break;
            case 2:
                Decrypt(scan);
                menu(scan);
                break;
            case 3:

                break;

            default:
                System.out.println("Error, Please input the correct number");
                break;
        }
    }

    public static void Encrypt(Scanner scan) {
        System.out.println("Enter Text To encrypt:");
        String text = scan.next();

    }

    public static void Decrypt(Scanner scan) {

    }
}
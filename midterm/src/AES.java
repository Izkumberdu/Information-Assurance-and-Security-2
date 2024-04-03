import java.util.Scanner;

public class AES {
    private static final int BLOCK_SIZE = 16;

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
                encryptMenu(scan);
                break;
            case 2:
                decryptMenu(scan);
                break;
            case 3:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Error, Please input the correct number");
                break;
        }
    }

    public static void encryptMenu(Scanner scan) {
        System.out.println("Enter Text To encrypt:");
        String text = scan.next();
        System.out.println("Enter Encryption Key (16 bytes in hexadecimal format):");
        String key = scan.next();
        String encryptedText = encryptECB(text, key);
        System.out.println("Encrypted Text: " + encryptedText);
        menu(scan);
    }

    public static void decryptMenu(Scanner scan) {
        System.out.println("Enter Text To decrypt:");
        String text = scan.next();
        System.out.println("Enter Decryption Key (16 bytes in hexadecimal format):");
        String key = scan.next();
        String decryptedText = decryptECB(text, key);
        System.out.println("Decrypted Text: " + decryptedText);
        menu(scan);
    }

    private static String encryptECB(String input, String key) {

        return input;
    }

    private static String decryptECB(String input, String key) {

        return input;
    }
}

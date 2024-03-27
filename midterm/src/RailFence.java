import java.util.Scanner;

public class RailFence {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Menu(scan);
    }

    public static void Menu(Scanner scan) {
        System.out.println("-- RailFence Cipher -- \n1 -- Encrypt\n2 -- Decrypt\n3 -- Exit");
        System.out.print("Input: ");
        int input = scan.nextInt();

        switch (input) {
            case 1:
                Encrypt(scan);
                Menu(scan);
                break;
            case 2:
                Decrypt(scan);
                Menu(scan);
                break;
            case 3:
                break;
            default:
                System.out.println("Error, Please input the correct number");
                Menu(scan);
                break;
        }
    }

    public static void Encrypt(Scanner scan) {
        System.out.print("Enter Text to Encrypt: ");
        String plaintext = scan.next();

        System.out.print("Enter Key: ");
        int key = scan.nextInt();

        String encryptedMessage = encryptProcess(plaintext, key);
        System.out.println("Encrypted Message: " + encryptedMessage);
    }

    public static void Decrypt(Scanner scan) {
        System.out.print("Enter Text to Decrypt: ");
        String encryptedText = scan.next();

        System.out.print("Enter Key: ");
        int key = scan.nextInt();

        String decryptedMessage = decryptProcess(encryptedText, key);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }

    static String encryptProcess(String text, int key) {
        String encryptMsg = "";
        boolean dir = false;
        int row = key;
        int col = text.length();
        int index = 0;
        char[][] rail = new char[row][col];

        for (int i = 0; i < col; i++) {
            if (index == 0 || index == key - 1)
                dir = !dir;

            rail[index][i] = text.charAt(i);
            if (dir)
                index++;
            else
                index--;
        }

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (rail[r][c] != 0) // Check if the cell is not empty
                    encryptMsg += rail[r][c];
            }
        }

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                System.out.print(rail[r][c] + " ");
            }
            System.out.println();
        }

        return encryptMsg;
    }

    static String decryptProcess(String text, int key) {
        String decryptMsg = "";
        boolean dir = false;
        int row = key;
        int col = text.length();
        int index = 0;
        char[][] rail = new char[row][col];

        for (int i = 0; i < col; i++) {
            if (index == 0 || index == key - 1)
                dir = !dir;

            rail[index][i] = '*';
            if (dir)
                index++;
            else
                index--;
        }

        int num = 0;
        dir = false;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (rail[r][c] == '*' && num < col) {
                    rail[r][c] = text.charAt(num++);
                }
            }
        }

        index = 0;
        for (int i = 0; i < col; i++) {
            if (index == 0 || index == key - 1)
                dir = !dir;

            decryptMsg += rail[index][i];
            if (dir)
                index++;
            else
                index--;
        }

        return decryptMsg;
    }
}
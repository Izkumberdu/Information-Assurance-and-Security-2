import java.util.Scanner;

public class RailFence {
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.print("Enter Text: ");
            String plaintext = scan.nextLine();

            System.out.print("Enter key: ");
            int key = scan.nextInt();

            String encryptMsg = encryptProcess(plaintext, key);
            System.out.print("Encrpted Message: " + encryptMsg);
            System.out.println("");

            String decryptMsg = decryptProcess(encryptMsg, key);
            System.out.print("Decrypted Message: " + decryptMsg);
        }
    }

    public static String encryptProcess(String text, int key) {
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

    public static String decryptProcess(String text, int key) {
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
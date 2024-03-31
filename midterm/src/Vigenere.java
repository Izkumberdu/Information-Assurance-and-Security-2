import java.util.Scanner;

public class Vigenere {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Menu(scan);
    }

    private static void Menu(Scanner scan) {
        System.out.println("-- Vigenere Cipher -- \n1 -- Encrypt\n2 -- Decrypt\n3 -- Exit");
        System.out.print("Input: ");
        int input = scan.nextInt();

        switch (input) {
            case 1:
                Encrypt(scan);
                Menu(scan);
                break;
            case 2:
                decrypt(scan);
                Menu(scan);
                break;
            case 3:
                break;

            default:
                System.out.println("Error, Please input the correct number");
                break;
        }
    }

    private static void Encrypt(Scanner scan) {
        System.out.print("Input Text to Encrypt: ");
        String text = scan.next().toUpperCase();
        text.toCharArray();
        System.out.println(text);

        System.out.print("Input key of the Encryption: ");
        String key = scan.next();

        if (text.length() != key.length()) {
            key = KeyRepeat(key, text);
        }
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            char keyChar = key.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                char encryptedChar = (char) ((currentChar + keyChar - 2 * 'A') % 26 + 'A');
                encryptedMessage.append(encryptedChar);
            } else {
                encryptedMessage.append(currentChar);
            }
        }
        System.out.println("Encrypted Message: " + encryptedMessage);
    }

    private static void decrypt(Scanner scan) {
        System.out.print("Input Text to Decrypt: ");
        String text = scan.next().toUpperCase();

        System.out.print("Input key of the Decryption: ");
        String key = scan.next().toUpperCase();

        if (text.length() != key.length()) {
            key = KeyRepeat(key, text);
        }
        StringBuilder decryptedMessage = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            char keyChar = key.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                char decryptedChar = (char) ((currentChar - keyChar + 2 * 'A') % 26 + 'A');
                decryptedMessage.append(decryptedChar);
            } else {
                decryptedMessage.append(currentChar);
            }
        }

        System.out.println("Encrypted Message: " + decryptedMessage);
    }

    private static String removedWhiteSpace(String text) {
        String editedText = text.replaceAll("\\s", "");
        System.out.println(editedText);
        return editedText;
    }

    private static String KeyRepeat(String key, String text) {
        StringBuilder repeatedKey = new StringBuilder();
        int keyIndex = 0;

        while (repeatedKey.length() < text.length()) {
            repeatedKey.append(key.charAt(keyIndex));
            keyIndex = (keyIndex + 1) % key.length();
        }
        return repeatedKey.toString().toUpperCase();
    }
}

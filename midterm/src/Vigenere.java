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
        scan.nextLine();
        String inputText = scan.nextLine();
        inputText = inputText.toUpperCase();
        char[] text = inputText.toCharArray();
        StringBuilder textWithoutWhitespace = new StringBuilder();

        // Loop to iterate over each character and skip whitespaces
        for (char c : text) {
            if (!Character.isWhitespace(c)) {
                textWithoutWhitespace.append(c);
            }
        }

        String textString = textWithoutWhitespace.toString();
        System.out.println("Text without Whitespace: " + textString);

        System.out.print("Input key of the Encryption: ");
        String key = scan.next();

        if (textString.length() != key.length()) {
            key = KeyRepeat(key, textString);
        }

        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < textString.length(); i++) {
            char currentChar = textString.charAt(i);
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

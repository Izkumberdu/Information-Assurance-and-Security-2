import java.math.BigInteger;
import java.util.Scanner;

public class DES {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("Choose an option:");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1, 2, or 3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    encryptMessage(scanner);
                    break;
                case 2:
                    decryptMessage(scanner);
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose 1, 2, or 3.\n");
            }
        }

        scanner.close();
    }

    private static void encryptMessage(Scanner scanner) {
        System.out.print("Enter Message to Encrypt (in hexadecimal): ");
        String messageHex = scanner.nextLine();

        System.out.print("Enter Key (8 characters, in hexadecimal): ");
        String keyHex = scanner.nextLine();

        if (keyHex.length() != 16) {
            System.out.println("Key must be 8 characters long in hexadecimal format.");
            return;
        }

        String encryptedHex = desEncrypt(messageHex, keyHex);

        System.out.println("Encrypted Message (in hexadecimal): " + encryptedHex + "\n");
    }

    private static void decryptMessage(Scanner scanner) {
        System.out.print("Enter Message to Decrypt (in hexadecimal): ");
        String encryptedMessageHex = scanner.nextLine();

        System.out.print("Enter Key (8 characters, in hexadecimal): ");
        String keyHex = scanner.nextLine();

        if (keyHex.length() != 16) {
            System.out.println("Key must be 8 characters long in hexadecimal format.");
            return;
        }

        String decryptedHex = desDecrypt(encryptedMessageHex, keyHex);

        System.out.println("Decrypted Message (in hexadecimal): " + decryptedHex + "\n");
    }

    private static String desEncrypt(String messageHex, String keyHex) {
        // Convert hexadecimal message and key to binary
        String messageBinary = hexToBinary(messageHex);
        String keyBinary = hexToBinary(keyHex);

        // Perform initial permutation on the binary message
        String permutedMessage = initialPermutation(messageBinary);

        // Generate subkeys for each round using the key
        String[] subkeys = generateSubkeys(keyBinary);

        // Perform multiple rounds of substitution and permutation using the generated subkeys
        String encryptedMessage = desRoundFunction(permutedMessage, subkeys);

        // Perform final permutation on the result
        encryptedMessage = finalPermutation(encryptedMessage);

        // Convert the final binary result back to hexadecimal and return it
        return binaryToHex(encryptedMessage);
    }

    private static String desDecrypt(String encryptedMessageHex, String keyHex) {
        // Convert hexadecimal encrypted message and key to binary
        String encryptedMessageBinary = hexToBinary(encryptedMessageHex);
        String keyBinary = hexToBinary(keyHex);

        // Generate subkeys for each round using the key
        String[] subkeys = generateSubkeys(keyBinary);

        // Perform multiple rounds of decryption using the generated subkeys (in reverse order of encryption)
        String decryptedMessage = desRoundFunction(encryptedMessageBinary, reverseSubkeys(subkeys));

        // Perform final permutation on the result
        decryptedMessage = finalPermutation(decryptedMessage);

        // Convert the final binary result back to hexadecimal and return it
        return binaryToHex(decryptedMessage);
    }

    // Helper methods for conversion, permutation, and subkey generation

    private static String hexToBinary(String hex) {
        String binary = "";
        for(int i = 0; i < hex.length(); i++) {
            int decimal = Integer.parseInt(String.valueOf(hex.charAt(i)), 16);
            String bin = Integer.toBinaryString(decimal);
            while (bin.length() < 4) {
                bin = "0" + bin;
            }
            binary += bin;
        }
        // Ensure the binary string is 64 bits long
        while (binary.length() < 64) {
            binary = "0" + binary;
        }
        return binary;
    }
    
    private static String binaryToHex(String binary) {
        String hex = "";
        for (int i = 0; i < binary.length(); i += 4) {
            int decimal = Integer.parseInt(binary.substring(i, i + 4), 2);
            hex += Integer.toHexString(decimal);
        }
        return hex.toUpperCase();
    }
    

    private static String initialPermutation(String message) {
        int[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
        };
        return permute(message, IP);
    }
    
    private static String finalPermutation(String message) {
        int[] FP = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
        };
        return permute(message, FP);
    }
    
    private static String permute(String input, int[] permutationTable) {
        // Assume that the input string is a binary string of 64 or 56 bits as necessary
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < permutationTable.length; i++) {
            output.append(input.charAt(permutationTable[i] - 1));
        }
        return output.toString();
    }
    

    private static String expand(String right, int[] expansionTable) {
        StringBuilder expanded = new StringBuilder();
        for (int i = 0; i < expansionTable.length; i++) {
            expanded.append(right.charAt(expansionTable[i]));
        }
        return expanded.toString();
    }
    
    
    private static String xor(String a, String b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            sb.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }
        return sb.toString();
    }

    private static String substitute(String input) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            String sixBits = input.substring(i * 6, (i + 1) * 6);
            int row = Integer.parseInt("" + sixBits.charAt(0) + sixBits.charAt(5), 2);
            int col = Integer.parseInt(sixBits.substring(1, 5), 2);
            int val = sBoxes[i][row][col];
            output.append(String.format("%4s", Integer.toBinaryString(val)).replace(' ', '0'));
        }
        return output.toString();
    }
    
    private static int[] shifts = {
        1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 // Shift schedule for the 16 rounds
    };

    private static int[] PC1 = {
        57, 49, 41, 33, 25, 17, 9,
        1, 58, 50, 42, 34, 26, 18,
        10, 2, 59, 51, 43, 35, 27,
        19, 11, 3, 60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
        7, 62, 54, 46, 38, 30, 22,
        14, 6, 61, 53, 45, 37, 29,
        21, 13, 5, 28, 20, 12, 4
    };
    
    private static int[] PC2 = {
        14, 17, 11, 24, 1, 5, 3, 28,
        15, 6, 21, 10, 23, 19, 12, 4,
        26, 8, 16, 7, 27, 20, 13, 2,
        41, 52, 31, 37, 47, 55, 30, 40,
        51, 45, 33, 48, 44, 49, 39, 56,
        34, 53, 46, 42, 50, 36, 29, 32
    };

    private static int[] expansionTable = {
        31, 0, 1, 2, 3, 4, 3, 4,
        5, 6, 7, 8, 7, 8, 9, 10,
        11, 12, 11, 12, 13, 14, 15, 16,
        15, 16, 17, 18, 19, 20, 19, 20,
        21, 22, 23, 24, 23, 24, 25, 26,
        27, 28, 27, 28, 29, 30, 31, 0
    };
    
    
        
    private static String[] generateSubkeys(String key) {
        String keyBinary = hexToBinary(key);
        
        // Apply PC-1 to get the 56-bit permuted key
        String permutedKey = permute(keyBinary, PC1);
        
        // Split the permuted key into two 28-bit halves
        String left = permutedKey.substring(0, 28);
        String right = permutedKey.substring(28, 56);
        
        String[] subkeys = new String[16];
        
        // Generate 16 subkeys
        for (int i = 0; i < 16; i++) {
            left = rotateLeft(left, shifts[i]);
            right = rotateLeft(right, shifts[i]);
            
            // Combine the halves and apply PC-2 to get the 48-bit subkey
            String combinedKey = left + right;
            subkeys[i] = permute(combinedKey, PC2);
        }
        
        return subkeys;
    }
     
    private static String rotateLeft(String input, int numBits) {
        int n = input.length();
        int rotations = numBits % n;
        return input.substring(rotations) + input.substring(0, rotations);
    }

    private static String desRoundFunction(String message, String[] subkeys) {
        for (int i = 0; i < 16; i++) {
            String rightExpanded = expand(message.substring(32)/* expansion table */);
            String xorResult = xor(rightExpanded, subkeys[i]);
            String substituted = substitute(xorResult /* using S-boxes */);
            String permuted = permute(substituted, P);
            message = xor(message.substring(0, 32), permuted) + message.substring(32);
            // Swap halves for all rounds except the last
            if (i < 15) {
                message = message.substring(32) + message.substring(0, 32);
            }
        }
        return message;
    }
    
    private static String[] reverseSubkeys(String[] subkeys) {
        String[] reversed = new String[subkeys.length];
        for (int i = 0; i < subkeys.length; i++) {
            reversed[i] = subkeys[subkeys.length - 1 - i];
        }
        return reversed;
    }
    
}
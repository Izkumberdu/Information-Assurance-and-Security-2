// import java.util.Arrays;
import java.util.Scanner;

public class DES {

    // Initial Permutation Table
    static final int[] initialPermutation = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    // Final Permutation Table (Inverse of Initial Permutation)
    static final int[] finalPermutation = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    // Expansion Permutation Table
    static final int[] expansionTable = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };

    // S-Box Tables (additional S-Box tables)
    static final int[][][] sbox = {
            {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13},
            },
            {
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9},
            },
            {
                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12},
            },
            {
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14},
            },
            {
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3},
            },
            {
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13},
            },
            {
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12},
            },
            {
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11},
            }
    };

    // Initial Permutation for keys (key permu)
    static final int[] pc1 = {
            57, 49, 41, 33, 25, 17, 9, 1,
            58, 50, 42, 34, 26, 18, 10, 2,
            59, 51, 43, 35, 27, 19, 11, 3,
            60, 52, 44, 36, 63, 55, 47, 39,
            31, 23, 15, 7, 62, 54, 46, 38,
            30, 22, 14, 6, 61, 53, 45, 37,
            29, 21, 13, 5, 28, 20, 12, 4
    };

    // Key Compression Permutation Table (key comp)
    static final int[] pc2 = {
            14, 17, 11, 24, 1, 5, 3, 28,
            15, 6, 21, 10, 23, 19, 12, 4,
            26, 8, 16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55, 30, 40,
            51, 45, 33, 48, 44, 49, 39, 56,
            34, 53, 46, 42, 50, 36, 29, 32
    };

    // Left Shifts for key schedule
    static final int[] shiftBits = {
            1, 1, 2, 2, 2, 2, 2, 2,
            1, 2, 2, 2, 2, 2, 2, 1
    };

    // permutation after sbox (plaintext)
    static final int[] permutationAfterSbox = {
            16, 7, 20, 21, 29, 12, 28, 17,
            1, 15, 23, 26, 5, 18, 31, 10,
            2, 8, 24, 14, 32, 27, 3, 9,
            19, 13, 30, 6, 22, 11, 4, 25
    };

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("1. Encrypt");
        System.out.println("2. Decrypt");
        System.out.print("Choose an option: ");
        int option = scan.nextInt();
        scan.nextLine();

        switch (option) {
            case 1:
                encryptMessage(scan);
                break;
            case 2:
                decryptMessage(scan);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    public static void encryptMessage(Scanner scan) {
        System.out.print("\nEnter message to Encrypt: ");
        String plaintext = scan.nextLine();
        System.out.print("Enter hexadecimal key: ");
        String keyHex = scan.nextLine();

        // Convert hexadecimal key to binary
        String keyBinary = hexToBin(keyHex);

        System.out.println("\nMessage to Encrypt: " + plaintext);
        String ciphertext = encrypt(plaintext, keyBinary);
        System.out.println("Encrypted: " + ciphertext);
    }

    public static void decryptMessage(Scanner scan) {
        System.out.print("\nEnter ciphertext to Decrypt: ");
        String ciphertext = scan.nextLine();
        System.out.print("Enter hexadecimal key: ");
        String keyHex = scan.nextLine();

        // Convert hexadecimal key to binary
        String keyBinary = hexToBin(keyHex);

        String decryptedText = decrypt(ciphertext, keyBinary);
        System.out.println("Decrypted: " + decryptedText);
    }

    public static String hexToBin(String hexString) {
        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < hexString.length(); i++) {
            char hexChar = hexString.charAt(i);
            String binaryChar = String.format("%4s", Integer.toBinaryString(Character.digit(hexChar, 16))).replace(' ', '0');
            binaryString.append(binaryChar);
        }
        return binaryString.toString();
    }


    public static String binToHex(String binString) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < binString.length(); i += 4) {
            String binaryChar = binString.substring(i, i + 4);
            int decimalValue = Integer.parseInt(binaryChar, 2);
            hexString.append(Integer.toHexString(decimalValue));
        }
        return hexString.toString().toUpperCase(); // Convert to uppercase for consistency
    }


    public static String hexToText(String hexString) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hexString.length(); i += 2) {
            String str = hexString.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    public static String permute(String block, int[] table) {
        StringBuilder permutedBlock = new StringBuilder();
        for (int i : table) {
            permutedBlock.append(block.charAt(i - 1));
        }
        return permutedBlock.toString();
    }

    public static String substitute(String block) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int row = Integer.parseInt(block.substring(i * 6, i * 6 + 1) + block.substring(i * 6 + 5, i * 6 + 6), 2);
            int col = Integer.parseInt(block.substring(i * 6 + 1, i * 6 + 5), 2);
            output.append(String.format("%04d", Integer.parseInt(Integer.toBinaryString(sbox[i][row][col]))));
        }
        return output.toString();
    }

    public static String xor(String bin1, String bin2) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < bin1.length(); i++) {
            output.append(bin1.charAt(i) == bin2.charAt(i) ? "0" : "1");
        }
        return output.toString();
    }

    public static String shiftLeft(String block, int n) {
        return block.substring(n) + block.substring(0, n);
    }

    public static String[] generateKeys(String key) {
        // Ensure key length is exactly 8 characters (64 bits)
        key = String.format("%-8s", key).substring(0, 8);
    
        // Convert each character to its binary representation
        StringBuilder binaryKey = new StringBuilder();
        for (char c : key.toCharArray()) {
            binaryKey.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
    
        String[] keys = new String[16];
        String permutedKey = permute(binaryKey.toString(), pc1);
        String[] halves = {permutedKey.substring(0, 28), permutedKey.substring(28)};
        for (int i = 0; i < 16; i++) {
            halves[0] = shiftLeft(halves[0], shiftBits[i]);
            halves[1] = shiftLeft(halves[1], shiftBits[i]);
            String subKey = permute(halves[0] + halves[1], pc2);
            keys[i] = subKey;
        }
        return keys;
    }
    

    public static String encrypt(String plaintext, String key) {
        String[] keys = generateKeys(key);
        StringBuilder ciphertext = new StringBuilder();
    
        // Convert plaintext to binary
        StringBuilder plaintextBinary = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            plaintextBinary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
    
        // Pad plaintext to a multiple of 64 bits
        int paddingLength = 64 - (plaintextBinary.length() % 64);
        plaintextBinary.append("0".repeat(paddingLength));
    
        // Encrypt each 64-bit block
        for (int i = 0; i < plaintextBinary.length(); i += 64) {
            String block = plaintextBinary.substring(i, i + 64);
            String encryptedBlock = desEncryptBlock(block, keys);
            // Convert encrypted block to hexadecimal and append to ciphertext
            ciphertext.append(String.format("%016X", Long.parseUnsignedLong(encryptedBlock, 2)));
        }
        return ciphertext.toString();
    }
    

    public static String decrypt(String ciphertextHex, String key) {
        String[] keys = generateKeys(key);
        StringBuilder plaintextBinary = new StringBuilder();
        String ciphertextBinary = "";
        for (int i = 0; i < ciphertextHex.length(); i += 16) {
            int endIndex = Math.min(i + 16, ciphertextHex.length());
            String hexSubstring = ciphertextHex.substring(i, endIndex);
            ciphertextBinary += String.format("%64s", Long.toBinaryString(Long.parseUnsignedLong(hexSubstring, 16))).replace(' ', '0');
        }
        for (int i = 0; i < ciphertextBinary.length(); i += 64) {
            String block = ciphertextBinary.substring(i, i + 64);
            String decryptedBlock = desDecryptBlock(block, keys);
            plaintextBinary.append(decryptedBlock);
        }
        String plaintextHex = binToHex(plaintextBinary.toString());
        return hexToText(plaintextHex);
    }
    
    

    public static String desEncryptBlock(String block, String[] keys) {
        block = permute(block, initialPermutation);
        String[] halves = {block.substring(0, 32), block.substring(32)};
        for (String key : keys) {
            String expanded = permute(halves[1], expansionTable);
            String xored = xor(expanded, key);
            String substituted = substitute(xored);
            String permuted = permute(substituted, permutationAfterSbox);
            String newRight = xor(halves[0], permuted);
            halves[0] = halves[1];
            halves[1] = newRight;
        }
        return permute(halves[1] + halves[0], finalPermutation);
    }

    public static String desDecryptBlock(String block, String[] keys) {
        block = permute(block, initialPermutation);
        String[] halves = {block.substring(0, 32), block.substring(32)};
        for (int i = keys.length - 1; i >= 0; i--) {
            String expanded = permute(halves[1], expansionTable);
            String xored = xor(expanded, keys[i]);
            String substituted = substitute(xored);
            String permuted = permute(substituted, permutationAfterSbox);
            String newRight = xor(halves[0], permuted);
            halves[0] = halves[1];
            halves[1] = newRight;
        }
        return permute(halves[1] + halves[0], finalPermutation);
    }
}

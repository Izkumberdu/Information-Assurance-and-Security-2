import java.util.Scanner;

public class AES {
        private static final int MAX_COL_SIZE = 4;
        private static final int MAX_ROW_SIZE = 4;

        private static final int[][] S_BOX = {
                        { 0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab,
                                        0x76 },
                        { 0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72,
                                        0xc0 },
                        { 0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31,
                                        0x15 },
                        { 0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2,
                                        0x75 },
                        { 0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f,
                                        0x84 },
                        { 0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58,
                                        0xcf },
                        { 0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f,
                                        0xa8 },
                        { 0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3,
                                        0xd2 },
                        { 0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19,
                                        0x73 },
                        { 0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b,
                                        0xdb },
                        { 0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4,
                                        0x79 },
                        { 0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae,
                                        0x08 },
                        { 0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b,
                                        0x8a },
                        { 0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d,
                                        0x9e },
                        { 0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28,
                                        0xdf },
                        { 0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb,
                                        0x16 }
        };
        private static final int[][] INVERSE_S_BOX = {
                        { 0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7,
                                        0xfb },
                        { 0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9,
                                        0xcb },
                        { 0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3,
                                        0x4e },
                        { 0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1,
                                        0x25 },
                        { 0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6,
                                        0x92 },
                        { 0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d,
                                        0x84 },
                        { 0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45,
                                        0x06 },
                        { 0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a,
                                        0x6b },
                        { 0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6,
                                        0x73 },
                        { 0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf,
                                        0x6e },
                        { 0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe,
                                        0x1b },
                        { 0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a,
                                        0xf4 },
                        { 0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec,
                                        0x5f },
                        { 0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c,
                                        0xef },
                        { 0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99,
                                        0x61 },
                        { 0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c,
                                        0x7d }
        };

        private static final int[] RCON = {
                        0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36
        };

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
                System.out.println("Enter Text To Encrypt:");
                // String text = "Two One Nine Two";
                String text = scan.next();
                System.out.println("Enter Decryption Key:");
                String key = scan.next();

                // String key = "Thats my Kung Fu";
                String encryptedText = encryptECB(text, key);
                System.out.println("Encrypted Text: " + encryptedText);
                menu(scan);
        }

        public static void decryptMenu(Scanner scan) {
                System.out.println("Enter Text To decrypt:");
                // String text = "29c3505f571420f6402299b31a02d73a";
                String text = scan.next();

                System.out.println("Enter Decryption Key:");
                // String key = "Thats my Kung Fu";
                String key = scan.next();
                String decryptedText = decryptECB(text, key);

                System.out.println("Decrypted Text: " + decryptedText);
                menu(scan);
        }

        private static String encryptECB(String text, String key) {

                String[][] initialState = convertToTable(text);
                String[][] roundKeys = keyExpansion(key);

                // Initial round: Add round key
                String[][] state = addRoundKey(initialState, convertToTable(key));

                // Main rounds: SubBytes, ShiftRows, MixColumns, AddRoundKey
                for (int round = 1; round <= 10; round++) {
                        state = subBytes(state);
                        state = shiftRows(state);
                        if (round < 10) {
                                state = mixColumns(state);
                        }
                        state = addRoundKey(state, getRoundKey(round, roundKeys));
                }

                // Convert state matrix back to text
                StringBuilder encryptedText = new StringBuilder();
                for (int col = 0; col < MAX_COL_SIZE; col++) {
                        for (int row = 0; row < MAX_ROW_SIZE; row++) {
                                encryptedText.append(state[row][col]);
                        }
                }

                return encryptedText.toString();
        }

        private static String[][] getRoundKey(int round, String[][] roundKeys) {
                String[][] roundKey = new String[4][4];
                int startCol = round * 4;

                for (int col = 0; col < 4; col++) {
                        for (int row = 0; row < 4; row++) {
                                roundKey[row][col] = roundKeys[row][startCol + col];
                        }
                }

                return roundKey;
        }

        private static String[][] subBytes(String[][] state) {
                String[][] subByte = new String[4][4];

                for (int row = 0; row < 4; row++) {
                        for (int col = 0; col < 4; col++) {
                                String hexValue = state[row][col];
                                int rowIndex = Integer.parseInt(hexValue.substring(0, 1), 16);
                                int colIndex = Integer.parseInt(hexValue.substring(1), 16);
                                subByte[row][col] = String.format("%02x", S_BOX[rowIndex][colIndex]);
                        }
                }

                return subByte;
        }

        private static String[][] shiftRows(String[][] state) {
                String[][] shiftedState = new String[4][4];

                for (int col = 0; col < 4; col++) {
                        shiftedState[0][col] = state[0][col];
                }

                for (int col = 0; col < 4; col++) {
                        shiftedState[1][col] = state[1][(col + 1) % 4];
                }

                for (int col = 0; col < 4; col++) {
                        shiftedState[2][col] = state[2][(col + 2) % 4];
                }

                for (int col = 0; col < 4; col++) {
                        shiftedState[3][col] = state[3][(col + 3) % 4];
                }

                return shiftedState;
        }

        private static String[][] addRoundKey(String[][] initialState, String[][] keyState) {
                String[][] newState = new String[4][4];

                for (int col = 0; col < 4; col++) {
                        for (int row = 0; row < 4; row++) {
                                int initialStateValue = Integer.parseInt(initialState[row][col], 16);
                                int keyStateValue = Integer.parseInt(keyState[row][col], 16);
                                int result = initialStateValue ^ keyStateValue;
                                newState[row][col] = String.format("%02x", result);
                        }
                }

                return newState;
        }

        private static String[][] convertToTable(String text) {
                byte[] bytes = text.getBytes();
                String[][] hexArray = new String[MAX_ROW_SIZE][MAX_COL_SIZE];
                int byteIndex = 0;

                for (int col = 0; col < MAX_COL_SIZE; col++) {
                        for (int row = 0; row < MAX_ROW_SIZE; row++) {
                                if (byteIndex >= bytes.length) {
                                        hexArray[row][col] = "00";
                                } else {
                                        String hex = Integer.toHexString(bytes[byteIndex] & 0xff);
                                        if (hex.length() == 1) {
                                                hex = "0" + hex;
                                        }
                                        hexArray[row][col] = hex;
                                        byteIndex++;
                                }
                        }
                }
                return hexArray;
        }

        private static String[][] mixColumns(String[][] state) {
                String[][] mixedState = new String[4][4];

                for (int col = 0; col < 4; col++) {
                        int a0 = Integer.parseInt(state[0][col], 16);
                        int a1 = Integer.parseInt(state[1][col], 16);
                        int a2 = Integer.parseInt(state[2][col], 16);
                        int a3 = Integer.parseInt(state[3][col], 16);

                        mixedState[0][col] = Integer
                                        .toHexString(mixColumnCell(0x02, a0) ^ mixColumnCell(0x03, a1) ^ a2 ^ a3);
                        mixedState[1][col] = Integer
                                        .toHexString(a0 ^ mixColumnCell(0x02, a1) ^ mixColumnCell(0x03, a2) ^ a3);
                        mixedState[2][col] = Integer
                                        .toHexString(a0 ^ a1 ^ mixColumnCell(0x02, a2) ^ mixColumnCell(0x03, a3));
                        mixedState[3][col] = Integer
                                        .toHexString(mixColumnCell(0x03, a0) ^ a1 ^ a2 ^ mixColumnCell(0x02, a3));
                }

                return mixedState;
        }

        private static int mixColumnCell(int multiplier, int value) {
                int result;
                if (multiplier == 0x02) {
                        result = (value << 1) ^ ((value & 0x80) != 0 ? 0x1B : 0);
                } else {
                        result = (value << 1) ^ ((value & 0x80) != 0 ? 0x1B : 0) ^ value;
                }
                return result & 0xFF;
        }

        private static String[][] keyExpansion(String key) {
                String[][] expandedKey = new String[4][44]; // 4x44 matrix for 11 round keys

                // Copy the original key into the first 4 columns of the expanded key
                String[][] keyState = convertToTable(key);
                for (int col = 0; col < 4; col++) {
                        for (int row = 0; row < 4; row++) {
                                expandedKey[row][col] = keyState[row][col];
                        }
                }

                // Generate the remaining round keys
                for (int col = 4; col < 44; col++) {
                        if (col % 4 == 0) {
                                // Perform key schedule core for words at multiples of Nk
                                String[] temp = new String[4];
                                for (int row = 0; row < 4; row++) {
                                        temp[row] = expandedKey[row][col - 1];
                                }
                                temp = keyScheduleCore(temp, col / 4);
                                for (int row = 0; row < 4; row++) {
                                        expandedKey[row][col] = xorHex(expandedKey[row][col - 4], temp[row]);
                                }
                        } else {
                                // XOR the word at (col - 4) with the word at (col - Nk)
                                for (int row = 0; row < 4; row++) {
                                        expandedKey[row][col] = xorHex(expandedKey[row][col - 4],
                                                        expandedKey[row][col - 1]);
                                }
                        }
                }

                return expandedKey;
        }

        private static String[] keyScheduleCore(String[] word, int round) {
                // Rotate the word
                String[] rotatedWord = new String[word.length];
                for (int i = 0; i < word.length; i++) {
                        rotatedWord[i] = word[(i + 1) % word.length];
                }

                // Substitute each byte in the word
                for (int i = 0; i < rotatedWord.length; i++) {
                        int rowIndex = Integer.parseInt(rotatedWord[i].substring(0, 1), 16);
                        int colIndex = Integer.parseInt(rotatedWord[i].substring(1), 16);
                        rotatedWord[i] = String.format("%02x", S_BOX[rowIndex][colIndex]);
                }

                // XOR the first byte of the word with RCON
                rotatedWord[0] = xorHex(rotatedWord[0], Integer.toHexString(RCON[round - 1]));

                return rotatedWord;
        }

        private static String xorHex(String a, String b) {
                int numA = Integer.parseInt(a, 16);
                int numB = Integer.parseInt(b, 16);
                int result = numA ^ numB;
                return String.format("%02x", result);
        }

        private static String decryptECB(String text, String key) {
                String[][] initialState = convertHexToStringToTable(text);

                String[][] roundKeys = keyExpansion(key);

                // Initial round: Add round key
                String[][] state = addRoundKey(initialState, convertToTable(key));
                for (int row = 0; row < 4; row++) {
                        for (int col = 0; col < 4; col++) {
                                System.out.print(state[row][col] + " ");
                        }
                        System.out.println(); // Move to the next line after printing each row
                }
                System.out.println(' ');

                for (int round = 1; round <= 10; round++) {
                        System.out.println(round + 10);
                        System.out.println(' ');
                        state = invShiftRows(state);
                        for (int row = 0; row < 4; row++) {
                                for (int col = 0; col < 4; col++) {
                                        System.out.print(state[row][col] + " ");
                                }
                                System.out.println(); // Move to the next line after printing each row
                        }
                        System.out.println(' ');
                        state = invSubBytes(state);
                        for (int row = 0; row < 4; row++) {
                                for (int col = 0; col < 4; col++) {
                                        System.out.print(state[row][col] + " ");
                                }
                                System.out.println(); // Move to the next line after printing each row
                        }

                        System.out.println(' ');
                        state = addRoundKey(state, getRoundKey(round, roundKeys));
                        for (int row = 0; row < 4; row++) {
                                for (int col = 0; col < 4; col++) {
                                        System.out.print(state[row][col] + " ");
                                }
                                System.out.println(); // Move to the next line after printing each row
                        }
                        System.out.println(' ');
                        if (round < 10) {
                                state = invMixColumns(state);
                        }
                        for (int row = 0; row < 4; row++) {
                                for (int col = 0; col < 4; col++) {
                                        System.out.print(state[row][col] + " ");
                                }
                                System.out.println(); // Move to the next line after printing each row
                        }
                }

                // Convert state matrix back to text
                StringBuilder decryptedText = new StringBuilder();
                for (int col = 0; col < MAX_COL_SIZE; col++) {
                        for (int row = 0; row < MAX_ROW_SIZE; row++) {
                                int asciiValue = Integer.parseInt(state[row][col], 16);
                                decryptedText.append((char) asciiValue);
                        }
                }

                return decryptedText.toString();
        }

        private static String[][] convertHexToStringToTable(String key) {

                String[][] keyState = new String[4][4];

                byte[] keyBytes = new byte[key.length() / 2];
                for (int i = 0; i < key.length(); i += 2) {
                        keyBytes[i / 2] = (byte) ((Character.digit(key.charAt(i), 16) << 4)
                                        + Character.digit(key.charAt(i + 1), 16));
                }

                int byteIndex = 0;
                for (int col = 0; col < 4; col++) {
                        for (int row = 0; row < 4; row++) {

                                String hex = Integer.toHexString(keyBytes[byteIndex] & 0xFF);

                                if (hex.length() == 1) {
                                        hex = "0" + hex;
                                }
                                keyState[row][col] = hex;
                                byteIndex++;
                        }
                }
                return keyState;
        }

        private static String[][] invShiftRows(String[][] state) {
                String[][] shiftedState = new String[4][4];

                for (int col = 0; col < 4; col++) {
                        shiftedState[0][col] = state[0][col];
                }

                for (int col = 0; col < 4; col++) {
                        shiftedState[1][col] = state[1][(col + 3) % 4];
                }

                for (int col = 0; col < 4; col++) {
                        shiftedState[2][col] = state[2][(col + 2) % 4];
                }

                for (int col = 0; col < 4; col++) {
                        shiftedState[3][col] = state[3][(col + 1) % 4];
                }

                return shiftedState;
        }

        private static String[][] invSubBytes(String[][] state) {
                String[][] subByte = new String[4][4];

                for (int row = 0; row < 4; row++) {
                        for (int col = 0; col < 4; col++) {
                                String hexValue = state[row][col];// Debug output
                                int rowIndex = Integer.parseInt(hexValue.substring(0, 1), 16);
                                int colIndex = Integer.parseInt(hexValue.substring(1), 16);
                                subByte[row][col] = String.format("%02x", INVERSE_S_BOX[rowIndex][colIndex]);
                        }
                }

                return subByte;
        }

        private static String[][] invMixColumns(String[][] state) {
                String[][] mixedState = new String[4][4];

                for (int col = 0; col < 4; col++) {
                        int s0 = Integer.parseInt(state[0][col], 16);
                        int s1 = Integer.parseInt(state[1][col], 16);
                        int s2 = Integer.parseInt(state[2][col], 16);
                        int s3 = Integer.parseInt(state[3][col], 16);

                        mixedState[0][col] = toHexString(invMixColumnCell(0x0e, s0) ^ invMixColumnCell(0x0b, s1)
                                        ^ invMixColumnCell(0x0d, s2) ^ invMixColumnCell(0x09, s3));
                        mixedState[1][col] = toHexString(invMixColumnCell(0x09, s0) ^ invMixColumnCell(0x0e, s1)
                                        ^ invMixColumnCell(0x0b, s2) ^ invMixColumnCell(0x0d, s3));
                        mixedState[2][col] = toHexString(invMixColumnCell(0x0d, s0) ^ invMixColumnCell(0x09, s1)
                                        ^ invMixColumnCell(0x0e, s2) ^ invMixColumnCell(0x0b, s3));
                        mixedState[3][col] = toHexString(invMixColumnCell(0x0b, s0) ^ invMixColumnCell(0x0d, s1)
                                        ^ invMixColumnCell(0x09, s2) ^ invMixColumnCell(0x0e, s3));
                }

                return mixedState;
        }

        private static String toHexString(int value) {
                String hexString = Integer.toHexString(value);
                return hexString.length() == 1 ? "0" + hexString : hexString;
        }

        private static int invMixColumnCell(int multiplier, int value) {
                int result;
                if (multiplier == 0x09) {
                        result = (value << 3) ^ (value << 1);
                } else if (multiplier == 0x0b) {
                        result = (value << 3) ^ (value << 1) ^ value;
                } else if (multiplier == 0x0d) {
                        result = (value << 3) ^ (value << 2) ^ value;
                } else {
                        result = (value << 3) ^ (value << 2) ^ (value << 1);
                }
                return result & 0xFF;
        }

}

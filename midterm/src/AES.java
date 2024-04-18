import java.util.Scanner;

public class AES {
        private static final int BLOCK_SIZE = 16;
        private static final int MAX_COL_SIZE = 4;
        private static final int MAX_ROW_SIZE = 4;

        private static final byte[][] S_BOX = {
                        { (byte) 0x63, (byte) 0x7c, (byte) 0x77, (byte) 0x7b, (byte) 0xf2, (byte) 0x6b, (byte) 0x6f,
                                        (byte) 0xc5,
                                        (byte) 0x30, (byte) 0x01, (byte) 0x67, (byte) 0x2b, (byte) 0xfe, (byte) 0xd7,
                                        (byte) 0xab,
                                        (byte) 0x76 },
                        { (byte) 0xca, (byte) 0x82, (byte) 0xc9, (byte) 0x7d, (byte) 0xfa, (byte) 0x59, (byte) 0x47,
                                        (byte) 0xf0,
                                        (byte) 0xad, (byte) 0xd4, (byte) 0xa2, (byte) 0xaf, (byte) 0x9c, (byte) 0xa4,
                                        (byte) 0x72,
                                        (byte) 0xc0 },
                        { (byte) 0xb7, (byte) 0xfd, (byte) 0x93, (byte) 0x26, (byte) 0x36, (byte) 0x3f, (byte) 0xf7,
                                        (byte) 0xcc,
                                        (byte) 0x34, (byte) 0xa5, (byte) 0xe5, (byte) 0xf1, (byte) 0x71, (byte) 0xd8,
                                        (byte) 0x31,
                                        (byte) 0x15 },
                        { (byte) 0x04, (byte) 0xc7, (byte) 0x23, (byte) 0xc3, (byte) 0x18, (byte) 0x96, (byte) 0x05,
                                        (byte) 0x9a,
                                        (byte) 0x07, (byte) 0x12, (byte) 0x80, (byte) 0xe2, (byte) 0xeb, (byte) 0x27,
                                        (byte) 0xb2,
                                        (byte) 0x75 },
                        { (byte) 0x09, (byte) 0x83, (byte) 0x2c, (byte) 0x1a, (byte) 0x1b, (byte) 0x6e, (byte) 0x5a,
                                        (byte) 0xa0,
                                        (byte) 0x52, (byte) 0x3b, (byte) 0xd6, (byte) 0xb3, (byte) 0x29, (byte) 0xe3,
                                        (byte) 0x2f,
                                        (byte) 0x84 },
                        { (byte) 0x53, (byte) 0xd1, (byte) 0x00, (byte) 0xed, (byte) 0x20, (byte) 0xfc, (byte) 0xb1,
                                        (byte) 0x5b,
                                        (byte) 0x6a, (byte) 0xcb, (byte) 0xbe, (byte) 0x39, (byte) 0x4a, (byte) 0x4c,
                                        (byte) 0x58,
                                        (byte) 0xcf },
                        { (byte) 0xd0, (byte) 0xef, (byte) 0xaa, (byte) 0xfb, (byte) 0x43, (byte) 0x4d, (byte) 0x33,
                                        (byte) 0x85,
                                        (byte) 0x45, (byte) 0xf9, (byte) 0x02, (byte) 0x7f, (byte) 0x50, (byte) 0x3c,
                                        (byte) 0x9f,
                                        (byte) 0xa8 },
                        { (byte) 0x51, (byte) 0xa3, (byte) 0x40, (byte) 0x8f, (byte) 0x92, (byte) 0x9d, (byte) 0x38,
                                        (byte) 0xf5,
                                        (byte) 0xbc, (byte) 0xb6, (byte) 0xda, (byte) 0x21, (byte) 0x10, (byte) 0xff,
                                        (byte) 0xf3,
                                        (byte) 0xd2 },
                        { (byte) 0xcd, (byte) 0x0c, (byte) 0x13, (byte) 0xec, (byte) 0x5f, (byte) 0x97, (byte) 0x44,
                                        (byte) 0x17,
                                        (byte) 0xc4, (byte) 0xa7, (byte) 0x7e, (byte) 0x3d, (byte) 0x64, (byte) 0x5d,
                                        (byte) 0x19,
                                        (byte) 0x73 },
                        { (byte) 0x60, (byte) 0x81, (byte) 0x4f, (byte) 0xdc, (byte) 0x22, (byte) 0x2a, (byte) 0x90,
                                        (byte) 0x88,
                                        (byte) 0x46, (byte) 0xee, (byte) 0xb8, (byte) 0x14, (byte) 0xde, (byte) 0x5e,
                                        (byte) 0x0b,
                                        (byte) 0xdb },
                        { (byte) 0xe0, (byte) 0x32, (byte) 0x3a, (byte) 0x0a, (byte) 0x49, (byte) 0x06, (byte) 0x24,
                                        (byte) 0x5c,
                                        (byte) 0xc2, (byte) 0xd3, (byte) 0xac, (byte) 0x62, (byte) 0x91, (byte) 0x95,
                                        (byte) 0xe4,
                                        (byte) 0x79 },
                        { (byte) 0xe7, (byte) 0xc8, (byte) 0x37, (byte) 0x6d, (byte) 0x8d, (byte) 0xd5, (byte) 0x4e,
                                        (byte) 0xa9,
                                        (byte) 0x6c, (byte) 0x56, (byte) 0xf4, (byte) 0xea, (byte) 0x65, (byte) 0x7a,
                                        (byte) 0xae,
                                        (byte) 0x08 },
                        { (byte) 0xba, (byte) 0x78, (byte) 0x25, (byte) 0x2e, (byte) 0x1c, (byte) 0xa6, (byte) 0xb4,
                                        (byte) 0xc6,
                                        (byte) 0xe8, (byte) 0xdd, (byte) 0x74, (byte) 0x1f, (byte) 0x4b, (byte) 0xbd,
                                        (byte) 0x8b,
                                        (byte) 0x8a },
                        { (byte) 0x70, (byte) 0x3e, (byte) 0xb5, (byte) 0x66, (byte) 0x48, (byte) 0x03, (byte) 0xf6,
                                        (byte) 0x0e,
                                        (byte) 0x61, (byte) 0x35, (byte) 0x57, (byte) 0xb9, (byte) 0x86, (byte) 0xc1,
                                        (byte) 0x1d,
                                        (byte) 0x9e },
                        { (byte) 0xe1, (byte) 0xf8, (byte) 0x98, (byte) 0x11, (byte) 0x69, (byte) 0xd9, (byte) 0x8e,
                                        (byte) 0x94,
                                        (byte) 0x9b, (byte) 0x1e, (byte) 0x87, (byte) 0xe9, (byte) 0xce, (byte) 0x55,
                                        (byte) 0x28,
                                        (byte) 0xdf },
                        { (byte) 0x8c, (byte) 0xa1, (byte) 0x89, (byte) 0x0d, (byte) 0xbf, (byte) 0xe6, (byte) 0x42,
                                        (byte) 0x68,
                                        (byte) 0x41, (byte) 0x99, (byte) 0x2d, (byte) 0x0f, (byte) 0xb0, (byte) 0x54,
                                        (byte) 0xbb,
                                        (byte) 0x16 }
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
                System.out.println("Enter Text To decrypt:");
                String text = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do";
                // String text = scan.next();
                System.out.println("Enter Decryption Key (16 bytes in hexadecimal format):");
                // String key = scan.next();

                String key = "2b7e151628aed2a6abf7158809cf4f3c";
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

        private static String encryptECB(String text, String key) {

                String[][] initialState = initialState(text);
                String[][] initialStateKey = convertKeyToState(key);

                String[][] state = addRoundKey(initialState, initialStateKey);
                System.out.println(initialState[0][0]);
                System.out.println(initialStateKey[0][1]);
                System.out.println(state[0][0]);
                state = subBytes(state);
                System.out.println(state[0][2]);
                state = shiftRows(state);
                System.out.println(state[1][0]);
                return text;
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

        private static String[][] initialState(String text) {
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

        private static String[][] convertKeyToState(String key) {

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

        private static String decryptECB(String input, String key) {

                return input;
        }
}

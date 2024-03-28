import java.util.Scanner;

public class RC4 {
    private byte[] S = new byte[256];
    private int keyLen;

    public RC4(byte[] key) {
        keyLen = key.length;
        byte[] T = new byte[256];
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
            T[i] = key[i % keyLen];
        }
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + T[i]) & 0xFF;
            byte temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }
    }

    public byte[] process(byte[] data) {
        byte[] output = new byte[data.length];
        int i = 0, j = 0;
        for (int counter = 0; counter < data.length; counter++) {
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;

            byte temp = S[i];
            S[i] = S[j];
            S[j] = temp;

            int t = (S[i] + S[j]) & 0xFF;
            byte k = S[t];
            
            output[counter] = (byte) (data[counter] ^ k);
        }
        return output;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the key (as a string): ");
        String keyString = scanner.nextLine();
        byte[] key = keyString.getBytes();

        System.out.print("Enter the plaintext (as a string): ");
        String plaintextString = scanner.nextLine();
        byte[] plaintext = plaintextString.getBytes();
        
        RC4 rc4Encrypt = new RC4(key);
        byte[] ciphertext = rc4Encrypt.process(plaintext);
        System.out.println("Ciphertext (in hex): " + bytesToHex(ciphertext));

        RC4 rc4Decrypt = new RC4(key); // Reinitialize for decryption
        byte[] decrypted = rc4Decrypt.process(ciphertext);
        System.out.println("Decrypted text: " + new String(decrypted));
        
        scanner.close();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim();
    }
}

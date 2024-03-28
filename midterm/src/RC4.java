import java.util.Scanner;

public class RC4 {
    private byte[] S = new byte[256];
    private byte[] T = new byte[256];
    private int keyLen;

    public RC4(byte[] key) {
        keyLen = key.length;
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
            T[i] = key[i % keyLen];
        }
        int j = 0;
        byte temp;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + T[i]) & 0xFF;
            temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }
    }

    public byte[] encrypt(byte[] plaintext) {
        byte[] ciphertext = new byte[plaintext.length];
        int i = 0, j = 0, k, t;
        byte temp;
        for (int counter = 0; counter < plaintext.length; counter++) {
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;

            temp = S[i];
            S[i] = S[j];
            S[j] = temp;

            t = (S[i] + S[j]) & 0xFF;
            k = S[t];
            
            ciphertext[counter] = (byte) (plaintext[counter] ^ k);
        }
        return ciphertext;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the key (as a string): ");
        String keyString = scanner.nextLine();
        byte[] key = keyString.getBytes();

        RC4 rc4 = new RC4(key);

        System.out.print("Enter the plaintext (as a string): ");
        String plaintextString = scanner.nextLine();
        byte[] plaintext = plaintextString.getBytes();
        
        byte[] ciphertext = rc4.encrypt(plaintext);
        System.out.println("Ciphertext (in hex): " + bytesToHex(ciphertext));

        // Decrypting the ciphertext for demonstration
        byte[] decrypted = rc4.encrypt(ciphertext); // RC4 is symmetric
        System.out.println("Decrypted text: " + new String(decrypted));
        
        scanner.close();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}

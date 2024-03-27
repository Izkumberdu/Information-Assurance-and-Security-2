function vigenereEncrypt(plainText, key) {
    plainText = plainText.toUpperCase();
    key = key.toUpperCase();
    let encryptedText = '';
    for (let i = 0; i < plainText.length; i++) {
        const plainChar = plainText.charCodeAt(i) - 65; // Convert to 0-indexed
        const keyChar = key.charCodeAt(i % key.length) - 65; // Repeat key if needed
        const encryptedChar = (plainChar + keyChar) % 26 + 65; // Mod 26 to wrap around, then convert back to ASCII
        encryptedText += String.fromCharCode(encryptedChar);
    }
    return encryptedText;
}

function vigenereDecrypt(encryptedText, key) {
    encryptedText = encryptedText.toUpperCase();
    key = key.toUpperCase();
    let decryptedText = '';
    for (let i = 0; i < encryptedText.length; i++) {
        const encryptedChar = encryptedText.charCodeAt(i) - 65; // Convert to 0-indexed
        const keyChar = key.charCodeAt(i % key.length) - 65; // Repeat key if needed
        let decryptedChar = (encryptedChar - keyChar) % 26; // Perform decryption
        if (decryptedChar < 0) decryptedChar += 26; // Handle negative results
        decryptedChar += 65; // Convert back to ASCII
        decryptedText += String.fromCharCode(decryptedChar);
    }
    return decryptedText;
}

// Example usage:
const plainText = "HELLO";
const key = "KEY";

const encryptedText = vigenereEncrypt(plainText, key);
console.log("Encrypted:", encryptedText); // Output: "RIJVS"

const decryptedText = vigenereDecrypt(encryptedText, key);
console.log("Decrypted:", decryptedText); // Output: "HELLO"

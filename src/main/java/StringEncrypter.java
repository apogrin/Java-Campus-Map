/**
 * Test
 * @author orenj
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
/**
 * encrypt class
 * @author louie
 */
public class StringEncrypter {

    private Key key;
    private String filename;

    /**
     * constructor
     */
    public StringEncrypter()  {    
        try{
            // Initialize a basic key without randomization
            byte[] keyBytes = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08 }; // Replace with your desired key values
            this.key = new SecretKeySpec(keyBytes, "DES");
            
            //this.filename = filename;
            //KeyGenerator generator;
            //generator = KeyGenerator.getInstance("DES");
            //SecureRandom sec = new SecureRandom(password.getBytes());
            //generator.init(sec);
            //key = generator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * method does the encryption 
     * @param userInfo string to be encrypted
     * @return the byte[] storing the encrypted key
     * @throws Exception some error
     */
    public byte[] encrypt(String userInfo) throws Exception {
        //String accountJson = "[{\"systemID\": \"" + systemID + "\", \"password\": \"" + password + "\"}]";
        try{
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, this.key);
            byte[] encryptedAccount = cipher.doFinal(userInfo.getBytes(StandardCharsets.UTF_8));
            //String encryptedID = cipher.doFinal(systemID);
            // Save the encrypted user account data, the key, and the IV to a file
            //FileOutputStream fos = new FileOutputStream(filename);
            //fos.write(encryptedAccount);
            //fos.close();
            return encryptedAccount;
        } catch (Exception e) { 
            e.printStackTrace();
        }
        return null;
    }
    /**
     * method decrypts a byte[]
     * @param encryptedAccount the byte array to be decrypted
     * @return the decrypted string
     * @throws Exception some exception
     */
    public String decrypt(byte[] encryptedAccount) throws Exception{
        try{
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, this.key);
            
            byte[] decryptedAccount = cipher.doFinal(encryptedAccount);
            // Check if the decrypted user account data matches the given username and password
            String accountJson = new String(decryptedAccount, StandardCharsets.UTF_8);
            //String expectedJson = "[{\"systemID\": \"" + systemID + "\", \"password\": \"" + password + "\"}]";
            return accountJson;
        } catch(Exception e ){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * test method
     * @param args command line arguments 
     */
    public static void main(String[] args) {
        try {
            StringEncrypter encryptor1 = new StringEncrypter();
            byte[] encryptedUser = encryptor1.encrypt("hello");
            System.out.println(encryptedUser);
            
            StringEncrypter encryptor2 = new StringEncrypter();
            byte[] encryptedID = new byte[] {26,-18,101,107,31,-31,37,82};
            String decryptedUser = encryptor2.decrypt(encryptedUser);
            System.out.println(decryptedUser);
            //System.out.println(encryptor.login("ojoffe", "hello","dataFiles/UserData.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
public class JSONEncryptor {
    private SecretKey key;
    private byte[] iv;
    
    public static void main(String[] args) {
        try {
            JSONEncryptor encryptor = new JSONEncryptor();
            encryptor.createAccount("ojoffe", "hello","dataFiles/UserData.json");
            System.out.println(encryptor.login("ojoffe", "hello","dataFiles/UserData.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public JSONEncryptor() {
        try {
            // Generate a secure random key to use for encryption
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom random = new SecureRandom();
            keyGen.init(256, random);
            this.key = keyGen.generateKey();
            
            // Generate a random initialization vector (IV)
            iv = new byte[16];
            random.nextBytes(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void createAccount(String systemID, String password, String fileName) throws Exception {
        // Create a user account with the given username and password
        String accountJson = "[{\"systemID\": \"" + systemID + "\", \"password\": \"" + password + "\"}]";

        // Encrypt the user account data using AES encryption
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] encryptedAccount = cipher.doFinal(accountJson.getBytes(StandardCharsets.UTF_8));

        // Save the encrypted user account data, the key, and the IV to a file
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(encryptedAccount);
        fos.write(key.getEncoded());
        fos.write(iv);
        fos.close();
    }

    public boolean login(String systemID, String password, String fileName) throws Exception {
        // Load the encrypted user account data, the key, and the IV from the file
        FileInputStream fis = new FileInputStream(fileName);
        byte[] encryptedAccount = fis.readAllBytes();
        byte[] keyBytes = fis.readNBytes(32);
        //byte[] iv = fis.readNBytes(16);
        fis.close();

        // Decrypt the user account data using AES decryption
        //SecretKey key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, this.key, new IvParameterSpec(this.iv));
        try {
            byte[] decryptedAccount = cipher.doFinal(encryptedAccount);
            // Check if the decrypted user account data matches the given username and password
            String accountJson = new String(decryptedAccount, StandardCharsets.UTF_8);
            String expectedJson = "[{\"systemID\": \"" + systemID + "\", \"password\": \"" + password + "\"}]";
            return accountJson.equals(expectedJson);
        } catch (BadPaddingException b) {
            return false;
        }
    }
}*/


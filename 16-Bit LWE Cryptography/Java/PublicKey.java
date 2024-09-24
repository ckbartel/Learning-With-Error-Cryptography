import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Random;
import java.util.Scanner;

public class PublicKey {
    private Matrix alpha;
    private Matrix beta;
    private int s;
    private int m;
    private int n;
    private int v;

    public PublicKey(String fileName) throws FileNotFoundException {
        FileInputStream file = new FileInputStream(fileName);
        Scanner inFS = new Scanner(file);

        s = Integer.parseInt(inFS.nextLine());
        v = Integer.parseInt(inFS.nextLine());

        alpha = new Matrix(s, v);

        for (int i = 0; i < s; i++) {
            String line = inFS.nextLine();
            String[] arr = line.split(" ");
            for (int j = 0; j < v; j++) {
                alpha.setElem(i, j, Integer.parseInt(arr[j]));
            }
        }

        beta = new Matrix(s, 1);

        for (int i = 0; i < s; i++) {
            beta.setElem(i, 0, Integer.parseInt(inFS.nextLine()));
        }

        m = Integer.parseInt(inFS.nextLine());

        n = Integer.parseInt(inFS.nextLine());

        inFS.close();
    }

    public PublicKey(Matrix alpha, Matrix beta, int m, int n) {
        this.alpha = alpha;
        this.beta = beta;
        this.s = alpha.getRows();
        this.m = m;
        this.n = n;
        this.v = alpha.getCols();
    }

    public void encrypt(String msgFileName, String encryptedMsgFileName) throws FileNotFoundException, IOException {
        StringBuilder message = new StringBuilder();
        FileInputStream msgFile = new FileInputStream(msgFileName);
        Scanner msgInFS = new Scanner(msgFile);

        while (msgInFS.hasNextLine()) {
            message.append(msgInFS.nextLine() + ((msgInFS.hasNextLine())? "\n" : ""));
        }

        msgInFS.close();

        StringBuilder messageString = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            StringBuilder byteString = new StringBuilder();
            byte by = (byte) message.charAt(i);
            while (by > 0) {
                byteString.insert(0, by % 2);
                by /= 2;
            }
            int len = byteString.length();
            for (int j = 0; j < 8 - len; j++) {
                byteString.insert(0, "0");
            }
            messageString.append(byteString);
        }
        
        Random r = new Random();
        
        int[] randomIndexes = new int[n];

        for (int i = 0; i < n; i++) {
            randomIndexes[i] = r.nextInt(s);
        }

        FileOutputStream encryptedMsgFile = new FileOutputStream(encryptedMsgFileName);

        for (int i = 0; i < messageString.length(); i++) {
            Matrix p = new Matrix(1, v);
            Matrix q = new Matrix(1, 1);

            for (int j = 0; j < n; j++) {
                int index = r.nextInt(s);
                p = p.plus(alpha.getRowMatrix(index));
                q = q.plus(beta.getRowMatrix(index));
            }

            if (messageString.charAt(i) == '0') {
                Matrix offset = new Matrix(1, 1);
                offset.setElem(0, 0, m / 2);
                q = q.plus(offset);
            }

            q.mod(m);
            String pString = p.to16Bit();
            for (int j = 0 ; j < 2 * v; j++) {
                encryptedMsgFile.write(pString.charAt(j));
                
            }
            
            String qString = q.to16Bit();
            encryptedMsgFile.write(qString.charAt(0));
            encryptedMsgFile.write(qString.charAt(1));
        }

        encryptedMsgFile.close();
    }
    /* 
    public void encrypt(String msgFileName, String encryptedMsgFileName) throws FileNotFoundException {
        String message = "";
        FileInputStream msgFile = new FileInputStream(msgFileName);
        Scanner msgInFS = new Scanner(msgFile);

        while (msgInFS.hasNextLine()) {
            message += msgInFS.nextLine() + ((msgInFS.hasNextLine())? "\n" : "");
        }

        msgInFS.close();

        String messageString = "";

        for (int i = 0; i < message.length(); i++) {
            StringBuilder byteString = new StringBuilder();
            byte by = (byte) message.charAt(i);
            while (by > 0) {
                byteString.insert(0, by % 2);
                by /= 2;
            }
            int len = byteString.length();
            for (int j = 0; j < 8 - len; j++) {
                byteString.insert(0, "0");
            }
            messageString += byteString;
        }
        
        Random r = new Random();
        
        int[] randomIndexes = new int[n];

        for (int i = 0; i < n; i++) {
            randomIndexes[i] = r.nextInt(s);
        }

        FileOutputStream encryptedMsgFile = new FileOutputStream(encryptedMsgFileName);
        PrintWriter encryptedMsgOutFS = new PrintWriter(encryptedMsgFile);

        for (int i = 0; i < messageString.length(); i++) {
            Matrix p = new Matrix(1, v);
            Matrix q = new Matrix(1, 1);

            for (int j = 0; j < n; j++) {
                int index = r.nextInt(s);
                p = p.plus(alpha.getRowMatrix(index));
                q = q.plus(beta.getRowMatrix(index));
            }

            if (messageString.charAt(i) == '0') {
                Matrix offset = new Matrix(1, 1);
                offset.setElem(0, 0, m / 2);
                q = q.plus(offset);
            }

            q.mod(m);
            
            encryptedMsgOutFS.print("" + p + q);
        }

        encryptedMsgOutFS.close();
    }
    */

    public void createFile(String fileName) throws FileNotFoundException {
        FileOutputStream file = new FileOutputStream(fileName);
        PrintWriter outFS = new PrintWriter(file);

        outFS.println(s);
        outFS.println(v);
        outFS.print(alpha);
        outFS.print(beta);
        outFS.println(m);
        outFS.println(n);

        outFS.close();
    }
}
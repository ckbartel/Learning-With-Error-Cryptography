import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Random;

public class PrivateKey {
    private Matrix key;
    private int s;
    private int v;
    private int m;
    private int n;
    private int r;
    private int c;

    public PrivateKey(String fileName) throws FileNotFoundException {
        FileInputStream file = new FileInputStream(fileName);
        Scanner inFS = new Scanner(file);

        s = Integer.parseInt(inFS.nextLine());
        v = Integer.parseInt(inFS.nextLine());
        key = new Matrix(v, 1);

        for (int i = 0; i < v; i++) {
            key.setElem(i, 0, Integer.parseInt(inFS.nextLine()));
        }

        m = Integer.parseInt(inFS.nextLine());
        n = Integer.parseInt(inFS.nextLine());
        r = Integer.parseInt(inFS.nextLine());
        c = Integer.parseInt(inFS.nextLine());

        inFS.close();
    }

    public PublicKey createPublicKey() {
        Random rand = new Random();
        Matrix alpha = new Matrix(s, v);

        for (int i = 0; i < s; i++) {
            for (int j = 0; j < v; j++) {
                alpha.setElem(i, j, rand.nextInt(c + 1));
            }
        }

        Matrix beta = alpha.times(key);
        Matrix rho = new Matrix(s, 1);

        for (int i = 0; i < s; i++) {
            rho.setElem(i, 0, rand.nextInt(2 * r + 1) - r);
        }

        beta = beta.plus(rho);
        beta.mod(m);

        return new PublicKey(alpha, beta, m, n);        
    }

    public void decrypt(String encryptedMsgFileName, String msgFileName) throws FileNotFoundException, IOException {
        FileInputStream encryptedMsgFile = new FileInputStream(encryptedMsgFileName);

        StringBuilder messageLine = new StringBuilder();

        while (encryptedMsgFile.available() != 0) {
            Matrix p = new Matrix(1, v );
            Matrix q = new Matrix(1, 1);

            for (int i = 0; i < v; i++) {
                String bits = "" + (char) encryptedMsgFile.read() + (char) encryptedMsgFile.read();
                int num = convertToNum(bits);
                p.setElem(0, i, num);
            }

            String bits = "" + (char) encryptedMsgFile.read() + (char) encryptedMsgFile.read();
            int num = convertToNum(bits);
            q.setElem(0, 0, num);

            if (p.times(key).isWithin(q, m)) {
                messageLine.append("1");
            } else {
                messageLine.append("0");
            }
        }

        encryptedMsgFile.close();

        FileOutputStream msgFile = new FileOutputStream(msgFileName);
        PrintWriter msgOutFS = new PrintWriter(msgFile);

        for (int i = 0; i < messageLine.length() / 8; i++) {
            byte by = 0;
            for (int j = 0; j < 8; j++) {
                if (messageLine.charAt(8 * i + j) == '1') {
                    by += Math.pow(2, 7 - j);
                }
            }
            msgOutFS.print((char) by);
        }

        msgOutFS.close();
    }

    /* 
    public void decrypt(String encryptedMsgFileName, String msgFileName) throws FileNotFoundException {
        FileInputStream encryptedMsgFile = new FileInputStream(encryptedMsgFileName);
        Scanner encryptedMsgInFS = new Scanner(encryptedMsgFile);
        
        String messageLine = "";

        while (encryptedMsgInFS.hasNextLine()) {
            String pLine = encryptedMsgInFS.nextLine();
            if (encryptedMsgInFS.hasNextLine()) {
                String qLine = encryptedMsgInFS.nextLine();
                String[] pArray = pLine.split(" ");

                Matrix p = new Matrix(1, v);

                for (int i = 0; i < v; i++) {
                    p.setElem(0, i, Integer.parseInt(pArray[i]));
                }

                Matrix q = new Matrix(1, 1);
                q.setElem(0, 0, Integer.parseInt(qLine));

                if (p.times(key).isWithin(q, m)) {
                    messageLine += "1";
                } else {
                    messageLine += "0";
                }
            }
            
        }

        encryptedMsgInFS.close();

        FileOutputStream msgFile = new FileOutputStream(msgFileName);
        PrintWriter msgOutFS = new PrintWriter(msgFile);

        for (int i = 0; i < messageLine.length() / 8; i++) {
            byte by = 0;
            for (int j = 0; j < 8; j++) {
                if (messageLine.charAt(8 * i + j) == '1') {
                    by += Math.pow(2, 7 - j);
                }
            }
            msgOutFS.print((char) by);
        }

        msgOutFS.close();
    }

*/

    private int convertToNum(String bits) {
        return 256 * bits.charAt(0) + bits.charAt(1);
    }
}

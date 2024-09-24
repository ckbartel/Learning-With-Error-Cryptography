
public class Encrypt {
    public static void main(String[] args) {
        
        View view = new View();

        boolean encrypting = true;

        while (encrypting) {
            char option = view.getOptions();

            try {
                if (option == 'e') {
                    PublicKey publicKey = new PublicKey(view.getFileName("Public Key"));
                    publicKey.encrypt(view.getFileName("Message"), view.getFileName("Encrypted Message"));
                } else if (option == 'd') {
                    PrivateKey privateKey = new PrivateKey(view.getFileName("Private Key"));
                    privateKey.decrypt(view.getFileName("Encrypted Message"), view.getFileName("Message"));
                } else if (option == 'c') {
                    PrivateKey privateKey = new PrivateKey(view.getFileName("Private Key"));
                    PublicKey publicKey = privateKey.createPublicKey();
                    publicKey.createFile(view.getFileName("Public Key"));
                } else {
                    encrypting = false;
                }
            } catch (Exception err) {
                view.displayFileNotFoundError();
            }
        }

        view.displayExiting();
    }
}
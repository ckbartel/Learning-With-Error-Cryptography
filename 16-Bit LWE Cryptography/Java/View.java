import java.util.Scanner;

public class View {
    private Scanner scnr = new Scanner(System.in);

    public View() {};

    public char getOptions() {
        System.out.print("\nWould you like to (e)ncrypt a file, (d)ecrypt a file, (c)reate a public key, or (s)top: ");
        while (true) {
            String option = scnr.nextLine();
            if (option.charAt(0) == 'e' || option.charAt(0) == 'E') {
                return 'e';
            } else if (option.charAt(0) == 'd' || option.charAt(0) == 'D') {
                return 'd';
            } else if (option.charAt(0) == 'c' || option.charAt(0) == 'C') {
                return 'c';
            } else if (option.charAt(0) == 's' || option.charAt(0) == 'S') {
                return 's';
            }
            System.out.print("Please enter (e)ncrypt, (d)ecrypt, (c)reate, or (s)top: ");
        }
    }
    public String getFileName(String fileType) {
        System.out.print("Enter the name of the " + fileType + " file: ");
        return scnr.nextLine();
    }

    public void displayFileNotFoundError() {
        System.out.println("File not found...");
    }

    public void displayExiting() {
        System.out.println("Exiting...");
    }
}

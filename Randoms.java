import java.util.Random;
import java.util.Scanner;

public class Randoms {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("How many random integers do you want to generate? ");
        int count = scanner.nextInt();

        System.out.print("What is your name? ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("Enter minimum: ");
        int min = scanner.nextInt();

        System.out.print("Enter maximum: ");
        int max = scanner.nextInt();

        System.out.print("Result: ");
        for (int i = 0; i < count; i++) {
            int randomNumber = random.nextInt(max - min + 1) + min;
            System.out.print(randomNumber);
            if (i < count - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        System.out.println("Did I do well, " + name + "?");
    }
}

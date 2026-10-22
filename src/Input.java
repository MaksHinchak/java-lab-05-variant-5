import java.util.Scanner;
public final class Input {
    private static final Scanner IN = new Scanner(System.in);
    public static String text(String prompt) {
        System.out.print(prompt);
        if (!IN.hasNextLine()) throw new IllegalStateException("Введення завершено.");
        return IN.nextLine();
    }
    public static int integer(String prompt, int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(text(prompt).trim());
                if (value < min || value > max) throw new NumberFormatException();
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Потрібне ціле число від " + min + " до " + max);
            }
        }
    }
    public static double real(String prompt) {
        while (true) {
            try {
                double value = Double.parseDouble(text(prompt).trim().replace(',', '.'));
                if (!Double.isFinite(value)) throw new NumberFormatException();
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Введіть скінченне число.");
            }
        }
    }
}

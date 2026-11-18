import java.nio.file.Path;
import java.util.HashSet;
import java.util.Scanner;

public final class Input {
    private static final Scanner IN = new Scanner(System.in);

    public static String text(String prompt) {
        System.out.print(prompt);
        if (!IN.hasNextLine()) {
            throw new IllegalStateException("Введення завершено.");
        }
        return IN.nextLine().strip();
    }

    public static int integer(String prompt, int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(text(prompt));
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Введіть ціле число від " + min + " до " + max + ".");
        }
    }

    public static double axis(String prompt) {
        while (true) {
            try {
                double value = Double.parseDouble(text(prompt).replace(',', '.'));
                if (Function.isValidAxis(value)) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Введіть піввісь більшу за 0 і не більшу за " + Function.MAX_AXIS + ".");
        }
    }

    public static Path path(String label, String defaultPath) {
        String value = text(label + " [Enter: " + defaultPath + "]: ");
        if (value.isEmpty()) {
            return Path.of(defaultPath);
        }
        return Path.of(value);
    }

    public static HashSet<Integer> integerSet(String prompt) {
        while (true) {
            String line = text(prompt);
            HashSet<Integer> values = new HashSet<>();
            if (line.isEmpty()) {
                return values;
            }
            try {
                for (String part : line.split("\\s+")) {
                    values.add(Integer.parseInt(part));
                }
                return values;
            } catch (NumberFormatException e) {
                System.out.println("Введіть цілі числа через пробіл або залиште рядок порожнім.");
            }
        }
    }
}

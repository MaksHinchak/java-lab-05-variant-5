import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
        try {
            int task = Input.integer("Завдання (1 - колекції функцій, 2 - множини): ", 1, 2);
            if (task == 1) {
                runCurves();
            } else {
                runSets();
            }
        } catch (IOException | IllegalArgumentException | IllegalStateException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void runCurves() throws IOException {
        Path ellipseFile = Input.path("Файл еліпсів", "data/ellipses.txt");
        ArrayList<Ellipse> ellipses = load(ellipseFile, Ellipse::new);
        Path hyperbolaFile = Input.path("Файл гіпербол", "data/hyperbolas.txt");
        ArrayList<Hyperbola> hyperbolas = load(hyperbolaFile, Hyperbola::new);
        printLists("Прочитано", ellipses, hyperbolas);

        Collections.sort(ellipses);
        Collections.sort(hyperbolas);
        printLists("Відсортовано", ellipses, hyperbolas);

        ellipses.add(new Ellipse(Input.axis("Новий еліпс a: "), Input.axis("b: ")));
        hyperbolas.add(new Hyperbola(Input.axis("Нова гіпербола a: "), Input.axis("b: ")));
        Collections.sort(ellipses);
        Collections.sort(hyperbolas);
        printLists("Після додавання", ellipses, hyperbolas);

        ArrayList<Function> all = new ArrayList<>(ellipses);
        all.addAll(hyperbolas);
        Collections.sort(all, new FunctionComparator());

        System.out.println("Спільний список: " + all);
        saveCurves(all);
    }

    private static void printLists(String title, ArrayList<Ellipse> ellipses,
                                   ArrayList<Hyperbola> hyperbolas) {
        System.out.println(title + ":");
        System.out.println(ellipses);
        System.out.println(hyperbolas);
    }

    private static <T extends Function> ArrayList<T> load(Path file, BiFunction<Double, Double, T> create)
            throws IOException {
        ArrayList<T> curves = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.strip().split("\\s+");
                if (parts.length != 2) {
                    throw new IOException(file + ", рядок " + lineNumber + ": потрібно два числа a b.");
                }

                try {
                    double a = Double.parseDouble(parts[0]);
                    double b = Double.parseDouble(parts[1]);
                    curves.add(create.apply(a, b));
                } catch (IllegalArgumentException e) {
                    throw new IOException(file + ", рядок " + lineNumber + ": " + e.getMessage(), e);
                }
            }
        }
        return curves;
    }

    private static void saveCurves(ArrayList<Function> curves) throws IOException {
        Path output = Input.path("Вихідний файл", "result.txt");
        if (Files.exists(output) && !Input.text("Файл існує. Перезаписати? так/ні: ").equalsIgnoreCase("так")) {
            System.out.println("Збереження скасовано.");
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(output)) {
            for (Function curve : curves) {
                writer.write(curve.getClass().getSimpleName() + " " + curve.getA() + " " + curve.getB());
                writer.newLine();
            }
        }
        System.out.println("Збережено: " + output);
    }

    private static void runSets() {
        HashSet<Integer> a = Input.integerSet("Множина A (числа через пробіл): ");
        HashSet<Integer> b = Input.integerSet("Множина B: ");

        System.out.println("Перетин: " + new TreeSet<>(intersection(a, b)));
        System.out.println("Об'єднання: " + new TreeSet<>(union(a, b)));
    }

    public static HashSet<Integer> intersection(HashSet<Integer> a, HashSet<Integer> b) {
        HashSet<Integer> result = new HashSet<>(a);
        result.retainAll(b);
        return result;
    }

    public static HashSet<Integer> union(HashSet<Integer> a, HashSet<Integer> b) {
        HashSet<Integer> result = new HashSet<>(a);
        result.addAll(b);
        return result;
    }
}

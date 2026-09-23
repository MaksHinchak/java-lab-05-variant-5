import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
public class Main {
    // static — виклик без об’єкта; public — доступ ззовні, private — лише в класі; void — без результату.
    // throws оголошує перевірюваний виняток: викликач мусить перехопити його або теж оголосити.
    public static ArrayList<Function> load(Path file, boolean ellipse) throws IOException { // Кожен тип читається зі свого файлу формату a b.
        // ArrayList<T> — змінний список елементів типу T; <> після new виводить тип із контексту.
        ArrayList<Function> result = new ArrayList<>();
        // try (ресурс) автоматично закриває його після блоку, зокрема при помилці.
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) { // Автоматично закриваємо вхідний файл.
            String line;
            int number = 0;
            // readLine повертає null при EOF; порожній рядок "" означає прочитаний порожній рядок.
            // Присвоєння в умові: спочатку читаємо значення, потім перевіряємо його.
            while ((line = reader.readLine()) != null) { // Читаємо всі рядки до кінця файлу.
                number++;
                if (line.isBlank()) continue; // Порожні рядки не створюють записів.
                // String.split приймає regex: "\\s+" — пробіли, "\\." — крапка; -1 зберігає кінцеві порожні частини.
                String[] parts = line.trim().split("\\s+");
                // .length — довжина масиву без дужок; для String — length(), для колекції — size().
                // throw передає помилку в catch; new створює об’єкт винятку з повідомленням.
                if (parts.length != 2) throw new IOException("Рядок " + number + ": потрібно два числа a b."); // Виявляємо неповні або зайві дані.
                try { // Окремо обробляємо неправильні числа та півосі.
                    // parseInt/parseDouble перетворюють текст на число; помилка формату — NumberFormatException.
                    double a = Double.parseDouble(parts[0]);
                    double b = Double.parseDouble(parts[1]);
                    // умова ? a : b — вибір значення: a, якщо true, інакше b.
                    // new Клас(...) створює об’єкт і викликає його конструктор.
                    result.add(ellipse ? new Ellipse(a, b) : new Hyperbola(a, b));
                // catch (Тип e) перехоплює виняток із try; e.getMessage() повертає його повідомлення.
                } catch (IllegalArgumentException e) { throw new IOException("Рядок " + number + ": " + e.getMessage(), e); } // Додаємо номер рядка до початкової причини.
            }
        }
        return result;
    }
    public static HashSet<Integer> union(HashSet<Integer> a, HashSet<Integer> b) { // Об'єднання не змінює вхідних множин.
        // HashSet<Integer> — множина; Integer потрібен замість int, упаковка відбувається автоматично.
        HashSet<Integer> result = new HashSet<>(a);
        result.addAll(b);
        return result;
    }
    public static HashSet<Integer> intersection(HashSet<Integer> a, HashSet<Integer> b) { // Перетин також є незалежною множиною.
        HashSet<Integer> result = new HashSet<>(a);
        result.retainAll(b);
        return result;
    }
    private static HashSet<Integer> set(String prompt) { // Читаємо набір цілих чисел одним рядком.
        HashSet<Integer> result = new HashSet<>();
        String line = Input.text(prompt).trim();
        // for (Тип елемент : колекція) — перебір елементів без індексу.
        if (!line.isEmpty()) for (String part : line.split("\\s+")) result.add(Integer.parseInt(part)); // Повторні числа зберігаються один раз.
        return result;
    }
    // main — точка входу; String[] args містить аргументи запуску без назви програми.
    public static void main(String[] args) { // Точка входу для роботи з колекціями.
        try { // Помилки файлів і даних мають зрозуміле повідомлення.
            int task = Input.integer("Завдання (1 - колекції функцій, 2 - множини): ", 1, 2);
            if (task == 2) { // Виконуємо операції над множинами цілих чисел.
                HashSet<Integer> a = set("Множина A (числа через пробіл): ");
                HashSet<Integer> b = set("Множина B: ");
                // TreeSet — множина з упорядкованим перебором.
                System.out.println("Перетин: " + new java.util.TreeSet<>(intersection(a, b)));
                System.out.println("Об'єднання: " + new java.util.TreeSet<>(union(a, b)));
                return;
            }
            ArrayList<Ellipse> ellipses = new ArrayList<>();
            ArrayList<Hyperbola> hyperbolas = new ArrayList<>();
            // Приведення посилання до підкласу; за невідповідного типу виникне ClassCastException.
            for (Function f : load(Path.of(Input.text("Файл еліпсів (data/ellipses.txt): ")), true)) ellipses.add((Ellipse) f); // Тип безпечний, оскільки load з true створює лише Ellipse.
            for (Function f : load(Path.of(Input.text("Файл гіпербол (data/hyperbolas.txt): ")), false)) hyperbolas.add((Hyperbola) f); // Другий файл містить лише Hyperbola.
            System.out.println("Прочитано: " + ellipses + "\n" + hyperbolas);
            // sort без порівнювача викликає Comparable.compareTo; з порівнювачем — Comparator.compare.
            Collections.sort(ellipses);
            Collections.sort(hyperbolas);
            System.out.println("Відсортовано: " + ellipses + "\n" + hyperbolas);
            ellipses.add(new Ellipse(Input.real("Новий еліпс a: "), Input.real("b: ")));
            hyperbolas.add(new Hyperbola(Input.real("Нова гіпербола a: "), Input.real("b: ")));
            Collections.sort(ellipses);
            Collections.sort(hyperbolas);
            System.out.println("Після додавання: " + ellipses + "\n" + hyperbolas);
            ArrayList<Function> all = new ArrayList<>(ellipses);
            all.addAll(hyperbolas);
            Collections.sort(all, new FunctionComparator());
            Path output = Path.of(Input.text("Вихідний файл: "));
            // && — «і», || — «або»; праву умову перевіряють лише за потреби.
            if (Files.exists(output) && !Input.text("Файл існує. Перезаписати? так/ні: ").equalsIgnoreCase("так")) return; // Існуючий файл змінюємо лише за явним вибором.
            try (BufferedWriter writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) { // Створюємо або погоджено перезаписуємо файл.
                for (Function f : all) { // Обходимо всі записи в остаточному порядку.
                    // getClass().getSimpleName() — коротка назва фактичного класу.
                    writer.write(f.getClass().getSimpleName() + " " + f.getA() + " " + f.getB());
                    writer.newLine();
                }
            }
            System.out.println("Спільний список: " + all + "\nЗбережено: " + output);
        // catch (Тип1 | Тип2 e) — один обробник для кількох типів винятків.
        } catch (IOException | RuntimeException e) { System.out.println("Помилка: " + e.getMessage()); } // Обробляємо формат, область значень та доступ до файлів.
    }
}

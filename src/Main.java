import java.io.BufferedReader; // Зчитуємо вхідні записи рядок за рядком.
import java.io.BufferedWriter; // Буферизований запис відсортованого результату.
import java.io.IOException; // Представляє помилки файлового вводу-виводу.
import java.nio.charset.StandardCharsets; // Всі текстові файли мають кодування UTF-8.
import java.nio.file.Files; // Відкриття текстових потоків.
import java.nio.file.Path; // Робота з користувацькими шляхами.
import java.util.ArrayList; // Контейнер для записів функцій.
import java.util.Collections; // Collections.sort викликає Comparable або Comparator.
import java.util.HashSet; // Зберігаємо лише унікальні цілі числа.
public class Main { // Лабораторна 5 використовує абстрактні функції з лабораторної 3, завдання 2.
    public static ArrayList<Function> load(Path file, boolean ellipse) throws IOException { // Кожен тип читається зі свого файлу формату a b.
        ArrayList<Function> result = new ArrayList<>(); // Створюємо змінний список записів.
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) { // Автоматично закриваємо вхідний файл.
            String line; // Поточний рядок файлу.
            int number = 0; // Номер рядка потрібен для змістовної помилки.
            while ((line = reader.readLine()) != null) { // Читаємо всі рядки до кінця файлу.
                number++; // Рахуємо також порожні рядки.
                if (line.isBlank()) continue; // Порожні рядки не створюють записів.
                String[] parts = line.trim().split("\\s+"); // Дозволяємо довільну кількість пробілів між параметрами.
                if (parts.length != 2) throw new IOException("Рядок " + number + ": потрібно два числа a b."); // Виявляємо неповні або зайві дані.
                try { // Окремо обробляємо неправильні числа та півосі.
                    double a = Double.parseDouble(parts[0]); // Перша піввісь.
                    double b = Double.parseDouble(parts[1]); // Друга піввісь.
                    result.add(ellipse ? new Ellipse(a, b) : new Hyperbola(a, b)); // Файл має один заздалегідь вибраний тип записів.
                } catch (IllegalArgumentException e) { throw new IOException("Рядок " + number + ": " + e.getMessage(), e); } // Додаємо номер рядка до початкової причини.
            }
        }
        return result; // Повертаємо всі прочитані об'єкти.
    }
    public static HashSet<Integer> union(HashSet<Integer> a, HashSet<Integer> b) { // Об'єднання не змінює вхідних множин.
        HashSet<Integer> result = new HashSet<>(a); // Копіюємо першу множину.
        result.addAll(b); // Додаємо елементи другої; дублікати HashSet відкидає.
        return result; // Повертаємо нову множину.
    }
    public static HashSet<Integer> intersection(HashSet<Integer> a, HashSet<Integer> b) { // Перетин також є незалежною множиною.
        HashSet<Integer> result = new HashSet<>(a); // Створюємо копію, щоб не змінити a.
        result.retainAll(b); // Зберігаємо лише спільні елементи.
        return result; // Порядок перебору HashSet не гарантується.
    }
    private static HashSet<Integer> set(String prompt) { // Читаємо набір цілих чисел одним рядком.
        HashSet<Integer> result = new HashSet<>(); // Порожній рядок означатиме порожню множину.
        String line = Input.text(prompt).trim(); // Видаляємо пробіли на краях.
        if (!line.isEmpty()) for (String part : line.split("\\s+")) result.add(Integer.parseInt(part)); // Повторні числа зберігаються один раз.
        return result; // Повертаємо побудовану множину.
    }
    public static void main(String[] args) { // Точка входу для роботи з колекціями.
        try { // Помилки файлів і даних мають зрозуміле повідомлення.
            int task = Input.integer("Завдання (1 - колекції функцій, 2 - множини): ", 1, 2); // Вибір задачі.
            if (task == 2) { // Виконуємо операції над множинами цілих чисел.
                HashSet<Integer> a = set("Множина A (числа через пробіл): "); // Читаємо першу множину.
                HashSet<Integer> b = set("Множина B: "); // Читаємо другу множину.
                System.out.println("Перетин: " + new java.util.TreeSet<>(intersection(a, b))); // TreeSet впорядковує лише вивід, обчислення виконано через HashSet.
                System.out.println("Об'єднання: " + new java.util.TreeSet<>(union(a, b))); // Друкуємо всі унікальні елементи.
                return; // Файлова частина в цьому режимі не виконується.
            }
            ArrayList<Ellipse> ellipses = new ArrayList<>(); // Окрема колекція першого похідного типу.
            ArrayList<Hyperbola> hyperbolas = new ArrayList<>(); // Окрема колекція другого похідного типу.
            for (Function f : load(Path.of(Input.text("Файл еліпсів (data/ellipses.txt): ")), true)) ellipses.add((Ellipse) f); // Тип безпечний, оскільки load з true створює лише Ellipse.
            for (Function f : load(Path.of(Input.text("Файл гіпербол (data/hyperbolas.txt): ")), false)) hyperbolas.add((Hyperbola) f); // Другий файл містить лише Hyperbola.
            System.out.println("Прочитано: " + ellipses + "\n" + hyperbolas); // Показуємо початковий порядок файлів.
            Collections.sort(ellipses); // Використовуємо Ellipse.compareTo.
            Collections.sort(hyperbolas); // Використовуємо Hyperbola.compareTo.
            System.out.println("Відсортовано: " + ellipses + "\n" + hyperbolas); // Показуємо результат природного сортування.
            ellipses.add(new Ellipse(Input.real("Новий еліпс a: "), Input.real("b: "))); // Додаємо один введений запис першого типу.
            hyperbolas.add(new Hyperbola(Input.real("Нова гіпербола a: "), Input.real("b: "))); // Додаємо один введений запис другого типу.
            Collections.sort(ellipses); // Повторне сортування після додавання.
            Collections.sort(hyperbolas); // Новий запис займає правильне місце.
            System.out.println("Після додавання: " + ellipses + "\n" + hyperbolas); // Демонструємо оновлені списки.
            ArrayList<Function> all = new ArrayList<>(ellipses); // Спільна колекція оголошена через абстрактний тип.
            all.addAll(hyperbolas); // Додаємо записи іншого підкласу.
            Collections.sort(all, new FunctionComparator()); // Явно передаємо порівнювач різнорідних об'єктів.
            Path output = Path.of(Input.text("Вихідний файл: ")); // Користувач обирає шлях для збереження.
            if (Files.exists(output) && !Input.text("Файл існує. Перезаписати? так/ні: ").equalsIgnoreCase("так")) return; // Існуючий файл змінюємо лише за явним вибором.
            try (BufferedWriter writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) { // Створюємо або погоджено перезаписуємо файл.
                for (Function f : all) { // Обходимо всі записи в остаточному порядку.
                    writer.write(f.getClass().getSimpleName() + " " + f.getA() + " " + f.getB()); // Зберігаємо тип, a і b в одному рядку.
                    writer.newLine(); // Відділяємо записи переходом рядка.
                }
            }
            System.out.println("Спільний список: " + all + "\nЗбережено: " + output); // Показуємо кінцевий результат і місце запису.
        } catch (IOException | RuntimeException e) { System.out.println("Помилка: " + e.getMessage()); } // Обробляємо формат, область значень та доступ до файлів.
    }
}

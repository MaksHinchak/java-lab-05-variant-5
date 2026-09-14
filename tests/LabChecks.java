public class LabChecks { // Автономні перевірки без зовнішніх бібліотек тестування.
    private static int count = 0; // Лічильник успішно перевірених умов.
    private static void check(boolean value) { // Допоміжний метод перетворює хибну умову на провал тесту.
        if (!value) throw new AssertionError("Перевірка " + (count + 1) + " не пройшла."); // Зупиняємо тест із ненульовим кодом процесу.
        count++; // Рахуємо лише успішні перевірки.
    }
    private static void near(double actual, double expected) { // Порівнюємо double з допуском на округлення.
        check(Math.abs(actual - expected) <= 1e-9 * Math.max(1, Math.abs(expected))); // Допуск враховує масштаб очікуваного числа.
    }
    private interface Action { void run() throws Exception; } // Лямбда тесту може породжувати і перевірювані винятки.
    private static void expect(Class<? extends Throwable> type, Action action) { // Перевіряємо, що помилкові дані дають саме потрібний вид помилки.
        try { action.run(); } // Виконуємо потенційно помилкову операцію.
        catch (Throwable error) { check(type.isInstance(error)); return; } // Неправильний тип винятку теж провалює тест.
        throw new AssertionError("Очікували " + type.getSimpleName()); // Відсутність потрібного винятку є помилкою реалізації.
    }
    public static void main(String[] args) throws Exception { // Метод запускає усі перевірки цієї лабораторної.
        java.util.HashSet<Integer> a = new java.util.HashSet<>(java.util.List.of(1,2,2)); // HashSet усуває повторне число.
        java.util.HashSet<Integer> b = new java.util.HashSet<>(java.util.List.of(2,3)); // Друга множина має один спільний елемент.
        check(Main.intersection(a,b).equals(java.util.Set.of(2))); // Очікуємо лише спільне число два.
        check(Main.union(a,b).equals(java.util.Set.of(1,2,3))); // Перевіряємо об'єднання.
        check(a.equals(java.util.Set.of(1,2))); // Операції не повинні змінити початкову множину.
        java.util.ArrayList<Ellipse> list = new java.util.ArrayList<>(java.util.List.of(new Ellipse(5,2),new Ellipse(2,3),new Ellipse(2,1))); // Дані перевіряють головний і додатковий ключі.
        java.util.Collections.sort(list); // Сортуємо через Comparable.
        check(list.get(0).equals(new Ellipse(2,1))); // При однаковому a менше b має бути першим.
        java.util.ArrayList<Function> mixed = new java.util.ArrayList<>(java.util.List.of(new Hyperbola(1,1),new Ellipse(2,1))); // Типи подано у зворотному порядку.
        java.util.Collections.sort(mixed,new FunctionComparator()); // Сортуємо через зовнішній Comparator.
        check(mixed.get(0) instanceof Ellipse); // Назва Ellipse стоїть перед Hyperbola.
        check(Main.load(java.nio.file.Path.of("data/ellipses.txt"),true).size() == 3); // Перевіряємо формат демонстраційного файлу.
        System.out.println("OK: " + count + " перевірок"); // Видимий підсумок після успішного виконання.
    }
}

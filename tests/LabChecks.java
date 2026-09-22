// class описує тип об’єктів; new створює конкретний об’єкт і викликає його конструктор. public робить клас
// доступним ззовні; final у заголовку класу, якщо він є, забороняє створювати підкласи, але сам по собі не робить
// поля незмінними.
public class LabChecks { // Автономні перевірки без зовнішніх бібліотек тестування.
    private static int count = 0; // Лічильник успішно перевірених умов.
    // static означає, що метод належить класу: його можна викликати без створення об’єкта. public дозволяє виклик
    // з інших класів, private обмежує використання цим класом; тип перед назвою задає результат, а void означає
    // відсутність значення для повернення.
    private static void check(boolean value) { // Допоміжний метод перетворює хибну умову на провал тесту.
        // throw — аналог raise у Python: негайно припиняємо звичайний хід методу й передаємо об’єкт помилки
        // найближчому відповідному catch. new створює виняток, а текст конструктора пояснює причину користувачу.
        // AssertionError тут явно позначає провал перевірки: тест зупиниться і покаже причину. На відміну від
        // ключового слова assert у Java, такий throw працює завжди, навіть без прапорця запуску -ea.
        if (!value) throw new AssertionError("Перевірка " + (count + 1) + " не пройшла."); // Зупиняємо тест із ненульовим кодом процесу.
        count++; // Рахуємо лише успішні перевірки.
    }
    private static void near(double actual, double expected) { // Порівнюємо double з допуском на округлення.
        // double зберігає двійкове наближення, тому очікуємо малу похибку, а не точне ==. Беремо більшу з
        // абсолютної межі 1e-9 та відносної 1e-9*abs(expected), приблизно як math.isclose з відповідними допусками
        // у Python.
        check(Math.abs(actual - expected) <= 1e-9 * Math.max(1, Math.abs(expected))); // Допуск враховує масштаб очікуваного числа.
    }
    // throws у заголовку попереджає викликача про перевірюваний виняток: Java вимагає його перехопити або теж
    // оголосити throws. Це не команда кинути помилку — її кидає throw усередині; у Python такої обов’язкової
    // декларації немає.
    // Цей маленький інтерфейс описує одну дію run(), тому його можна передати лямбдою () -> ... . throws Exception
    // дозволяє тестувати й методи з перевірюваними винятками; сама дія виконається всередині expect, де її помилка
    // буде перевірена.
    private interface Action { void run() throws Exception; } // Лямбда тесту може породжувати і перевірювані винятки.
    // Class зберігає опис типу, наприклад ArithmeticException.class. ? extends Throwable означає «якийсь тип
    // помилки, що успадковує Throwable»; isInstance перевіряє відповідність, як isinstance(error, expected_type) у
    // Python.
    // Знак ? тут позначає невідомий конкретний тип у параметрі Class, а не вибір через ? :; extends обмежує його
    // підкласами Throwable.
    private static void expect(Class<? extends Throwable> type, Action action) { // Перевіряємо, що помилкові дані дають саме потрібний вид помилки.
        try { action.run(); } // Виконуємо потенційно помилкову операцію.
        // catch — аналог except у Python: ця гілка виконується лише після відповідної помилки в try. e — об’єкт
        // винятку, getMessage() дає його пояснення; вертикальна риска між типами дозволяє одним блоком обробити
        // кілька видів помилок.
        catch (Throwable error) { check(type.isInstance(error)); return; } // Неправильний тип винятку теж провалює тест.
        throw new AssertionError("Очікували " + type.getSimpleName()); // Відсутність потрібного винятку є помилкою реалізації.
    }
    // public дозволяє Java знайти точку входу; static означає виклик без new Main(); void означає, що метод не
    // повертає значення. String[] args — масив аргументів запуску без назви програми (на відміну від Python
    // sys.argv). Тут починається виконання, приблизно як у блоці if __name__ == "__main__" у Python.
    public static void main(String[] args) throws Exception { // Метод запускає усі перевірки цієї лабораторної.
        // HashSet — множина унікальних значень, схожа на Python set; порядок перебору не гарантований. <Integer>
        // потрібен замість <int>, бо колекції Java містять об’єкти: упаковка int в Integer відбувається
        // автоматично.
        // of(...) створює незмінювану колекцію: додавання або видалення після створення спричинить
        // UnsupportedOperationException. Для змінної копії її передають у new ArrayList<>(...) чи new
        // HashSet<>(...).
        java.util.HashSet<Integer> a = new java.util.HashSet<>(java.util.List.of(1,2,2)); // HashSet усуває повторне число.
        java.util.HashSet<Integer> b = new java.util.HashSet<>(java.util.List.of(2,3)); // Друга множина має один спільний елемент.
        // equals(...) порівнює вміст за правилом відповідного класу. Для рядків не слід писати ==, бо в Java він
        // порівнює посилання; Python == для рядків уже порівнює текст. Для власних об’єктів правило equals
        // визначено в їхньому класі.
        check(Main.intersection(a,b).equals(java.util.Set.of(2))); // Очікуємо лише спільне число два.
        check(Main.union(a,b).equals(java.util.Set.of(1,2,3))); // Перевіряємо об'єднання.
        check(a.equals(java.util.Set.of(1,2))); // Операції не повинні змінити початкову множину.
        // ArrayList — список, що може змінювати довжину, найближчий тут до Python list. Тип у <...> обмежує
        // допустимі елементи під час компіляції; <> після new означає «вивести цей тип із контексту». Конструктор
        // із колекцією копіює список посилань, а не самі об’єкти.
        java.util.ArrayList<Ellipse> list = new java.util.ArrayList<>(java.util.List.of(new Ellipse(5,2),new Ellipse(2,3),new Ellipse(2,1))); // Дані перевіряють головний і додатковий ключі.
        // Collections.sort змінює порядок самого списку, як list.sort() у Python. Без другого аргументу
        // викликається compareTo елементів (Comparable); з переданим Comparator використовується його compare.
        // Рішення про порядок задає знак результату порівняння.
        java.util.Collections.sort(list); // Сортуємо через Comparable.
        check(list.get(0).equals(new Ellipse(2,1))); // При однаковому a менше b має бути першим.
        java.util.ArrayList<Function> mixed = new java.util.ArrayList<>(java.util.List.of(new Hyperbola(1,1),new Ellipse(2,1))); // Типи подано у зворотному порядку.
        java.util.Collections.sort(mixed,new FunctionComparator()); // Сортуємо через зовнішній Comparator.
        check(mixed.get(0) instanceof Ellipse); // Назва Ellipse стоїть перед Hyperbola.
        // Path — об’єкт шляху, приблизно pathlib.Path у Python. Path.of тільки розбирає запис шляху, а не створює
        // файл; відносний шлях рахується від робочої папки запущеної програми.
        check(Main.load(java.nio.file.Path.of("data/ellipses.txt"),true).size() == 3); // Перевіряємо формат демонстраційного файлу.
        System.out.println("OK: " + count + " перевірок"); // Видимий підсумок після успішного виконання.
    }
}

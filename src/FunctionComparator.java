import java.util.Comparator;
// final class — від цього класу не можна успадковуватися.
// implements — клас реалізує методи зазначеного інтерфейсу.
public final class FunctionComparator implements Comparator<Function> {
    // @Override — компілятор перевіряє, що метод перевизначає успадкований або реалізує інтерфейс.
    @Override public int compare(Function left, Function right) { // Сортуємо спочатку за типом, потім за a і b.
        // getClass().getSimpleName() — коротка назва фактичного класу.
        int type = left.getClass().getSimpleName().compareTo(right.getClass().getSimpleName());
        if (type != 0) return type; // Якщо типи різні, додаткові параметри вже не потрібні.
        // compare повертає знак порядку: від’ємне / 0 / додатне; його використовує сортування.
        int axis = Double.compare(left.getA(), right.getA());
        // умова ? a : b — вибір значення: a, якщо true, інакше b.
        return axis != 0 ? axis : Double.compare(left.getB(), right.getB());
    }
}

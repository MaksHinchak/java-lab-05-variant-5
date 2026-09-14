import java.util.Comparator; // Comparator задає окремий порядок для різнорідних функцій.
public final class FunctionComparator implements Comparator<Function> { // Порівнювач працює з базовим абстрактним типом.
    @Override public int compare(Function left, Function right) { // Сортуємо спочатку за типом, потім за a і b.
        int type = left.getClass().getSimpleName().compareTo(right.getClass().getSimpleName()); // Назви Ellipse і Hyperbola визначають порядок груп.
        if (type != 0) return type; // Якщо типи різні, додаткові параметри вже не потрібні.
        int axis = Double.compare(left.getA(), right.getA()); // Усередині типу порівнюємо першу піввісь.
        return axis != 0 ? axis : Double.compare(left.getB(), right.getB()); // При однаковому a порівнюємо другу піввісь.
    }
}

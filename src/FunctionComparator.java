import java.util.Comparator;
public final class FunctionComparator implements Comparator<Function> {
    @Override public int compare(Function left, Function right) {
        int type = left.getClass().getSimpleName().compareTo(right.getClass().getSimpleName());
        if (type != 0) return type;
        int axis = Double.compare(left.getA(), right.getA());
        return axis != 0 ? axis : Double.compare(left.getB(), right.getB());
    }
}

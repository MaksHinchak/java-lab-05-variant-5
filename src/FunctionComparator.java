import java.util.Comparator;

public final class FunctionComparator implements Comparator<Function> {
    @Override
    public int compare(Function left, Function right) {
        String leftType = left.getClass().getSimpleName();
        String rightType = right.getClass().getSimpleName();
        int order = leftType.compareTo(rightType);

        if (order == 0) {
            return left.compareAxes(right);
        }
        return order;
    }
}

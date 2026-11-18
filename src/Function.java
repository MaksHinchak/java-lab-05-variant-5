import java.util.Objects;

public abstract class Function {
    static final double MAX_AXIS = 1e100;

    protected final double a;
    protected final double b;

    protected Function(double a, double b) {
        if (!isValidAxis(a) || !isValidAxis(b)) {
            throw new IllegalArgumentException("Півосі мають бути більшими за 0 і не більшими за " + MAX_AXIS + ".");
        }
        this.a = a;
        this.b = b;
    }

    static boolean isValidAxis(double axis) {
        return axis > 0 && axis <= MAX_AXIS;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public abstract double value(double x);

    public void print(double x) {
        System.out.println(this + "; x=" + x + "; y=" + value(x));
    }

    protected int compareAxes(Function other) {
        int order = Double.compare(a, other.a);
        if (order != 0) {
            return order;
        }
        return Double.compare(b, other.b);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(a=" + a + ", b=" + b + ")";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Function function
                && getClass() == function.getClass()
                && a == function.a
                && b == function.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), a, b);
    }
}

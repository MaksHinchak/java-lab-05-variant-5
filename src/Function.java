import java.util.Objects;
public abstract class Function {
    protected final double a;
    protected final double b;
    protected Function(double a, double b) {
        if (!Double.isFinite(a) || !Double.isFinite(b) || a <= 0 || b <= 0 || a > 1e100 || b > 1e100) throw new IllegalArgumentException("Півосі мають бути в (0; 1e100].");
        this.a = a;
        this.b = b;
    }
    public double getA() { return a; }
    public double getB() { return b; }
    public abstract double value(double x);
    public abstract void print(double x);
    @Override public abstract String toString();
    @Override public abstract boolean equals(Object other);
    @Override public int hashCode() { return Objects.hash(getClass(), a, b); }
}

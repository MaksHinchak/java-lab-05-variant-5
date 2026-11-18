import java.math.BigDecimal;
import java.math.MathContext;

public final class Hyperbola extends Function implements Comparable<Hyperbola> {
    public Hyperbola(double a, double b) {
        super(a, b);
    }

    @Override
    public double value(double x) {
        double absX = Math.abs(x);
        if (!Double.isFinite(absX) || absX < a) {
            throw new IllegalArgumentException("x поза областю визначення Hyperbola.");
        }

        BigDecimal distance = new BigDecimal(absX);
        BigDecimal axis = new BigDecimal(a);
        BigDecimal radicand = distance.subtract(axis).multiply(distance.add(axis));
        BigDecimal root = radicand.sqrt(MathContext.DECIMAL128);
        double result = root.multiply(new BigDecimal(b))
                .divide(axis, MathContext.DECIMAL128)
                .doubleValue();

        if (!Double.isFinite(result)) {
            throw new ArithmeticException("Переповнення обчислення функції.");
        }
        return result;
    }

    @Override
    public int compareTo(Hyperbola other) {
        return compareAxes(other);
    }
}

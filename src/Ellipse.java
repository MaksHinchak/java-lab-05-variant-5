public final class Ellipse extends Function implements Comparable<Ellipse> {
    public Ellipse(double a, double b) {
        super(a, b);
    }

    @Override
    public double value(double x) {
        if (!Double.isFinite(x) || Math.abs(x) > a) {
            throw new IllegalArgumentException("x поза областю визначення Ellipse.");
        }

        double ratio = x / a;
        return b * Math.sqrt(1 - ratio * ratio);
    }

    @Override
    public int compareTo(Ellipse other) {
        return compareAxes(other);
    }
}

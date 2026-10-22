public final class Hyperbola extends Function implements Comparable<Hyperbola> {
    public Hyperbola(double a, double b) { super(a, b); }
    @Override public double value(double x) {
        if (!Double.isFinite(x) || Math.abs(x) < a) throw new IllegalArgumentException("x поза областю визначення Hyperbola.");
        double result = b * Math.sqrt((Math.abs(x) / a - 1) * (Math.abs(x) / a + 1));
        if (!Double.isFinite(result)) throw new ArithmeticException("Переповнення обчислення функції.");
        return result;
    }
    @Override public void print(double x) { System.out.println(this + "; x=" + x + "; y=" + value(x)); }
    @Override public String toString() { return "Hyperbola(a=" + a + ", b=" + b + ")"; }
    @Override public boolean equals(Object other) { return other instanceof Hyperbola f && Double.compare(a, f.a) == 0 && Double.compare(b, f.b) == 0; }
    @Override public int compareTo(Hyperbola other) {
        int first = Double.compare(a, other.a);
        return first != 0 ? first : Double.compare(b, other.b);
    }
}

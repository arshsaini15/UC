package UC;

import java.util.function.DoubleBinaryOperator;

public class UC13 {

    public static void main(String[] args) {

        Quantity<LengthScale> l1 = new Quantity<>(10, LengthScale.FEET);
        Quantity<LengthScale> l2 = new Quantity<>(6, LengthScale.INCH);

        System.out.println("Subtract: " + l1.subtract(l2));
        System.out.println("Subtract (INCH): " + l1.subtract(l2, LengthScale.INCH));
        System.out.println("Reverse Subtract: " + l2.subtract(l1));

        System.out.println("Divide: " + l1.divide(new Quantity<>(2, LengthScale.FEET)));

        Quantity<WeightScale> w1 = new Quantity<>(10, WeightScale.KILOGRAM);
        Quantity<WeightScale> w2 = new Quantity<>(5000, WeightScale.GRAM);

        System.out.println("Weight Add: " + w1.add(w2, WeightScale.GRAM));

        Quantity<CapacityScale> v1 = new Quantity<>(5, CapacityScale.LITRE);
        Quantity<CapacityScale> v2 = new Quantity<>(2, CapacityScale.LITRE);

        System.out.println("Volume Divide: " + v1.divide(v2));
    }
}

interface MeasurementScale {

    double factor();

    default double toBase(double value) {
        return value * factor();
    }

    default double fromBase(double base) {
        return base / factor();
    }

    String symbol();
}

enum LengthScale implements MeasurementScale {
    FEET(1.0), INCH(1.0 / 12), YARD(3.0), CM(1.0 / 30.48);

    private final double f;

    LengthScale(double f) {
        this.f = f;
    }

    public double factor() {
        return f;
    }

    public String symbol() {
        return name();
    }
}

enum WeightScale implements MeasurementScale {
    KILOGRAM(1.0), GRAM(0.001), POUND(0.453592);

    private final double f;

    WeightScale(double f) {
        this.f = f;
    }

    public double factor() {
        return f;
    }

    public String symbol() {
        return name();
    }
}

enum CapacityScale implements MeasurementScale {
    LITRE(1.0), MILLILITRE(0.001), GALLON(3.78541);

    private final double f;

    CapacityScale(double f) {
        this.f = f;
    }

    public double factor() {
        return f;
    }

    public String symbol() {
        return name();
    }
}

enum OperationKind {

    ADD((a, b) -> a + b),

    SUBTRACT((a, b) -> a - b),

    DIVIDE((a, b) -> {
        if (Math.abs(b) < 1e-9)
            throw new ArithmeticException();
        return a / b;
    });

    private final DoubleBinaryOperator op;

    OperationKind(DoubleBinaryOperator op) {
        this.op = op;
    }

    double apply(double a, double b) {
        return op.applyAsDouble(a, b);
    }
}

class Quantity<U extends MeasurementScale> {

    private final double value;
    private final U unit;
    private static final double EPS = 1e-6;

    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException();
        if (!Double.isFinite(value)) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U target) {
        validate(other, target, true);
        double base = compute(other, OperationKind.ADD);
        return new Quantity<>(round(target.fromBase(base)), target);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U target) {
        validate(other, target, true);
        double base = compute(other, OperationKind.SUBTRACT);
        return new Quantity<>(round(target.fromBase(base)), target);
    }

    public double divide(Quantity<U> other) {
        validate(other, null, false);
        return compute(other, OperationKind.DIVIDE);
    }

    private void validate(Quantity<U> other, U target, boolean needTarget) {
        if (other == null) throw new IllegalArgumentException();
        if (!unit.getClass().equals(other.unit.getClass())) throw new IllegalArgumentException();
        if (!Double.isFinite(value) || !Double.isFinite(other.value)) throw new IllegalArgumentException();
        if (needTarget) {
            if (target == null) throw new IllegalArgumentException();
            if (!unit.getClass().equals(target.getClass())) throw new IllegalArgumentException();
        }
    }

    private double compute(Quantity<U> other, OperationKind op) {
        double a = unit.toBase(value);
        double b = other.unit.toBase(other.value);
        return op.apply(a, b);
    }

    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> q)) return false;
        if (!unit.getClass().equals(q.unit.getClass())) return false;
        double a = unit.toBase(value);
        double b = q.unit.toBase(q.value);
        return Math.abs(a - b) < EPS;
    }

    @Override
    public int hashCode() {
        double base = unit.toBase(value);
        return Double.hashCode(Math.round(base / EPS));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.symbol() + ")";
    }
}
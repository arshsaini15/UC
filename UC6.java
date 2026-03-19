package UC;

public class UC6 {


    public enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.393701 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double valueInFeet) {
            return valueInFeet / toFeetFactor;
        }
    }


    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }


        public QuantityLength convertTo(LengthUnit targetUnit) {
            double base = this.unit.toFeet(this.value);
            double converted = targetUnit.fromFeet(base);
            return new QuantityLength(converted, targetUnit);
        }

        public static double convert(double value, LengthUnit from, LengthUnit to) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (from == null || to == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }

            double base = from.toFeet(value);
            return to.fromFeet(base);
        }

        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException("Other length cannot be null");
            }

            double thisFeet = this.unit.toFeet(this.value);
            double otherFeet = other.unit.toFeet(other.value);

            double sumFeet = thisFeet + otherFeet;

            double result = this.unit.fromFeet(sumFeet);

            return new QuantityLength(result, this.unit);
        }


        public static QuantityLength add(QuantityLength q1, QuantityLength q2) {
            return q1.add(q2);
        }


        public static QuantityLength add(double v1, LengthUnit u1,
                                         double v2, LengthUnit u2) {
            QuantityLength q1 = new QuantityLength(v1, u1);
            QuantityLength q2 = new QuantityLength(v2, u2);
            return q1.add(q2);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double thisFeet = this.unit.toFeet(this.value);
            double otherFeet = other.unit.toFeet(other.value);

            return Double.compare(thisFeet, otherFeet) == 0;
        }

        // ===== toString =====
        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // ===== MAIN METHOD =====
    public static void main(String[] args) {

        try {
            // ===== UC5 Conversion =====
            System.out.println("Conversion:");
            System.out.println("1 FEET → INCHES = " +
                    QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES));

            QuantityLength q = new QuantityLength(2.0, LengthUnit.YARDS);
            System.out.println("2 YARDS → INCHES = " + q.convertTo(LengthUnit.INCHES));

            // ===== UC3/UC4 Equality =====
            System.out.println("\nEquality:");
            QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
            QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);
            System.out.println(q1 + " == " + q2 + " → " + q1.equals(q2));

            // ===== UC6 Addition =====
            System.out.println("\nAddition:");

            QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
            QuantityLength b = new QuantityLength(12.0, LengthUnit.INCHES);

            System.out.println("1 ft + 12 in = " + a.add(b)); // 2 ft
            System.out.println("12 in + 1 ft = " + b.add(a)); // 24 in

            QuantityLength c = new QuantityLength(1.0, LengthUnit.YARDS);
            QuantityLength d = new QuantityLength(3.0, LengthUnit.FEET);

            System.out.println("1 yard + 3 ft = " + c.add(d));

            QuantityLength e = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
            QuantityLength f = new QuantityLength(1.0, LengthUnit.INCHES);

            System.out.println("2.54 cm + 1 in = " + e.add(f));

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
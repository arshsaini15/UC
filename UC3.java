package UC;

public class UC3 {

    // Step 1: Enum for units
    public enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }
    }

    // Step 2: Single Quantity class
    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double thisInFeet = this.unit.toFeet(this.value);
            double otherInFeet = other.unit.toFeet(other.value);

            return Double.compare(thisInFeet, otherInFeet) == 0;
        }
    }

    public static void main(String[] args) {

        try {
            QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
            QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

            QuantityLength q3 = new QuantityLength(1.0, LengthUnit.INCHES);
            QuantityLength q4 = new QuantityLength(1.0, LengthUnit.INCHES);

            System.out.println("1 ft vs 12 inch → " + q1.equals(q2)); // true
            System.out.println("1 inch vs 1 inch → " + q3.equals(q4)); // true

        } catch (Exception e) {
            System.out.println("Error occurred");
        }
    }
}
package UC;

public class UC4 {

    // Updated Enum (ONLY CHANGE FROM UC3)
    public enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.393701 / 12.0); // convert cm → inches → feet

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }
    }

    // SAME CLASS (no change from UC3)
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
            // Yard tests
            QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
            QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);
            QuantityLength q3 = new QuantityLength(36.0, LengthUnit.INCHES);

            // CM tests
            QuantityLength q4 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
            QuantityLength q5 = new QuantityLength(0.393701, LengthUnit.INCHES);

            System.out.println("1 yard vs 3 feet → " + q1.equals(q2)); // true
            System.out.println("1 yard vs 36 inches → " + q1.equals(q3)); // true
            System.out.println("1 cm vs 0.393701 inch → " + q4.equals(q5)); // true

        } catch (Exception e) {
            System.out.println("Error occurred");
        }
    }
}
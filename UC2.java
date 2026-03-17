package UC;

public class UC2 {

    // Feet class
    public static class Feet {
        private final double value;

        Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Inches class
    public static class Inches {
        private final double value;

        Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    public static boolean compareFeet(double v1, double v2) {
        Feet f1 = new Feet(v1);
        Feet f2 = new Feet(v2);
        return f1.equals(f2);
    }

    public static boolean compareInches(double v1, double v2) {
        Inches i1 = new Inches(v1);
        Inches i2 = new Inches(v2);
        return i1.equals(i2);
    }

    public static void main(String[] args) {

        try {

            double ft1 = 1.0;
            double ft2 = 1.0;

            double in1 = 1.0;
            double in2 = 2.0;

            boolean feetResult = compareFeet(ft1, ft2);
            boolean inchResult = compareInches(in1, in2);

            System.out.println("Feet Comparison → " + (feetResult ? "Equal (true)" : "Not Equal (false)"));
            System.out.println("Inch Comparison → " + (inchResult ? "Equal (true)" : "Not Equal (false)"));

        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
}
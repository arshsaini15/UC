package UC;

import java.util.*;

public class UC1 {
    public static class Feet {
        private final double s1;

        Feet(double s1) {
            this.s1 = s1;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;

            return Double.compare(this.s1, other.s1) == 0;
        }
    }

    public static void main(String[] args) {

        try {
            Scanner sc = new Scanner(System.in);

            double v1 = sc.nextDouble();
            double v2 = sc.nextDouble();

            UC1.Feet d1 = new Feet(v1);
            UC1.Feet d2 = new Feet(v2);

            boolean result = d1.equals(d2);

            System.out.println(result ? "Equal (true)" : "Not Equal (false)");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter numeric values.");
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

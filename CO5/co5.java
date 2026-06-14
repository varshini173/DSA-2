
public class co5 {

    static class Delivery {
        int over;
        int ball;

        Delivery(int over, int ball) {
            this.over = over;
            this.ball = ball;
        }

        public String toString() {
            return "(" + over + "," + ball + ")";
        }
    }

    static Delivery[] countingSortByBall(Delivery[] arr) {

        int K = 12;

        int[] count = new int[K + 1];

        for (Delivery d : arr)
            count[d.ball]++;

        for (int i = 1; i <= K; i++)
            count[i] += count[i - 1];

        Delivery[] out = new Delivery[arr.length];

        for (int i = arr.length - 1; i >= 0; i--) {
            Delivery d = arr[i];
            out[--count[d.ball]] = d;
        }

        return out;
    }

    static Delivery[] countingSortByOver(Delivery[] arr) {

        int K = 50;

        int[] count = new int[K + 1];

        for (Delivery d : arr)
            count[d.over]++;

        for (int i = 1; i <= K; i++)
            count[i] += count[i - 1];

        Delivery[] out = new Delivery[arr.length];

        for (int i = arr.length - 1; i >= 0; i--) {
            Delivery d = arr[i];
            out[--count[d.over]] = d;
        }

        return out;
    }

    static void printArray(Delivery[] arr) {

        for (Delivery d : arr)
            System.out.print(d + " ");

        System.out.println();
    }

    public static void main(String[] args) {

        Delivery[] deliveries = {
                new Delivery(2,4),
                new Delivery(1,1),
                new Delivery(3,6),
                new Delivery(1,5),
                new Delivery(2,2),
                new Delivery(3,1),
                new Delivery(1,3),
                new Delivery(2,6),
                new Delivery(3,4),
                new Delivery(1,2)
        };

        System.out.println("CRICKET DELIVERY SORTING");
        System.out.println();

        System.out.println("INPUT");
        printArray(deliveries);

        Delivery[] pass1 = countingSortByBall(deliveries);

        System.out.println();
        System.out.println("PASS 1 : SORT BY BALL");
        printArray(pass1);

        Delivery[] pass2 = countingSortByOver(pass1);

        System.out.println();
        System.out.println("PASS 2 : STABLE SORT BY OVER");
        printArray(pass2);

        System.out.println();
        System.out.println("FINAL SORTED ORDER");
        printArray(pass2);

        System.out.println();
        System.out.println("TIME COMPLEXITY");
        System.out.println("O(n + K)");

        System.out.println();
        System.out.println("SPACE COMPLEXITY");
        System.out.println("O(n + K)");

        System.out.println();
        System.out.println("STABILITY");
        System.out.println("Reverse traversal preserves relative order of equal keys.");

        System.out.println();
        System.out.println("SEASON ANALYSIS");
        System.out.println("Deliveries = 1,000,000");
        System.out.println("Over Range = 0..49");
        System.out.println("Ball Range = 1..12");
        System.out.println("Counting Sort is ideal because K is very small.");
    }
}

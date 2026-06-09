import java.util.Arrays;

public class co5 {

    static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void dutchFlag(char[] arr) {

        int low = 0;
        int mid = 0;
        int high = arr.length - 1;

        int step = 1;

        System.out.println("DUTCH NATIONAL FLAG PARTITION\n");

        System.out.println("Initial Array:");
        System.out.println(Arrays.toString(arr));
        System.out.println();

        System.out.printf(
                "%-5s %-25s %-5s %-5s %-5s%n",
                "Step",
                "Array State",
                "Low",
                "Mid",
                "High");

        while (mid <= high) {

            if (arr[mid] == 'S') {

                swap(arr, low, mid);
                low++;
                mid++;

            } else if (arr[mid] == 'P') {

                mid++;

            } else {

                swap(arr, mid, high);
                high--;
            }

            System.out.printf(
                    "%-5d %-25s %-5d %-5d %-5d%n",
                    step++,
                    Arrays.toString(arr),
                    low,
                    mid,
                    high);
        }

        System.out.println("\nFINAL PARTITIONED ARRAY");
        System.out.println(Arrays.toString(arr));

        System.out.println("\nREGIONS");

        int success = 0;
        int pending = 0;
        int failed = 0;

        for (char c : arr) {
            if (c == 'S')
                success++;
            else if (c == 'P')
                pending++;
            else
                failed++;
        }

        System.out.println(
                "SUCCESS = " + success);

        System.out.println(
                "PENDING = " + pending);

        System.out.println(
                "FAILED  = " + failed);
    }

    public static void main(String[] args) {

        char[] bookings = {
                'F', 'S', 'P', 'F',
                'S', 'S', 'P', 'F',
                'P', 'S', 'F', 'P'
        };

        System.out.println(
                "IRCTC TATKAL BOOKING STATUS PARTITIONING\n");

        dutchFlag(bookings);

        System.out.println("\nTIME COMPLEXITY");
        System.out.println("O(n)");

        System.out.println("\nSPACE COMPLEXITY");
        System.out.println("O(1)");

        System.out.println("\n3-WAY QUICKSORT ANALYSIS");

        int n = 50000;
        int k = 3;

        double normal =
                n * (Math.log(n) / Math.log(2));

        double threeWay =
                n * (Math.log(k) / Math.log(2));

        double speedup =
                normal / threeWay;

        System.out.printf(
                "Standard Quicksort Work : %.0f%n",
                normal);

        System.out.printf(
                "3-Way Quicksort Work    : %.0f%n",
                threeWay);

        System.out.printf(
                "Approx Speedup          : %.2fx%n",
                speedup);
    }
}
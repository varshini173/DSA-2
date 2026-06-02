public class co2 {

    static class FenwickTree {
        int[] bit;
        int n;

        FenwickTree(int n) {
            this.n = n;
            bit = new int[n + 1];
        }

        void update(int index, int value) {
            while (index <= n) {
                bit[index] += value;
                index += index & (-index);
            }
        }

        int prefixSum(int index) {
            int sum = 0;
            while (index > 0) {
                sum += bit[index];
                index -= index & (-index);
            }
            return sum;
        }

        int rangeSum(int left, int right) {
            return prefixSum(right) - prefixSum(left - 1);
        }
    }

    public static void main(String[] args) {

        int sales[] = {10, 20, 30, 40, 50, 60, 70, 80};

        FenwickTree ft = new FenwickTree(sales.length);

        System.out.println("FENWICK TREE SALES ANALYTICS\n");

        System.out.print("Initial Product Sales:\n[");
        for (int i = 0; i < sales.length; i++) {
            System.out.print(sales[i]);
            if (i < sales.length - 1)
                System.out.print(", ");
        }
        System.out.println("]\n");

        for (int i = 0; i < sales.length; i++) {
            ft.update(i + 1, sales[i]);
        }

        System.out.println("Fenwick Tree Constructed Successfully\n");

        System.out.println("Queries Performed:\n");

        System.out.println("1) Prefix Sum upto Product 4");
        System.out.println("Result = " + ft.prefixSum(4));

        System.out.println("\n2) Range Sum (Product 2 to Product 6)");
        System.out.println("Result = " + ft.rangeSum(2, 6));

        System.out.println("\nSales Update:");
        System.out.println("Product 5 += 25");

        sales[4] += 25;
        ft.update(5, 25);

        System.out.print("\nUpdated Product Sales:\n[");
        for (int i = 0; i < sales.length; i++) {
            System.out.print(sales[i]);
            if (i < sales.length - 1)
                System.out.print(", ");
        }
        System.out.println("]\n");

        System.out.println("3) Prefix Sum upto Product 5");
        System.out.println("Result = " + ft.prefixSum(5));

        System.out.println("\n4) Range Sum (Product 3 to Product 7)");
        System.out.println("Result = " + ft.rangeSum(3, 7));

        System.out.println("\nTime Complexity:");
        System.out.println("Update = O(log n)");
        System.out.println("Prefix Sum Query = O(log n)");
        System.out.println("Range Sum Query = O(log n)");
    }
}

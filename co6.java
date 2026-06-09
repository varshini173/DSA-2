import java.util.*;

public class co6 {

    static int[][] split;

    static int matrixChainOrder(int[] p) {

        int n = p.length - 1;

        int[][] m = new int[n + 1][n + 1];

        split = new int[n + 1][n + 1];

        for (int len = 2; len <= n; len++) {

            for (int i = 1; i <= n - len + 1; i++) {

                int j = i + len - 1;

                m[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {

                    int cost =
                            m[i][k]
                            + m[k + 1][j]
                            + p[i - 1] * p[k] * p[j];

                    if (cost < m[i][j]) {

                        m[i][j] = cost;

                        split[i][j] = k;
                    }
                }
            }
        }

        System.out.println("MCM DP TABLE\n");

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= n; j++) {

                if (j < i)
                    System.out.print("-\t");
                else
                    System.out.print(m[i][j] + "\t");
            }

            System.out.println();
        }

        return m[1][n];
    }

    static String printParens(
            int[][] s,
            int i,
            int j) {

        if (i == j)
            return "M" + i;

        int k = s[i][j];

        return "("
                + printParens(s, i, k)
                + " x "
                + printParens(s, k + 1, j)
                + ")";
    }

    public static void main(String[] args) {

        int[] p = {
                10,
                30,
                5,
                60,
                10,
                20
        };

        System.out.println(
                "SQL QUERY OPTIMIZER - MATRIX CHAIN MULTIPLICATION\n");

        System.out.println(
                "Dimensions:");

        System.out.println(
                Arrays.toString(p));

        int minCost =
                matrixChainOrder(p);

        System.out.println(
                "\nOPTIMAL PARENTHESIZATION");

        System.out.println(
                printParens(
                        split,
                        1,
                        p.length - 2));

        System.out.println(
                "\nMINIMUM SCALAR MULTIPLICATIONS");

        System.out.println(
                minCost);

        System.out.println(
                "\nTIME COMPLEXITY");

        System.out.println(
                "O(n^3)");

        System.out.println(
                "\nSPACE COMPLEXITY");

        System.out.println(
                "O(n^2)");

        int tables = 30;

        long operations =
                (long) tables
                * tables
                * tables;

        System.out.println(
                "\nIRCTC SCALE ANALYSIS");

        System.out.println(
                "Tables = 30");

        System.out.println(
                "Approx DP Operations = "
                        + operations);
    }
}
import java.util.*;

class PSegNode {
    long sum;
    PSegNode left, right;

    PSegNode(long s, PSegNode l, PSegNode r) {
        sum = s;
        left = l;
        right = r;
    }
}

public class co2 {

    static int newNodesCreated = 0;

    // Build initial tree
    static PSegNode build(int[] arr, int lo, int hi) {

        if (lo == hi) {
            return new PSegNode(arr[lo], null, null);
        }

        int mid = (lo + hi) / 2;

        PSegNode left = build(arr, lo, mid);
        PSegNode right = build(arr, mid + 1, hi);

        return new PSegNode(
                left.sum + right.sum,
                left,
                right
        );
    }

    // Persistent Update
    static PSegNode pointUpdate(
            PSegNode node,
            int lo,
            int hi,
            int idx,
            long newVal) {

        newNodesCreated++;

        if (lo == hi) {
            return new PSegNode(
                    newVal,
                    null,
                    null
            );
        }

        int mid = (lo + hi) / 2;

        if (idx <= mid) {

            PSegNode newLeft =
                    pointUpdate(
                            node.left,
                            lo,
                            mid,
                            idx,
                            newVal
                    );

            return new PSegNode(
                    newLeft.sum + node.right.sum,
                    newLeft,
                    node.right      // shared
            );
        }
        else {

            PSegNode newRight =
                    pointUpdate(
                            node.right,
                            mid + 1,
                            hi,
                            idx,
                            newVal
                    );

            return new PSegNode(
                    node.left.sum + newRight.sum,
                    node.left,      // shared
                    newRight
            );
        }
    }

    static long rangeSum(
            PSegNode node,
            int lo,
            int hi,
            int l,
            int r) {

        if (r < lo || hi < l)
            return 0;

        if (l <= lo && hi <= r)
            return node.sum;

        int mid = (lo + hi) / 2;

        return rangeSum(
                node.left,
                lo,
                mid,
                l,
                r
        ) +
        rangeSum(
                node.right,
                mid + 1,
                hi,
                l,
                r
        );
    }

    static void printResults(
            String version,
            PSegNode root) {

        System.out.println(
                version +
                " Root Sum = " +
                root.sum
        );
    }

    public static void main(String[] args) {

        int stock[] = {
                12, 7, 25, 18,
                9, 14, 6, 30
        };

        System.out.println(
                "AMAZON WAREHOUSE INVENTORY SNAPSHOTS\n"
        );

        System.out.println(
                "Initial Stock:"
        );

        System.out.println(
                Arrays.toString(stock)
        );

        // v0
        PSegNode v0 =
                build(stock, 0, 7);

        printResults("v0", v0);

        // Update 1
        // stock[3] += 50
        stock[2] += 50;

        newNodesCreated = 0;

        PSegNode v1 =
                pointUpdate(
                        v0,
                        0,
                        7,
                        2,
                        stock[2]
                );

        System.out.println(
                "\nUpdate (i): stock[3] += 50"
        );

        printResults("v1", v1);

        System.out.println(
                "New Nodes Created = "
                + newNodesCreated
        );

        // Update 2
        // stock[6] -= 4
        stock[5] -= 4;

        newNodesCreated = 0;

        PSegNode v2 =
                pointUpdate(
                        v1,
                        0,
                        7,
                        5,
                        stock[5]
                );

        System.out.println(
                "\nUpdate (ii): stock[6] -= 4"
        );

        printResults("v2", v2);

        System.out.println(
                "New Nodes Created = "
                + newNodesCreated
        );

        // Update 3
        // stock[3] -= 12
        stock[2] -= 12;

        newNodesCreated = 0;

        PSegNode v3 =
                pointUpdate(
                        v2,
                        0,
                        7,
                        2,
                        stock[2]
                );

        System.out.println(
                "\nUpdate (iii): stock[3] -= 12"
        );

        printResults("v3", v3);

        System.out.println(
                "New Nodes Created = "
                + newNodesCreated
        );

        System.out.println(
                "\nRANGE SUM QUERY"
        );

        System.out.println(
                "Categories 3..6"
        );

        System.out.println(
                "v0 : "
                + rangeSum(v0,0,7,2,5)
        );

        System.out.println(
                "v1 : "
                + rangeSum(v1,0,7,2,5)
        );

        System.out.println(
                "v2 : "
                + rangeSum(v2,0,7,2,5)
        );

        System.out.println(
                "v3 : "
                + rangeSum(v3,0,7,2,5)
        );

        System.out.println(
                "\nMEMORY ANALYSIS"
        );

        System.out.println(
                "Original Tree Nodes = 15"
        );

        System.out.println(
                "New Nodes Per Update = 4"
        );

        System.out.println(
                "Total Nodes = 15 + (3 × 4)"
        );

        System.out.println(
                "= 27"
        );
    }
}

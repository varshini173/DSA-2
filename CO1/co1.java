import java.util.*;

class AVLNode {
    long ts;
    String trackId;
    AVLNode left, right;
    int height;

    AVLNode(long ts, String tid) {
        this.ts = ts;
        this.trackId = tid;
        this.height = 1;
    }
}

public class co1 {

    static List<String> rotations = new ArrayList<>();

    static int height(AVLNode n) {
        return (n == null) ? 0 : n.height;
    }

    static int getBalance(AVLNode n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    static AVLNode rightRotate(AVLNode y) {

        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height =
            Math.max(height(y.left), height(y.right)) + 1;

        x.height =
            Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    static AVLNode leftRotate(AVLNode x) {

        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height =
            Math.max(height(x.left), height(x.right)) + 1;

        y.height =
            Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // DESCENDING TREE
    // Larger timestamp goes LEFT
    static AVLNode insert(AVLNode node,
                          long ts,
                          String trackId) {

        if (node == null)
            return new AVLNode(ts, trackId);

        if (ts > node.ts)
            node.left =
                insert(node.left, ts, trackId);

        else if (ts < node.ts)
            node.right =
                insert(node.right, ts, trackId);

        else
            return node;

        node.height =
            1 + Math.max(
                    height(node.left),
                    height(node.right));

        int balance = getBalance(node);

        // LL
        if (balance > 1 &&
            ts > node.left.ts) {

            rotations.add(
                "LL Rotation at pivot "
                + node.ts);

            return rightRotate(node);
        }

        // RR
        if (balance < -1 &&
            ts < node.right.ts) {

            rotations.add(
                "RR Rotation at pivot "
                + node.ts);

            return leftRotate(node);
        }

        // LR
        if (balance > 1 &&
            ts < node.left.ts) {

            rotations.add(
                "LR Rotation at pivot "
                + node.ts);

            node.left =
                leftRotate(node.left);

            return rightRotate(node);
        }

        // RL
        if (balance < -1 &&
            ts > node.right.ts) {

            rotations.add(
                "RL Rotation at pivot "
                + node.ts);

            node.right =
                rightRotate(node.right);

            return leftRotate(node);
        }

        return node;
    }

    static void printTree(
            AVLNode root,
            String indent,
            boolean last) {

        if (root != null) {

            System.out.print(indent);

            if (last) {
                System.out.print("\\--- ");
                indent += "     ";
            } else {
                System.out.print("/--- ");
                indent += "|    ";
            }

            System.out.println(
                root.ts +
                "(bf=" +
                getBalance(root) +
                ")");

            printTree(
                root.left,
                indent,
                false);

            printTree(
                root.right,
                indent,
                true);
        }
    }

    static void topKHelper(
            AVLNode root,
            int k,
            List<Long> result) {

        if (root == null ||
            result.size() >= k)
            return;

        // In-order gives descending order
        topKHelper(
            root.left,
            k,
            result);

        if (result.size() < k)
            result.add(root.ts);

        if (result.size() >= k)
            return;

        topKHelper(
            root.right,
            k,
            result);
    }

    static List<Long> topKDescending(
            AVLNode root,
            int k) {

        List<Long> result =
            new ArrayList<>();

        topKHelper(root, k, result);

        return result;
    }

    public static void main(String[] args) {

        long timestamps[] = {
            32400,
            28800,
            36000,
            25200,
            39600,
            21600,
            43200,
            18000,
            46800,
            14400,
            50400
        };

        AVLNode root = null;

        System.out.println(
            "AVL INSERTION (Arrival Order)\n");

        System.out.println(
            "Insertion order:");

        for (long ts : timestamps) {
            System.out.print(ts + " ");

            root = insert(
                root,
                ts,
                "Track_" + ts);
        }

        System.out.println("\n");

        System.out.println(
            "Rotations that occurred:");

        if (rotations.isEmpty()) {

            System.out.println(
                "No rotations");
        }
        else {

            for (String s : rotations)
                System.out.println(s);
        }

        System.out.println(
            "\nFINAL AVL TREE");
        System.out.println(
            "(Descending by timestamp)\n");

        printTree(root, "", true);

        System.out.println(
            "\nTOP 5 DESCENDING (k = 5)");

        List<Long> top5 =
            topKDescending(root, 5);

        System.out.println(
            "Top 5 timestamps (most recent first):");

        System.out.println(top5);

        System.out.println(
            "\nTime Complexity (worst case):");

        System.out.println(
            "O(min(n,k) + log n)");
    }
}

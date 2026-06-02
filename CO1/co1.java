import java.util.*;

class Node {
    int key, height;
    Node left, right;

    Node(int key) {
        this.key = key;
        height = 1;
    }
}

public class co1 {

    Node root;

    int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    Node insert(Node node, int key) {

        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // LL
        if (balance > 1 && key < node.left.key) {
            System.out.println("LL Rotation at pivot " + node.key);
            return rightRotate(node);
        }

        // RR
        if (balance < -1 && key > node.right.key) {
            System.out.println("RR Rotation at pivot " + node.key);
            return leftRotate(node);
        }

        // LR
        if (balance > 1 && key > node.left.key) {
            System.out.println("LR Rotation at pivot " + node.key);
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (balance < -1 && key < node.right.key) {
            System.out.println("RL Rotation at pivot " + node.key);
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void printDescending(Node root, List<Integer> list) {
        if (root == null)
            return;

        printDescending(root.right, list);
        list.add(root.key);
        printDescending(root.left, list);
    }

    void displayTree(Node root, String indent, boolean right) {
        if (root != null) {
            displayTree(root.right, indent + "    ", true);

            System.out.println(
                    indent +
                    (right ? "/" : "\\") +
                    "--- " +
                    root.key +
                    "(bf=" + getBalance(root) + ")");

            displayTree(root.left, indent + "    ", false);
        }
    }

    public static void main(String[] args) {

        co1 tree = new co1();

        int timestamps[] = {
                32400, 28800, 36000,
                25200, 39600, 21600,
                43200, 18000, 46800,
                14400, 50400
        };

        System.out.println("AVL INSERTION (Arrival Order)");
        System.out.println("\nInsertion order:");

        for (int t : timestamps) {
            System.out.print(t + " ");
        }

        System.out.println("\n\nRotations that occurred:");

        for (int t : timestamps) {
            tree.root = tree.insert(tree.root, t);
        }

        System.out.println("\nFINAL AVL TREE");
        System.out.println("(Descending by timestamp)\n");

        tree.displayTree(tree.root, "", true);

        List<Integer> recent = new ArrayList<>();
        tree.printDescending(tree.root, recent);

        int k = 5;

        System.out.println("\nTOP 5 DESCENDING (k = 5)");
        System.out.println("Top 5 timestamps (most recent first):");

        System.out.println(
                recent.subList(0, Math.min(k, recent.size()))
        );

        System.out.println(
                "\nTime Complexity (worst case): O(min(n,k) + log n)"
        );
    }
}

import java.util.ArrayList;

class Node {
    double rating;
    int height;
    int size;
    Node left, right;

    Node(double rating) {
        this.rating = rating;
        this.height = 1;
        this.size = 1;
    }
}

public class co1 {

    Node root;
    ArrayList<String> rotations = new ArrayList<>();

    int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    int size(Node n) {
        return (n == null) ? 0 : n.size;
    }

    void update(Node n) {
        if (n != null) {
            n.height = 1 + Math.max(height(n.left), height(n.right));
            n.size = 1 + size(n.left) + size(n.right);
        }
    }

    int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        update(y);
        update(x);

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        update(x);
        update(y);

        return y;
    }

    Node insert(Node node, double rating) {

        if (node == null)
            return new Node(rating);

        if (rating < node.rating)
            node.left = insert(node.left, rating);
        else
            node.right = insert(node.right, rating);

        update(node);

        int balance = getBalance(node);

        // LL
        if (balance > 1 && rating < node.left.rating) {
            rotations.add("LL Rotation at pivot " + node.rating);
            return rightRotate(node);
        }

        // RR
        if (balance < -1 && rating > node.right.rating) {
            rotations.add("RR Rotation at pivot " + node.rating);
            return leftRotate(node);
        }

        // LR
        if (balance > 1 && rating > node.left.rating) {
            rotations.add("LR Rotation at pivot " + node.rating);
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (balance < -1 && rating < node.right.rating) {
            rotations.add("RL Rotation at pivot " + node.rating);
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void printTree(Node node, String indent, boolean last) {

        if (node != null) {

            System.out.print(indent);

            if (last) {
                System.out.print("\\--- ");
                indent += "     ";
            } else {
                System.out.print("/--- ");
                indent += "|    ";
            }

            System.out.println(node.rating +
                    "(bf=" + getBalance(node) +
                    ",size=" + node.size + ")");

            printTree(node.right, indent, false);
            printTree(node.left, indent, true);
        }
    }

    double findMedian(Node node, int k) {

        int leftSize = size(node.left);

        System.out.println("\nVisiting Node: " + node.rating);
        System.out.println("Left Subtree Size = " + leftSize);

        if (k == leftSize + 1) {
            System.out.println("Median Found!");
            return node.rating;
        }

        if (k <= leftSize) {
            System.out.println("Move Left");
            return findMedian(node.left, k);
        }

        System.out.println("Move Right");
        return findMedian(node.right, k - leftSize - 1);
    }

    public static void main(String[] args) {

        co1 tree = new co1();

        double ratings[] = {
                4.5, 3.8, 4.2, 5.0, 4.0,
                4.7, 3.9, 4.3, 4.1
        };

        System.out.println("GOODREADS RATING INSERTION\n");

        System.out.print("Ratings inserted:\n");
        for (double r : ratings) {
            System.out.print(r + " ");
            tree.root = tree.insert(tree.root, r);
        }

        System.out.println("\n");

        System.out.println("Rotations that occurred:");
        if (tree.rotations.isEmpty()) {
            System.out.println("No rotations required");
        } else {
            for (String r : tree.rotations)
                System.out.println(r);
        }

        System.out.println("\nFINAL AVL TREE");
        System.out.println("(Ratings BST Structure)\n");

        tree.printTree(tree.root, "", true);

        int totalRatings = tree.size(tree.root);
        int medianRank = (totalRatings + 1) / 2;

        System.out.println("\nMEDIAN LOOKUP");
        System.out.println("\nTotal Ratings : " + totalRatings);
        System.out.println("Median Rank   : " + medianRank);

        double median = tree.findMedian(tree.root, medianRank);

        System.out.println("\nMedian Rating = " + median);

        System.out.println("\nTime Complexity:");
        System.out.println("Insertion     : O(log n)");
        System.out.println("Deletion      : O(log n)");
        System.out.println("Median Lookup : O(log n)");
    }
}

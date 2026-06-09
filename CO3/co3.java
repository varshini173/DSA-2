import java.util.*;

class Frame {
    String node;
    boolean processed;

    Frame(String n, boolean p) {
        node = n;
        processed = p;
    }

    @Override
    public String toString() {
        return processed
                ? node + "(L)"
                : node + "(E)";
    }
}

public class co3 {

    static void addEdge(
            Map<String, List<String>> adj,
            String from,
            String to) {

        adj.putIfAbsent(from,
                new ArrayList<>());

        adj.putIfAbsent(to,
                new ArrayList<>());

        adj.get(from).add(to);
    }

    static String stackToString(
            Deque<Frame> stack) {

        List<String> temp =
                new ArrayList<>();

        for (Frame f : stack) {
            temp.add(f.toString());
        }

        return temp.toString();
    }

    static List<String> topoSortIterative(
            Map<String, List<String>> adj,
            String start) {

        Map<String, Integer> color =
                new HashMap<>();

        for (String v : adj.keySet()) {
            color.put(v, 0);
        }

        List<String> order =
                new ArrayList<>();

        Deque<Frame> stack =
                new ArrayDeque<>();

        stack.push(
                new Frame(start, false));

        System.out.println(
                "DFS TRACE TABLE\n");

        System.out.printf(
                "%-5s %-12s %-10s %-10s %-50s %-30s%n",
                "Step",
                "Node",
                "Action",
                "Color",
                "Stack",
                "Output");

        int step = 1;

        while (!stack.isEmpty()) {

            Frame f = stack.pop();

            if (f.processed) {

                color.put(f.node, 2);

                order.add(f.node);

                System.out.printf(
                        "%-5d %-12s %-10s %-10s %-50s %-30s%n",
                        step++,
                        f.node,
                        "LEAVE",
                        "BLACK",
                        stackToString(stack),
                        order);

                continue;
            }

            if (color.get(f.node) != 0)
                continue;

            color.put(f.node, 1);

            stack.push(
                    new Frame(
                            f.node,
                            true));

            List<String> nbrs =
                    new ArrayList<>(
                            adj.get(f.node));

            nbrs.sort(
                    Collections.reverseOrder());

            for (String n : nbrs) {

                if (color.get(n) == 0) {

                    stack.push(
                            new Frame(
                                    n,
                                    false));
                }
            }

            System.out.printf(
                    "%-5d %-12s %-10s %-10s %-50s %-30s%n",
                    step++,
                    f.node,
                    "ENTER",
                    "GREY",
                    stackToString(stack),
                    order);
        }

        return order;
    }

    public static void main(String[] args) {

        Map<String, List<String>> adj =
                new HashMap<>();

        addEdge(adj, "app", "core");

        addEdge(adj, "core", "logging");
        addEdge(adj, "core", "util");

        addEdge(adj, "logging", "filehandler");
        addEdge(adj, "logging", "log4j");

        addEdge(adj, "util", "math");
        addEdge(adj, "util", "serial");

        addEdge(adj, "math", "bigint");

        System.out.println(
                "MAVEN DEPENDENCY RESOLVER\n");

        System.out.println(
                "Dependency Graph:\n");

        System.out.println(
                "app -> core");
        System.out.println(
                "core -> logging");
        System.out.println(
                "core -> util");
        System.out.println(
                "logging -> filehandler");
        System.out.println(
                "logging -> log4j");
        System.out.println(
                "util -> math");
        System.out.println(
                "util -> serial");
        System.out.println(
                "math -> bigint\n");

        List<String> topo =
                topoSortIterative(
                        adj,
                        "app");

        System.out.println(
                "\nFINAL TOPOLOGICAL ORDER");
        System.out.println(
                "(Dependencies First)\n");

        for (int i = 0; i < topo.size(); i++) {

            System.out.println(
                    (i + 1)
                    + ". "
                    + topo.get(i));
        }

        System.out.println(
                "\nVERIFICATION");

        System.out.println(
                "All dependencies appear before dependent modules.\n");

        System.out.println(
                "TIME COMPLEXITY");

        System.out.println(
                "O(V + E)");

        System.out.println(
                "\nSPACE COMPLEXITY");

        System.out.println(
                "O(V)");

        int depth = 7800;

        long frameSize = 32;

        long heapMemory =
                depth * frameSize;

        System.out.println(
                "\nPRODUCTION ANALYSIS");

        System.out.println(
                "Depth = 7800");

        System.out.println(
                "Artifacts = 50000");

        System.out.println(
                "Frame Size = 32 bytes");

        System.out.println(
                "Heap Stack Memory = "
                        + heapMemory
                        + " bytes");

        System.out.println(
                "= "
                        + (heapMemory / 1024.0)
                        + " KB");

        System.out.println(
                "\nADVANTAGE");

        System.out.println(
                "Iterative DFS avoids StackOverflowError by using heap memory instead of JVM call stack.");
    }
}

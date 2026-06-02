import java.util.Arrays;

public class co3 {

    static final int V = 5;

    int minDistance(int dist[], boolean visited[]) {

        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!visited[v] && dist[v] < min) {
                min = dist[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    void dijkstra(int graph[][], int src) {

        int dist[] = new int[V];
        boolean visited[] = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        System.out.println("DIJKSTRA'S SHORTEST PATH ANALYSIS\n");

        String[] locations = {
                "Home",
                "Mall",
                "College",
                "Railway Station",
                "Airport"
        };

        System.out.println("Locations:");
        for (int i = 0; i < V; i++) {
            System.out.println(i + " - " + locations[i]);
        }

        System.out.println("\nRoad Network Constructed Successfully");
        System.out.println("\nSource Location: Home (0)\n");

        for (int count = 0; count < V - 1; count++) {

            int u = minDistance(dist, visited);
            visited[u] = true;

            System.out.println("Step " + (count + 1) + ":");
            System.out.println("Selected Node = " + locations[u]);

            for (int v = 0; v < V; v++) {

                if (!visited[v]
                        && graph[u][v] != 0
                        && dist[u] != Integer.MAX_VALUE
                        && dist[u] + graph[u][v] < dist[v]) {

                    dist[v] = dist[u] + graph[u][v];
                }
            }

            System.out.println("Current Distances:");
            for (int i = 0; i < V; i++) {
                if (dist[i] == Integer.MAX_VALUE)
                    System.out.println(locations[i] + " = INF");
                else
                    System.out.println(locations[i] + " = " + dist[i]);
            }
            System.out.println();
        }

        System.out.println("FINAL SHORTEST DISTANCES\n");

        for (int i = 0; i < V; i++) {
            System.out.println(
                    "Home -> " + locations[i] +
                    " = " + dist[i]
            );
        }

        System.out.println("\nTime Complexity:");
        System.out.println("O((V + E) log V)");
    }

    public static void main(String[] args) {

        int graph[][] = {
                {0, 4, 2, 0, 0},
                {4, 0, 1, 5, 0},
                {2, 1, 0, 8, 10},
                {0, 5, 8, 0, 2},
                {0, 0, 10, 2, 0}
        };

        co3 d = new co3();
        d.dijkstra(graph, 0);
    }
}

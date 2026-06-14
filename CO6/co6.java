import java.util.*;

public class co6 {

    static class Item {
        String name;
        int weight;
        int value;
        double ratio;

        Item(String name, int weight, int value) {
            this.name = name;
            this.weight = weight;
            this.value = value;
            this.ratio = (double) value / weight;
        }
    }

    public static void main(String[] args) {

        Item[] items = {
                new Item("A", 5, 40),
                new Item("B", 8, 50),
                new Item("C", 3, 20),
                new Item("D",10, 70),
                new Item("E", 4, 30),
                new Item("F", 6, 35),
                new Item("G", 7, 45),
                new Item("H", 2, 15)
        };

        int W = 24;

        greedyKnapsack(items, W);

        System.out.println("\n----------------------------------\n");

        dpKnapsack(items, W);
    }

    static void greedyKnapsack(Item[] items, int capacity) {

        Item[] copy = items.clone();

        Arrays.sort(copy, (a,b) ->
                Double.compare(b.ratio, a.ratio));

        int totalWeight = 0;
        int totalValue = 0;

        System.out.println("GREEDY KNAPSACK (Value/Weight Ratio)\n");

        System.out.println("Order of Consideration:");

        for(Item item : copy){
            System.out.printf("%s (w=%d, v=%d, ratio=%.2f)%n",
                    item.name,item.weight,item.value,item.ratio);
        }

        System.out.println("\nSelected Items:");

        for(Item item : copy){

            if(totalWeight + item.weight <= capacity){
                totalWeight += item.weight;
                totalValue += item.value;

                System.out.println(item.name);
            }
        }

        System.out.println("\nGreedy Total Weight = " + totalWeight);
        System.out.println("Greedy Total Value  = " + totalValue);
    }

    static void dpKnapsack(Item[] items, int W){

        int n = items.length;

        int[][] dp = new int[n+1][W+1];

        for(int i=1;i<=n;i++){

            int wt = items[i-1].weight;
            int val = items[i-1].value;

            for(int w=0;w<=W;w++){

                dp[i][w] = dp[i-1][w];

                if(wt <= w){

                    dp[i][w] =
                            Math.max(dp[i][w],
                                    dp[i-1][w-wt] + val);
                }
            }
        }

        System.out.println("DYNAMIC PROGRAMMING KNAPSACK\n");

        System.out.println("Optimal Value = " + dp[n][W]);

        List<String> chosen = new ArrayList<>();

        int w = W;

        for(int i=n;i>=1;i--){

            if(dp[i][w] != dp[i-1][w]){

                chosen.add(items[i-1].name);

                w -= items[i-1].weight;
            }
        }

        Collections.reverse(chosen);

        System.out.println("Chosen Items = " + chosen);

        int totalWeight = 0;

        for(String s : chosen){

            for(Item item : items){

                if(item.name.equals(s)){
                    totalWeight += item.weight;
                }
            }
        }

        System.out.println("Total Weight = " + totalWeight);

        System.out.println("\nFINAL DP ROW (i = 8)\n");

        for(int j=0;j<=W;j++){

            System.out.printf("%4d", dp[n][j]);
        }

        System.out.println();
    }
}

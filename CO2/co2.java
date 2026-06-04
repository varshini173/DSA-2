import java.util.*;

public class co2 {

    static class IndexEntry {
        long offset;
        long position;

        IndexEntry(long offset, long position) {
            this.offset = offset;
            this.position = position;
        }
    }

    public static void main(String[] args) {

        ArrayList<IndexEntry> sparseIndex = new ArrayList<>();

        sparseIndex.add(new IndexEntry(1000, 4096));
        sparseIndex.add(new IndexEntry(3000, 12288));
        sparseIndex.add(new IndexEntry(5000, 20480));
        sparseIndex.add(new IndexEntry(7000, 28672));
        sparseIndex.add(new IndexEntry(9000, 36864));

        long targetOffset = 7850;

        System.out.println("APACHE KAFKA SPARSE INDEX LOOKUP\n");

        System.out.println("Target Offset: " + targetOffset);

        System.out.println("\nSparse Index Entries:");
        for (IndexEntry entry : sparseIndex) {
            System.out.println(entry.offset + " -> Position " + entry.position);
        }

        int low = 0;
        int high = sparseIndex.size() - 1;

        IndexEntry nearest = null;

        System.out.println("\nBINARY SEARCH PROCESS:");

        while (low <= high) {

            int mid = (low + high) / 2;
            IndexEntry current = sparseIndex.get(mid);

            System.out.println(
                    "Checking Offset " + current.offset);

            if (current.offset == targetOffset) {
                nearest = current;
                break;
            }

            if (current.offset < targetOffset) {
                nearest = current;
                System.out.println("Move Right");
                low = mid + 1;
            } else {
                System.out.println("Move Left");
                high = mid - 1;
            }
        }

        System.out.println("\nNEAREST INDEX ENTRY FOUND:");
        System.out.println(
                nearest.offset +
                " -> Position " +
                nearest.position);

        System.out.println("\nJUMP TO PHYSICAL POSITION:");
        System.out.println("Position " + nearest.position);

        System.out.println("\nSEQUENTIAL SCAN:");

        long recordsScanned = 0;

        for (long offset = nearest.offset + 1;
             offset <= targetOffset;
             offset++) {

            recordsScanned++;

            if (offset <= nearest.offset + 5 ||
                offset >= targetOffset - 5) {

                System.out.println("Scanning Offset " + offset);
            }

            if (offset == nearest.offset + 6) {
                System.out.println("...");
            }
        }

        System.out.println("\nRECORD FOUND!");
        System.out.println("Offset = " + targetOffset);

        System.out.println("\nSTATISTICS");
        System.out.println("Index Size (n_index): "
                + sparseIndex.size());

        System.out.println("Records Scanned (k): "
                + recordsScanned);

        System.out.println("\nTIME COMPLEXITY");
        System.out.println(
                "Binary Search : O(log n_index)");
        System.out.println(
                "Sequential Scan : O(k)");
        System.out.println(
                "Overall : O(log n_index + k)");

        System.out.println("\nINDEX DENSITY TRADE-OFF");
        System.out.println(
                "Dense Index  -> Faster seeks, more memory");
        System.out.println(
                "Sparse Index -> Less memory, more scanning");
        System.out.println(
                "Kafka favors Sparse Index for scalability.");
    }
}

import java.util.ArrayList;

public class CustomHashTable{
    static final class Node {
        Object key;
        Node next;
        int count;
        // Object value;
        Node(Object k, Node n) { key = k; next = n; }
    }
    Node[] table = new Node[8]; // always a power of 2
    int size = 0;
    boolean contains(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key))
                return true;
        }
        return false;
    }

    boolean add(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key))
                return false;
        }
        table[i] = new Node(key, table[i]);
        ++size;
        if ((float)size/table.length >= 0.75f) {
            resize();
        }
        return true;
    }

    void resize() {
        Node[] oldTable = table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity << 1;
        Node[] newTable = new Node[newCapacity];
        for (int i = 0; i < oldCapacity; ++i) {
            for (Node e = oldTable[i]; e != null; e = e.next) {
                int h = e.key.hashCode();
                int j = h & (newTable.length - 1);
                newTable[j] = new Node(e.key, newTable[j]);
            }
        }
        table = newTable;
    }

    double doCosineSimilarity(String[] a, String[] b){

        // Declaring variables necessary for the math (cosine similarity)
        double similarity;
        int numerator = 0;
        int denominatorA;
        int denominatorB;

        CustomHashTable ht1 = new CustomHashTable();
        CustomHashTable ht2 = new CustomHashTable();
        ArrayList<Object> lst = new ArrayList<>();

        // Each repeat in ht1 and ht2 forms the numerator and the sizes forms the denominator
        for(Object obj : a){
            if(ht1.add(obj)) lst.add(obj);
        }
        denominatorA = ht1.size;
        for(Object obj : b){
            ht2.add(obj);
        }
        denominatorB = ht2.size;
        for(Object obj : lst){
            if(ht2.contains(obj)) numerator += 1;
        }

        // Tweaked the formula to only necessary data
        similarity = numerator/(Math.sqrt(denominatorA)*Math.sqrt(denominatorB));

        return similarity;
    }
}

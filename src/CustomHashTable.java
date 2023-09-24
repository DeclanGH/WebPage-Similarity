public class CustomHashTable{
    static final class Node {
        Object key;
        Node next;
        // int count;
        // Object value;
        Node(Object k, Node n) { key = k; next = n; }
    }
    Node[] table = new Node[8]; // always a power of 2
    Object[] keyList = new Object[8]; // creating this for iterating purposes
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
        keyList[size] = key;
        ++size;
        if ((float)size/table.length >= 0.75f) {
            resize();
        }
        return true;
    }

    void resize() { // I would be sharing the resize method with my keyList
        Node[] oldTable = table;
        Object[] oldKeyList = keyList;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity << 1; // doubles the capacity
        Node[] newTable = new Node[newCapacity];
        Object[] newKeyList = new Object[newCapacity];

        for (int i = 0; i < oldCapacity; ++i) {
            newKeyList[i] = oldKeyList[i]; // populate the new one with the "old"
            for (Node e = oldTable[i]; e != null; e = e.next) {
                int h = e.key.hashCode();
                int j = h & (newTable.length - 1);
                newTable[j] = new Node(e.key, newTable[j]);
            }
        }
        table = newTable;
        keyList = newKeyList;
    }

    Object[] toKeyList(){
        return keyList;
    }

    int getSize(){
        return size;
    }

}

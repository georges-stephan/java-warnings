package warnings.jw.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <pre>
 * f - folder, w is warning
 * 
 * (f0,w, map (f1,f2,f3,f4)
 * |
 * ------------------------------------------
 * |		|				|		        |
 * (f1,w)	(f2,w, map(f5))	(f3,w,map())	(f4,w, map())
 * 		    |
 * 		    |
 * 		    (f5,w, map (f6,f7)
 * 		    |
 * 		    -----------------
 * 		    |				|
 * 		    (f6,w, map())	(f7,w, map())
 * </pre>
 * 
 * Inspired from https://www.javagists.com/java-tree-data-structure
 */
public class TreeNode<K, V> {
    private K key = null;
    private V data = null;
    private Map<K, List<TreeNode<K, V>>> children = new HashMap<>();
    private TreeNode<K, V> parent = null;

    public TreeNode(K key, V data) {
        this.data = data;
        this.key = key;
    }

    public long getChildrenCount() {
        final long count = children.entrySet().stream().map(e -> {
            e.getValue().stream().map(f -> {
                return f;
            });
            return e;
        }).collect(Collectors.counting());
        return count;
    }

    public TreeNode<K, V> addChild(TreeNode<K, V> child) {
        child.setParent(this);
        List<TreeNode<K, V>> nodesChildrenList = children.get(child.getKey());

        if (nodesChildrenList == null) {
            List<TreeNode<K, V>> aList = new ArrayList<>();
            aList.add(child);
            children.put(key, aList);
        } else {
            nodesChildrenList.add(child);
        }

        return child;
    }

    public TreeNode<K, V> getChild(K key, int index) {
        return children.get(key).get(index);
    }

    public Optional<TreeNode<K, V>> getChild(K key) {
        return children.get(key).stream().filter(e -> e.getKey().equals(key)).findFirst();
    }

    public List<TreeNode<K, V>> getChildren(K key) {
        return children.get(key);
    }

    public V getData() {
        return data;
    }

    public K getKey() {
        return key;
    }

    public void setData(K key, V data) {
        this.data = data;
    }

    private void setParent(TreeNode<K, V> parent) {
        this.parent = parent;
    }

    public TreeNode<K, V> getParent() {
        return parent;
    }

    // public void traverse(TreeNode<K, V> node) {
    // if (node.getChildrenCount() < 1) {
    // System.out.println(String.format("Path:%s, value is:%s", node.getKey(),
    // node.getData()));
    // return;
    // }
    // node.getChildren().entrySet().stream().forEach(e -> traverse(node));
    // }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Parent key:");
        buffer.append(parent == null ? "null" : this.parent.getKey());
        buffer.append("\r\n");

        buffer.append("Key:");
        buffer.append(this.key);
        buffer.append("\r\n");

        buffer.append("Value:");
        buffer.append(this.data);
        buffer.append("\r\n");

        buffer.append("Children:");
        buffer.append(this.children);
        buffer.append("\r\n");

        return buffer.toString();
    }

}

package warnings.jw.tree;

import java.util.List;

import warnings.jw.DefaultWarning;
import warnings.jw.Warning;

public class TreeFacade {
    private final TreeNode<String, Warning> treeRootNode = new TreeNode<>(".", null);

    public TreeFacade() {
        Warning warningOne = new DefaultWarning("Ouh la la... C'est quoi ca?");
        Warning warningTwo = new DefaultWarning("Quoi? Encore???");
        TreeNode<String, Warning> treeNodeWarning = storeWarning("com.machin.truc.moche", warningOne);
        System.out.println("treeNodeWarning=" + treeNodeWarning);
        treeNodeWarning = storeWarning("com.machin.truc.moche", warningTwo);
        System.out.println("treeNodeWarning=" + treeNodeWarning);
        System.out.println("===================================================================");
        System.out.println(treeRootNode);
    }

    private TreeNode<String, Warning> storeWarning(String pathFolderName, Warning warning) {
        String[] packagePathNames = pathFolderName.split("\\.");
        TreeNode<String, Warning> movingNode = treeRootNode;

        for (int i = 0; i < packagePathNames.length; i++) {
            boolean hasNext = true;
            if ((i + 1) == packagePathNames.length) {
                // Last iteration of the loop
                hasNext = false;
            }

            List<TreeNode<String, Warning>> children = movingNode.getChildren(packagePathNames[i]);
            if (children == null) {
                // First occurence of this package name in the tree
                if (hasNext) {
                    movingNode = movingNode
                            .addChild(new TreeNode<String, Warning>(packagePathNames[i], null));
                } else {
                    movingNode = movingNode
                            .addChild(new TreeNode<String, Warning>(packagePathNames[i], warning));
                }

            } else {
                movingNode = movingNode.getChild(packagePathNames[i]).get();
                // movingNode = children.getChild(packagePathNames[i]);
            }
            System.out.println(movingNode == null ? "null" : movingNode.toString());
        }

        return movingNode;

    }

    public void storeWarningOneByOne(String pathFolderName, TreeNode<String, Warning> treeNode) {

        System.out.println("===================================================================");
        TreeNode<String, Warning> childOne = treeNode
                .addChild(new TreeNode<String, Warning>("com", new DefaultWarning("one")));
        System.out.println("childOne=" + childOne);

        System.out.println("===================================================================");
        TreeNode<String, Warning> childTwo = childOne
                .addChild(new TreeNode<String, Warning>("georges", new DefaultWarning("two")));
        System.out.println("childTwo=" + childTwo);

        System.out.println("===================================================================");
        TreeNode<String, Warning> childThree = childTwo
                .addChild(new TreeNode<String, Warning>("stephan", new DefaultWarning("three")));
        System.out.println("childThree=" + childThree);

        TreeNode<String, Warning> childFour = childThree
                .addChild(new TreeNode<String, Warning>("boutros", null));
        System.out.println("childFour=" + childFour);
        System.out.println("===================================================================");
        System.out.println("===================================================================");
        System.out.println("childOne=" + childOne);
    }

    public static void main(String[] args) {
        new TreeFacade();
    }

}

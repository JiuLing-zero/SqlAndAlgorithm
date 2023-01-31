### BFS-广度优先搜索
![](../../../../../../picture/B_BFS/1DFSMessage.png)
**伪代码和实际代码**
![](../../../../../../picture/B_BFS/2DFSMessage.png)

### 第一题，MinimumDepthOfBinaryTree
![](../../../../../../picture/B_BFS/3MinimumDepthOfBinaryTree.png)
给定一棵二叉树，求它的最小深度。   
最小深度是从根节点到最近的叶节点的最短路径上的节点数。   
注意:叶子是没有子节点的节点。   
**下面是给出的java代码模板，在此基础上完成题目**
```Java
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
```

### 第二题，BinaryTreeLevelOrderTraversal
给定一棵二叉树，返回其节点值的层次顺序遍历。(即，从左到右，一层一层)。
![](../../../../../../picture/B_BFS/4BinaryTreeLevelOrderTraversal.png)

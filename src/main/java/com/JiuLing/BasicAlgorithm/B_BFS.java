package com.JiuLing.BasicAlgorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Description 基础算法2-BFS BV1Rz4y1Z7tJ
 * Create by hfli11 on 2023/1/16 16:06
 */
public class B_BFS {

    public static void main(String[] args) {
//        minDepth_Driver();
        levelOrder_Driver();
    }

    //公共类 TreeNode
    public static class TreeNode {
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

// * * * * * * * * * * 第一题，MinimumDepthOfBinaryTree * * * * * * * * * *
    //解法1
    public static int minDepth1(TreeNode root){
        if(root == null) return 0;
        int depth = 1;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()){
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                if(cur.left == null && cur.right == null) return depth;
                if(cur.left != null) q.offer(cur.left);
                if(cur.right != null) q.offer(cur.right);
            }
            depth++;
        }
        return depth;
    }
    //解法2(dfs)
    public static int minDepth2(TreeNode root){
        if(root == null) return 0;
        if(root.left == null) return minDepth2(root.right) + 1;
        if(root.right == null) return minDepth2(root.left) + 1;
        return Math.min(minDepth2(root.right), minDepth2(root.left)) + 1;
    }
// * * * * * * * * * * 第一题，MinimumDepthOfBinaryTree [Over] * * * * * * * * * *
// * * * * * * * * * * 第二题，BinaryTreeLevelOrderTraversal [Over] * * * * * * * * * *
    //解法一
    public static List<List<Integer>> levelOrder1(TreeNode root){
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        if(root != null) q.offer(root);
        while (!q.isEmpty()){
            int size = q.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                level.add(cur.val);
                if(cur.left != null) q.offer(cur.left);
                if(cur.right != null) q.offer(cur.right);
            }
            res.add(level);
        }
        return res;
    }

    //解法二，递归
    public static List<List<Integer>> levelOrder2(TreeNode root){
        List<List<Integer>> res = new ArrayList<>();
        dfs(root, res, 0);
        return res;
    }
    public static void dfs(TreeNode root, List<List<Integer>> res, int height){
        if(root == null) return;
        if(height >= res.size()) res.add(new ArrayList<Integer>());
        res.get(height).add(root.val);
        if(root.left != null) dfs(root.left, res, height+1);
        if(root.right != null) dfs(root.right, res, height+1);
    }
// * * * * * * * * * * 第二题，BinaryTreeLevelOrderTraversal [Over] * * * * * * * * * *


    // BV1Rz4y1Z7tJ 16:35

    //1
    public static void minDepth_Driver(){
        TreeNode a = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(17)));
        System.out.println(minDepth1(a));
        System.out.println(minDepth2(a));
    }
    //2
    public static void levelOrder_Driver(){
        TreeNode a = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(17)));
        System.out.println(levelOrder1(a));
        System.out.println(levelOrder2(a));
    }


}

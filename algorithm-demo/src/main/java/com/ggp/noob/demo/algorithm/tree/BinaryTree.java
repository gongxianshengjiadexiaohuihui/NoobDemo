package com.ggp.noob.demo.algorithm.tree;

/**
 * @Author:ggp
 * @Date:2020/12/29 20:37
 * @Description:
 */
public class BinaryTree<K,V>  {
    /**
     * 根节点
     */
    private Node<K,V> root;

    /**
     * 前序遍历  根节点->左子树->右子树
     * @return
     */
    public void preOrderTraversal(){
        if(null == root){
            System.out.println("这是一个空树");
            return;
        }
        preOrderTraversal(root);
    }
    private void preOrderTraversal(Node<K,V> node){
        if(null == node){
            return;
        }
        System.out.println(node);
        preOrderTraversal(node.getLeftSubNode());
        preOrderTraversal(node.getRightSubNode());

    }

    /**
     * 中序遍历  左子树->根节点->右子树
     */
    public void inOrderTraversal(){
        if(null == root){
            System.out.println("这是一个空树");
            return;
        }
        inOrderTraversal(root);
    }
    private void inOrderTraversal(Node<K,V> node){
        if(null == node){
            return;
        }
        inOrderTraversal(node.getLeftSubNode());
        System.out.println(node);
        inOrderTraversal(node.getRightSubNode());
    }
    /**
     * 后序遍历  左子树->右子树->根节点
     */
    public void postOrderTraversal(){
        if(null == root){
            System.out.println("这是一个空树");
            return;
        }
        postOrderTraversal(root);
    }
    private void postOrderTraversal(Node<K,V> node){
        if(null == node){
            return;
        }
        postOrderTraversal(node.getLeftSubNode());
        postOrderTraversal(node.getRightSubNode());
        System.out.println(node);
    }
    public Node<K, V> getRoot() {
        return root;
    }

    public void setRoot(Node<K, V> root) {
        this.root = root;
    }
}

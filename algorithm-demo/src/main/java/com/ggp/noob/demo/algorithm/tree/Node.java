package com.ggp.noob.demo.algorithm.tree;

/**
 * @Author:ggp
 * @Date:2020/12/29 20:17
 * @Description:
 * 树节点，里面存储键值对
 */
public class Node<K,V> {
    /**
     * key值
     */
    private K key;
    /**
     * value值
     */
    private V value;
    /**
     * 左子节点
     */
    private Node<K,V> leftSubNode;
    /**
     * 右子节点
     */
    private Node<K,V> rightSubNode;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node<K, V> getLeftSubNode() {
        return leftSubNode;
    }

    public void setLeftSubNode(Node<K, V> leftSubNode) {
        this.leftSubNode = leftSubNode;
    }

    public Node<K, V> getRightSubNode() {
        return rightSubNode;
    }

    public void setRightSubNode(Node<K, V> rightSubNode) {
        this.rightSubNode = rightSubNode;
    }

    @Override
    public String toString() {
        return "{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}

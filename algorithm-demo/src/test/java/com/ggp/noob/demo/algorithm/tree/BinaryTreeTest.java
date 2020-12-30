package com.ggp.noob.demo.algorithm.tree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author:ggp
 * @Date:2020/12/30 19:26
 * @Description:
 */
public class BinaryTreeTest {
    private static Node<Integer,Integer>[] nodes = new Node[9];
    @Before
    public void init(){
        for (int i = 1; i <9 ; i++) {
            Node<Integer,Integer> node = new Node<>();
            node.setKey(i);
            node.setValue(i);
            nodes[i]=node;
        }
        nodes[1].setLeftSubNode(nodes[2]);
        nodes[1].setRightSubNode(nodes[3]);
        nodes[2].setLeftSubNode(nodes[4]);
        nodes[2].setRightSubNode(nodes[5]);
        nodes[3].setRightSubNode(nodes[6]);
        nodes[5].setLeftSubNode(nodes[7]);
        nodes[5].setRightSubNode(nodes[8]);
    }

    @Test
    public void preOrderTraversal() {
        BinaryTree<Integer,Integer> tree = new BinaryTree<>();
        tree.setRoot(nodes[1]);
        tree.preOrderTraversal();
    }

    @Test
    public void inOrderTraversal() {
        BinaryTree<Integer,Integer> tree = new BinaryTree<>();
        tree.setRoot(nodes[1]);
        tree.inOrderTraversal();
    }

    @Test
    public void postOrderTraversal() {
        BinaryTree<Integer,Integer> tree = new BinaryTree<>();
        tree.setRoot(nodes[1]);
        tree.postOrderTraversal();
    }
}
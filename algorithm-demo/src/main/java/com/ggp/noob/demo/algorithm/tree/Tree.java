package com.ggp.noob.demo.algorithm.tree;

/**
 * @Author:ggp
 * @Date:2020/12/29 20:16
 * @Description:
 * 树的接口，提供增删改查操作
 */
public interface Tree<K,V> {
    /**
     * 添加节点
     * @param node 如果存在相同的key值，则覆盖，并返回旧值，如果是新key，则返回null
     * @return
     */
    Node<K,V> putNode(Node<K,V> node);

    /**
     * 删除节点
     * @param key 节点的key值
     */
    void deleteNode(K key);

    /**
     * 查询节点
     * @param key 节点的key值
     * @return
     */
    Node<K,V> queryNode(K key);
}

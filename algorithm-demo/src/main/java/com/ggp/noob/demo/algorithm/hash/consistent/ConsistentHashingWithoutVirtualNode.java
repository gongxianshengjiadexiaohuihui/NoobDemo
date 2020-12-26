package com.ggp.noob.demo.algorithm.hash.consistent;


import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author:ggp
 * @Date:2020/12/25 14:22
 * @Description: 不带虚拟点的一致性哈希算法
 */
public class ConsistentHashingWithoutVirtualNode {
    /**
     * key表示服务器的hash值，value表示服务器
     */
    private SortedMap<Integer, String> sortedMap = new TreeMap<>();
    /***
     * key表示服务器，value表示在服务器上存储的内容(键值对)
     */
    private Map<String, Map<String, Object>> servers = new HashMap<>();

    /**
     * 将服务器放入hash环
     *
     * @param servers 待加入hash环的服务器列表
     */
    public void init(String[] servers) {
        for (String server : servers) {
            int hash = FNV1_32_HASH.getHash(server);
            System.out.println(MessageFormat.format("[{0}]加入集合中，其hash值为[{1}]", server, String.valueOf(hash)));
            sortedMap.put(hash, server);
            this.servers.put(server, null);
        }
    }

    /**
     * 计算该对象应该再在那个服务器
     *
     * @param key
     * @return
     */
    public void putServer(String key, Object value) {
        int hash = FNV1_32_HASH.getHash(key);
        /**
         * 得到大于该hash值得所有值
         */
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
        String server;
        Map map;
        if (subMap.isEmpty()) {
            /**
             * 如果没有大于该hash值的服务器，返回第一个服务器，形成环结构
             */
            server = sortedMap.get(sortedMap.firstKey());
        } else {
            /**
             * 第一个就是顺时针过去离这个key最近的那个节点
             */
            server = subMap.get(subMap.firstKey());
        }
        map = servers.get(server);
        if (null == map) {
            map = new HashMap<String, Object>();
            servers.put(server, map);
        }
        map.put(key, value);
        System.out.println(MessageFormat.format("键值对[{0},{1}],key的hash值是[{2}]被路由到服务器[{3}]存储", key, value, String.valueOf(hash), server));
    }

    /**
     * 打印各服务器的信息
     */
    public void print() {
        Map<String, Object> map;
        for (String server : servers.keySet()) {
            System.out.println("服务器：" + server);
            System.out.println("存储内容：");
            map = servers.get(server);
            if (null == map) {
                System.out.println("{}");
            } else {
                System.out.print("{");
                for (String key : map.keySet()) {
                    System.out.print(MessageFormat.format("[{0},{1}],", key, map.get(key)));
                }
                System.out.println("}");
            }
        }
    }
}

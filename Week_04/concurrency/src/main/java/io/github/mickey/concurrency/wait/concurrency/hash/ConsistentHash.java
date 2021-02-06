package io.github.mickey.concurrency.wait.concurrency.hash;


import java.util.*;

public class ConsistentHash {

    /**
     * store the target server list
     */
    private String[] servers = {"192.168.50.0:8080", "192.168.50.1:8080", "192.168.50.2:8080",
            "192.168.50.3:8080", "192.168.50.4:8080"};

    /**
     * real node list: frequently added and deleted =>  LinkedList
     */
    private final List<String> realNodes = new LinkedList<>();

    private final SortedMap<Integer, String> virtualNodes = new TreeMap<>();

    /**
     * number of virtual node  for every server
     */
    private  int virtualNodeNumber = 5;

    public ConsistentHash(String[] servers, int virtualNodeNumber) {
        if (servers != null)
            this.servers = servers;
        this.virtualNodeNumber = virtualNodeNumber;
        init();
    }

    private void init() {
        realNodes.addAll(Arrays.asList(servers));

        for (String realNode : realNodes) {
            for (int i = 0; i < this.virtualNodeNumber; i++) {
                String virtualNodeName = realNode + "VN" + i;
                int hash = getHash(virtualNodeName);
                virtualNodes.put(hash, virtualNodeName);
                System.out.println("虚拟节点[" + virtualNodeName + "]被添加, hash值=" + hash);
            }
        }
        System.out.println();
    }


    /**
     * FNV1_32_HASH
     *
     * @param str
     * @return
     */
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }


    public String getServer(String node) {
        int hash = getHash(node);
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        String virtualNode;
        if (subMap.isEmpty()){
            virtualNode = virtualNodes.get(virtualNodes.firstKey());
        }else {
            virtualNode = subMap.get(subMap.firstKey());
        }
        return virtualNode.substring(0, virtualNode.indexOf("VN"));
    }



}

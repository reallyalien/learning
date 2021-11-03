package com.ot.noline.tree;


import java.util.*;

/**
 * 赫夫曼编码2 ,正确的哈夫曼编码， 可以编码任何字符，中文标点都可以
 */
public class HuffManEncode2 {

    private static StringBuilder sb = new StringBuilder();
    private static Map<Character, String> smap = new HashMap<>();
    //保留最后一次的字符串
    private static String lastStr = null;

    public static void main(String[] args) {
        String str = "i likea likec likeaaa java do you like a javakkaakll在，￥你妹";
        byte[] encode = encode(str);
        System.out.println(Arrays.toString(encode));
        String decode = decode(encode, smap);
        System.out.println(decode);
        System.out.println(smap);
    }

    public static byte[] encode(String str) {
        List<Node> list = new ArrayList<>();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            //合并的方法
            map.merge(c, 1, Integer::sum);
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            list.add(node);
        }
        Node root = buildHuffmanTree(list);
        getCode(root);
        byte[] zip = zip(str, smap);
        return zip;
    }

    public static byte[] zip(String str, Map<Character, String> map) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            String s = map.get(c);
            sb.append(s);
        }
        String s = sb.toString();
        System.out.println("编码之后的二进制字符串：" + s);
        //只保留8的整数位，
        byte[] bytes = new byte[(s.length() + 7) / 8 - 1];
        int index = 0;
        for (int i = 0; i < s.length(); i += 8) {
            String sub = null;
            byte b;
            //如果已经截取到了末尾，或者正好是末尾，也把最后8个字节单独保存起来，不需要做其他处理
            if (i + 8 >= s.length()) {
                sub = s.substring(i);
                lastStr = sub;
            } else {
                //否则截取8位数
                sub = s.substring(i, i + 8);
                b = (byte) Integer.parseInt(sub, 2);
                bytes[index++] = b;
            }
        }
        return bytes;
    }

    public static String decode(byte[] bytes, Map<Character, String> map) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String s = null;
            s = binary2String(bytes[i]);
            sb.append(s);
        }
        sb.append(lastStr);
        System.out.println("解码之后的二进制字符串：" + sb);
        //将编码表翻转
        Map<String, Character> reverseMap = new HashMap<>();
        for (Map.Entry<Character, String> entry : map.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }
        List<Character> list = new ArrayList<>();
        Character temp = null;
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            temp = sb.charAt(i);
            sb2.append(temp);
            if (reverseMap.containsKey(sb2.toString())) {
                list.add(reverseMap.get(sb2.toString()));
                sb2.delete(0, sb2.length());
            }
        }
        sb2.delete(0, sb.length());
        for (Character character : list) {
            sb2.append(character);
        }
        return sb2.toString();
    }

    //这里存在一个问题，假如在二进制编码之后的字符串当中存在  011110 这样一个字符串，转换成字节是 30，但是在30转换成二进制时，缺失
    //前面的0，导致重新解码之后的字符串与原来不相等，因此导致结果不对
    public static String binary2String(byte b) {
        String s = Integer.toBinaryString(b);
        //负数的补码是32位二进制数，因此截取最后8位，正数的补码绝不会超过8位
        if (b < 0) {
            s = s.substring(s.length() - 8);
        } else {
            int length = s.length();
            //因为正数的补码一般不足8位，因此前面需要补0，否则会导致编解码不一致
            if (length < 8) {
                for (int i = 0; i < 8 - length; i++) {
                    s = "0" + s;
                }
            }
        }
//        System.out.println("b:" + b + "s:" + s);
        return s;
    }


    /**
     * 到达每一个字符的路径，左为0，右为1，拼接
     *
     * @param node
     */
    public static void getCode(Node node) {
        if (node == null) {
            return;
        }
        getCode(node.left, "0", sb);
        getCode(node.right, "1", sb);
    }

    public static void getCode(Node node, String code, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder(sb);
        //先拼接当前节点
        sb2.append(code);
        //如果这个节点不为空，
        if (node != null) {
            //只有有字符的会存在key，
            if (node.key != null) {
                smap.put(node.key, sb2.toString());
            } else {
                //如果没有，则向下递归
                getCode(node.left, "0", sb2);
                getCode(node.right, "1", sb2);
            }
        }

    }

    public static Node buildHuffmanTree(List<Node> list) {
        while (list.size() > 1) {
            Collections.sort(list);
            Node node1 = list.get(0);
            Node node2 = list.get(1);
            Node parent = new Node(null, node1.value + node2.value);
            parent.left = node1;
            parent.right = node2;
            list.remove(node1);
            list.remove(node2);
            list.add(parent);
        }
        return list.get(0);
    }

    static class Node implements Comparable<Node> {
        private int id;
        private Character key;
        private int value;
        private Node left;
        private Node right;

        public Node(int id) {
            this.id = id;
        }

        public Node(Character key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(Node o) {
            return this.value - o.value;
        }
    }

}

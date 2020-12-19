package com.ot.noline.tree;

import java.util.*;
/**
 * 注意事项：
 * 1.使用字节处理，可以处理任何文件
 * 2.对已经压缩的文件再次压缩，效果不明显
 * 2.如果文件当中重复的内容不多，压缩也不明显
 */

/**
 * 赫夫曼编码
 * 1.统计各个字符出现的次数
 * 2。创建node节点，value值是次数，char是字符
 * 3.创建赫夫曼树
 * 4.从赫夫曼树头root节点开始，到每一个叶子节点，左子节点为0，右子节点为1
 */
public class HuffManEncode {

    /*
    { =01, a=100, d=11000, u=11001, e=1110, v=11011, i=101, y=11010, j=0010, k=1111, l=000, o=0011}
     */
    static Map<Character, String> huffManCodes = new HashMap<>();

    static StringBuilder sb = new StringBuilder();
    //这里我们直接利用HuffManTree当中的方法，在node当中增加一个属性

    public static void main(String[] args) {
        String str = "i like like like java do you like a java";//共40个字符
        byte[] encode = encode(str);
        String decode = decode(encode, huffManCodes);
//        System.out.println(Arrays.toString(encode));
        System.out.println(decode);
    }

    public static byte[] encode(String str) {
        List<HuffManTree.Node> list = new ArrayList<>();
        Map<Character, Integer> data = new HashMap<>();
        //获取每个字符出现的次数
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (data.containsKey(c)) {
                Integer val = data.get(c);
                data.put(c, val + 1);
            } else {
                data.put(c, 1);
            }
        }
        //利用data构建node
        Set<Map.Entry<Character, Integer>> entries = data.entrySet();
        for (Map.Entry<Character, Integer> entry : entries) {
            HuffManTree.Node node = new HuffManTree.Node(entry.getKey(), entry.getValue());
//            System.out.println(node);
            list.add(node);
        }
        //构建赫夫曼树
        HuffManTree.Node root = HuffManTree.createHuffManTree(list);
        //前序遍历
//        HuffManTree.preOrder(root);
        //生成赫夫曼树对应的编码
        getCodes(root);//{ =01, a=100, d=11000, u=11001, e=1110, v=11011, i=101, y=11010, j=0010, k=1111, l=000, o=0011}
        byte[] zip = zip(str, huffManCodes);
        return zip;
    }

    /**
     * @param bytes 哈夫曼编码之后的字节数组
     * @param map   赫夫曼编码表
     * @return 返回解码之后的字符串
     */
    public static String decode(byte[] bytes, Map<Character, String> map) {
        //1.先获取bytes对应的二进制的字符串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            //判断是不是最后一个字节
            boolean flag = (i == bytes.length - 1);
            sb.append(byte2BitString(!flag, bytes[i]));
        }
        //把字符串按照编码表进行解码,把霍夫曼编码进行交换,key value互换
        Map<String, Character> reverseMap = new HashMap<>();
        Set<Map.Entry<Character, String>> entries = map.entrySet();
        for (Map.Entry<Character, String> entry : entries) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }
        //创建一个集合存放byte
        List<Character> list = new ArrayList<>();
        Character temp = null;
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            temp = sb.charAt(i);
            sb2.append(temp);
            if (reverseMap.containsKey(sb2.toString())) {
                list.add(reverseMap.get(sb2.toString()));
                sb2.delete(0, sb.length());
            }
        }
        //利用sb2
        sb2.delete(0, sb2.length());
        for (Character character : list) {
            sb2.append(character);
        }
        return sb2.toString();
    }

    /**
     * 将一个bytes转成二进制字符串
     *
     * @param flag 标识是否需要补高位，如果是最后一个字节，无需补高位
     * @return
     */
    public static String byte2BitString(boolean flag, byte b) {
        int temp = b;
        if (flag) {
            //如果是正数，我们得补高位
            temp |= 256;
        }
        String binaryString = Integer.toBinaryString(temp);//返回的是补码
        if (flag) {
            return binaryString.substring(binaryString.length() - 8);
        } else {
            return binaryString;
        }
    }

    /**
     * @param str 原始字符串
     * @param map 赫夫曼编码表
     * @return 编码之后的字节数组 8位一组，放入数组当中
     */
    public static byte[] zip(String str, Map<Character, String> map) {
        //1.利用赫夫曼编码表转成赫夫曼编制之后的字符串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append(map.get(str.charAt(i)));
        }
        //将sb换变成byte数组
        //统计返回的字节数组的长度
        int len = (sb.length() + 7) / 8;
        //创建存储压缩后的数组
        byte[] zipBytes = new byte[len];
        //步长为8.每8位对应一个byte
        //记录第几个byte
        int index = 0;
        for (int i = 0; i < sb.length(); i += 8) {
            String s = "";
            if ((i + 8) > sb.length()) {
                //最后一次不足8位，直接截取到最后
                s = sb.substring(i);
            } else {
                s = sb.substring(i, i + 8);
            }
            zipBytes[index] = (byte) Integer.parseInt(s, 2);
            index++;
        }
        return zipBytes;
    }

    public static void getCodes(HuffManTree.Node root) {
        if (root == null) return;
        //处理左子树
        getCodes(root.left, "0", sb);
        //处理右子树
        getCodes(root.right, "1", sb);
    }

    /**
     * 生成赫夫曼编码表
     * 1、将赫夫曼编码表存放在map当中
     * 2、在生成编码表时，需要拼接路径，StringBuilder,存储叶子节点的路径
     *
     * @param node 传入节点
     * @param code 路径，左边是0 右边是1
     * @param sb   用于拼接路径
     */
    public static void getCodes(HuffManTree.Node node, String code, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder(sb);
        //将code加入到sb2
        sb2.append(code);
        if (node != null) {
            //判断当前节点是否是叶子节点
            if (node.c == null) {//非叶子节点
                //递归处理
                //向左
                getCodes(node.left, "0", sb2);
                //向右
                getCodes(node.right, "1", sb2);
            } else {
                //是叶子节点
                huffManCodes.put(node.c, sb2.toString());
            }
        }
    }

    /**
     * 将一个文件进行压缩
     */
}

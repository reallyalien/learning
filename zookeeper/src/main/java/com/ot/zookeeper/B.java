package com.ot.zookeeper;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Title: A
 * @Author wangtao
 * @Date 2023/7/20 10:36
 * @description:
 */
public class B {

    public static void main(String[] args) {
        Node a = new Node(0, "A", null);
        Node b = new Node(1, "B", null);
        Node c = new Node(2, "C", null);
        Node d = new Node(3, "D", null);
        Node e = new Node(4, "E", null);
        Node f = new Node(5, "F", null);
        Node g = new Node(6, "G", null);
        Node h = new Node(7, "H", null);
        Node i = new Node(8, "I", null);
        List<Node> nodes = Arrays.asList(a, b, c, d, e, f, g, h, i);

        Line line1 = new Line(0, 1);
        Line line2 = new Line(1, 4);
        Line line3 = new Line(4, 8);
        Line line4 = new Line(2, 1);
        Line line5 = new Line(2, 3);
        Line line6 = new Line(3, 4);
        Line line7 = new Line(5, 6);
        Line line8 = new Line(6, 7);
        Line line9 = new Line(7, 8);
        Line line10 = new Line(-1, 0);
        Line line11 = new Line(-1, 2);
        Line line12 = new Line(-1, 3);
        List<Line> lines = Arrays.asList(line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, line11,line12);

        List<Line> collect = lines.stream().filter(z -> z.getFrom().equals(-1)).collect(Collectors.toList());
        Line firstLine = collect.get(0);
        Node firstNode = nodes.stream().filter(z -> z.getId().equals(firstLine.getTo())).findFirst().get();
        p(lines, nodes, firstNode, null);
    }


    public static void p(List<Line> lines, List<Node> nodes, Node node, List<Integer> next) {
        Queue<Node> queue = new LinkedList<>();
        if (node != null) {
            queue.offer(node);
        }
        if (next != null) {
            for (int i = 0; i < next.size(); i++) {
                int finalI = i;
                Node node1 = nodes.stream().filter(e -> e.getId().equals(next.get(finalI))).findFirst().get();
                queue.offer(node1);
            }
        }
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur.data != null) {
                continue;
            }
            //向前寻找一次
            List<Integer> tmp = lines.stream().filter(i -> i.getTo().equals(cur.id) && i.getFrom() != -1).map(Line::getFrom).collect(Collectors.toList());
            //剔除已经处理过的
            List<Integer> tmp22 = new ArrayList<>();
            for (Integer i : tmp) {
                Optional<Node> first = nodes.stream().filter(node1 -> node1.getId().equals(i) && node1.data == null).findFirst();
                if (first.isPresent()) {
                    tmp22.add(i);
                }
            }

            if (tmp22.isEmpty()) {
                //当前节点没有前节点或前节点均处理完毕,处理当前节点
                cur.setData("测试数据" + cur.getName());
                System.out.println(cur);
                List<Integer> next1 = findNext(lines, cur);
                p(lines, nodes, null, next1);
            } else {
                //向前寻找最前面尚未被处理的节点
                Integer pre = findPre(lines, tmp22);
                Optional<Node> first = nodes.stream().filter(i -> pre.equals(i.getId()) && i.getData() == null).findFirst();
                if (first.isPresent()) {
                    Node earlastNode = first.get();
                    queue.offer(earlastNode);
                }
            }
        }
    }

    public static Integer findPre(List<Line> lines, List<Integer> tmp) {
        Integer result = null;
        while (!tmp.isEmpty()) {
            List<Integer> finalTmp = tmp;
            result = tmp.get(0);
            tmp = lines.stream().filter(i -> i.getTo().equals(finalTmp.get(0)) && i.getFrom() != -1).map(Line::getFrom).collect(Collectors.toList());
        }
        return result;
    }

    public static List<Integer> findNext(List<Line> lines, Node node) {
        List<Integer> collect = lines.stream().filter(i -> i.getFrom().equals(node.id)).map(Line::getTo).collect(Collectors.toList());
        return collect;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Node {
        private Integer id;
        private String name;
        private String data;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Line {
        private Integer from;
        private Integer to;
    }
}

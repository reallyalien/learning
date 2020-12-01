package com.ot.netty.codec3.msgpack;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MsgApi {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        list.add("java");
        list.add("c++");
        list.add("py");
        MessagePack messagePack = new MessagePack();
        byte[] bytes = messagePack.write(list);
        List<String> list1 = messagePack.read(bytes, Templates.tList(Templates.TString));
        System.out.println(list1);
    }
}

package com.questpirates.greathomesfurniture.dataBlock;

import java.util.ArrayList;
import java.util.List;

public class chatHolderBlock {

    public static List<String> chatList;

    static {
        chatList = new ArrayList<>();

        chatList.add("user@How are you?");
        chatList.add("rose@I'm good, How are you doing?");
        chatList.add("user@Fine");
        chatList.add("rose@How may i help you?");
    }

}

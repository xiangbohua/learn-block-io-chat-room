package com.xiangbohua.chat.client.tool;

import com.xiangbohua.chat.common.tool.MessageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiangbohua
 * @date 2020/8/19 14:09
 */
public class ConsoleState {
    private List<String> supportedCommand;

    private Map<Integer, String> autoFill;

    private String actionHolder;

    private String  help;

    public ConsoleState() {
        this.autoFill = new HashMap<>();
        this.supportedCommand = new ArrayList<>();
    }



    public void setAutoFill(Integer index, String value) {
        this.autoFill.put(index, value);
    }

    public void getRealCommand(String command) throws Exception {
        String actionName = MessageUtil.getAction(command);
        if (!supportedCommand.stream().anyMatch(x->x.equals(actionName))) {
            throw new Exception("Action not supported in this window");
        }







    }

}

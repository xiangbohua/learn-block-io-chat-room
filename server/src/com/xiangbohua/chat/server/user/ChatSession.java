package com.xiangbohua.chat.server.user;

import com.xiangbohua.chat.server.common.ChatMessage;
import com.xiangbohua.chat.server.common.Constant;
import com.xiangbohua.chat.server.common.MessageType;
import com.xiangbohua.chat.server.msg.IMessageSender;
import com.xiangbohua.chat.server.msg.MessageSenderContainer;
import com.xiangbohua.chat.common.tool.StorageUtil;
import com.xiangbohua.chat.common.tool.StorageUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author xiangbohua
 * @date 2020/8/18 15:53
 */
public class ChatSession {

    public String sessionId;

    public List<String> relatedUsers;

    public LocalDateTime createTime;

    private static String filePath = "/session";

    private static List<ChatSession> sessionInfo = new ArrayList<>();

    static {
        StorageUtil.initPath(filePath);
    }

    public static void loadSessionInfo() {
        Map<String, List<String>> allUserChats = Chat.getAllUserChats();

        allUserChats.values().forEach((x)->{
            x.forEach(y->{
                try {
                    ChatSession s = loadSessionInfo(y);
                    if (s != null) {
                        sessionInfo.add(s);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private static String getFilePath(String sessionId) {
        return filePath + "/" + sessionId +".txt";
    }

    public static void newChatSession(String userName, String newFriend) throws IOException {
        boolean hasSession = false;

        ChatSession oldSession = sessionInfo.stream()
                .filter(
                        x->{
                            String mm = String.join("", x.relatedUsers);
                            return (userName + newFriend).equals(mm) || (newFriend + userName).equals(mm);
                        }
                ).findFirst().orElse(null);

        if (Objects.isNull(oldSession)) {
            String sessionId = UUID.randomUUID().toString();
            ChatSession newSession = new ChatSession();
            newSession.sessionId = sessionId;
            newSession.relatedUsers = new ArrayList<>();
            newSession.relatedUsers.add(userName);
            newSession.relatedUsers.add(newFriend);
            newSession.createTime = LocalDateTime.now();
            newSession.saveSessionInfo();
            sessionInfo.add(newSession);

            Chat.newChat(userName, sessionId);
        }
    }

    public static ChatSession loadSessionInfo(String sessionId) throws IOException {
        String storagePath = getFilePath(sessionId);
        List<String> info = StorageUtil.loadAllLine(storagePath);
        if (info == null || info.size() == 0) {
            return null;
        }
        String[] infoArr = info.get(0).split(":");
        List<String> users = Arrays.asList(infoArr[0].split(","));
        LocalDateTime createTime = LocalDateTime.parse(infoArr[1], DateTimeFormatter.ofPattern(Constant.DT_FMT));


        ChatSession chatSession = new ChatSession();
        chatSession.sessionId = sessionId;
        chatSession.relatedUsers = users;
        chatSession.createTime = createTime;

        return chatSession;
    }

    public static ChatSession getInstance(String sessionId) {
        return sessionInfo.stream().filter(x->x.sessionId.equals(sessionId)).findFirst().orElse(null);
    }

    public void saveSessionInfo() throws IOException {
        String sessionInfo = String.join(",", this.relatedUsers) + ":" + this.createTime.format(DateTimeFormatter.ofPattern(Constant.DT_FMT));
        StorageUtil.createOrFillFile(getFilePath(this.sessionId), sessionInfo);
    }


    public void sendMessage(String sendUser, ChatMessage message) throws Exception {
        MessageType type = MessageType.valueOf(message.contentType);
        IMessageSender sender = MessageSenderContainer.getByType(type);
        if (sender == null) {
            throw new Exception("Message type not supported" + message.contentType);
        }
        for (String userName : this.relatedUsers) {
            if (!sendUser.equals(userName)) {
                sender.sendMessage(userName, message);
            }
        }
    }
}

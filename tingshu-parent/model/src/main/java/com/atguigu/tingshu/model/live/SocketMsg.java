package com.atguigu.tingshu.model.live;


import lombok.Data;
import lombok.Getter;

/**
 * 这里我们就不能使用简单的文本消息进行消息的发送了，我们使用json进行消息的发送。
 * 所以需要先创建一个消息对象，里面包含了消息发送者，消息接受者，消息类型（单聊还是群聊），还是就是消息，如下：
 */
@Data
public class SocketMsg {

    @Getter
    public enum MsgTypeEnum {

        HEART_BEAT("0","心跳信息"),
        PUBLIC_MSG("1","公共聊天消息"),
        JOIN_CHAT("2","加入聊天室"),
        CLOSE_SOCKET("3","退出聊天室"),
        ONLINE_NUM("4","在线用户数"),
        TOKEN_INVALID("-1","token无效"),
        ;

        private String code;
        private String data;

        MsgTypeEnum(String code, String data) {
            this.code = code;
            this.data = data;
        }

    }

    //直播房间id
    private Long liveRoomId;
//    //消息体
//    private MsgBody msgBody;

    //消息类型
    private String msgType;
    //消息内容
    private Object msgContent;

    //发送者
    private FromUser fromUser;
    //时间戳
    private Long timestamp;
}

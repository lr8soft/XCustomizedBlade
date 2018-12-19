package net.lrsoft.xcustomizedblade.XCBFunc;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class XCBNetworkSynchronize  implements IMessage {

    private String sender;

    public XCBNetworkSynchronize() {}

    public XCBNetworkSynchronize(String sender) {
        this.sender = sender;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        char chars[] = new char[buf.readInt()]; //读取字符串长度
        for(int i=0;i<chars.length;i++) //读取字符
            chars[i] = buf.readChar();
        sender = String.valueOf(chars); //创建字符串对象
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(sender.length()); //写入字符串长度
        for(char c:sender.toCharArray()) //写入字符串
            buf.writeChar(c); 
    }
    
}


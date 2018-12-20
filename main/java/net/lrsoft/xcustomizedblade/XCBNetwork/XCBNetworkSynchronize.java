package net.lrsoft.xcustomizedblade.XCBNetwork;

import cpw.mods.fml.common.network.ByteBufUtils;
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
        sender = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf,sender);
    }
}


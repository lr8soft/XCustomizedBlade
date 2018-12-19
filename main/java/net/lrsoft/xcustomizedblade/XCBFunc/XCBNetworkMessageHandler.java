package net.lrsoft.xcustomizedblade.XCBFunc;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class XCBNetworkMessageHandler implements IMessageHandler<XCBNetworkSynchronize, IMessage> {

    @Override
    public IMessage onMessage(XCBNetworkSynchronize message, MessageContext ctx) {
        return null;
    }
}

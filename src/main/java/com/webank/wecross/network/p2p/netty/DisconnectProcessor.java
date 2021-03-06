package com.webank.wecross.network.p2p.netty;

import com.webank.wecross.account.AccountSyncManager;
import com.webank.wecross.network.p2p.netty.common.Node;
import com.webank.wecross.network.p2p.netty.message.processor.Processor;
import com.webank.wecross.network.p2p.netty.message.proto.Message;
import com.webank.wecross.peer.Peer;
import com.webank.wecross.peer.PeerManager;
import com.webank.wecross.zone.ZoneManager;
import io.netty.channel.ChannelHandlerContext;

public class DisconnectProcessor implements Processor {
    private PeerManager peerManager;
    private ZoneManager zoneManager;
    private AccountSyncManager accountSyncManager;

    @Override
    public String name() {
        return "DisconnectProcessor";
    }

    @Override
    public void process(ChannelHandlerContext ctx, Node node, Message message) {
        Peer peer = peerManager.getPeerInfo(node);
        if (peer != null) {
            zoneManager.removeRemoteChains(peer, peer.getChainInfos(), true);
            peerManager.removePeerInfo(node);
            accountSyncManager.removeNodeSeq(node);
        }
    }

    public PeerManager getPeerManager() {
        return peerManager;
    }

    public void setPeerManager(PeerManager peerManager) {
        this.peerManager = peerManager;
    }

    public ZoneManager getZoneManager() {
        return zoneManager;
    }

    public void setZoneManager(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    public void setAccountSyncManager(AccountSyncManager accountSyncManager) {
        this.accountSyncManager = accountSyncManager;
    }
}

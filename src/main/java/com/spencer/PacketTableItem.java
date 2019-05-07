package com.spencer;

public class PacketTableItem {
    private String packetTimestamp = null;
    private String packetDestinationAddr = null;
    private String packetSourceAddr = null;
    private String packetType = null;

    public PacketTableItem() {

    }

    PacketTableItem(String packetTimestamp, String packetSourceAddr, String packetDestinationAddr, String packetType) {
        this.packetTimestamp = packetTimestamp;
        this.packetSourceAddr = packetSourceAddr;
        this.packetDestinationAddr = packetDestinationAddr;
        this.packetType = packetType;
    }

    public String getPacketTimestamp() {
        return packetTimestamp;
    }

    public void setPacketTimestamp(String packetTimestamp) {
        this.packetTimestamp = packetTimestamp;
    }

    public String getPacketDestinationAddr() {
        return packetDestinationAddr;
    }

    public void setPacketDestinationAddr(String packetDestinationAddr) {
        this.packetDestinationAddr = packetDestinationAddr;
    }

    public String getPacketSourceAddr() {
        return packetSourceAddr;
    }

    public void setPacketSourceAddr(String packetSourceAddr) {
        this.packetSourceAddr = packetSourceAddr;
    }

    public String getPacketType() {
        return packetType;
    }

    public void setPacketType(String packetType) {
        this.packetType = packetType;
    }


}

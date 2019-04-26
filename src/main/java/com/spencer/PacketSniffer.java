package com.spencer;

import org.pcap4j.core.*;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketSniffer {

    public List<PcapNetworkInterface> getNetworkDeviceList() throws IOException {
        List<PcapNetworkInterface> allDevices;

        try {
            allDevices = Pcaps.findAllDevs();
        } catch (PcapNativeException e) {
            throw new IOException(e.getMessage());
        }

        if (allDevices == null || allDevices.isEmpty()) {
            throw new IOException("No NIF to capture");
        }

        return allDevices;
    }

    public ArrayList<PcapPacket> sniffPackets(int maxPackets, PcapNetworkInterface device)
            throws PcapNativeException, NotOpenException {
        ArrayList<PcapPacket> packetList = new ArrayList<>();

        // Open handle to capture packets
        int snapshotLength = 65536;
        int readTimeout = 50;
        PromiscuousMode mode = PromiscuousMode.PROMISCUOUS;
        final PcapHandle handle = device.openLive(snapshotLength, mode, readTimeout);

        // Set packet listener
        PacketListener packetListener = packetList::add;

        try {
            handle.loop(maxPackets, packetListener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        handle.close();

        return packetList;
    }
}

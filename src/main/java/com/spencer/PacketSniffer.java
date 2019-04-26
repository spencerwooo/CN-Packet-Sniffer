package com.spencer;

import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

import java.io.IOException;
import java.util.List;

class PacketSniffer {

    List<PcapNetworkInterface> getNetworkDeviceList() throws IOException {
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
}

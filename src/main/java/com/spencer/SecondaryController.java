package com.spencer;

import javafx.fxml.FXML;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapPacket;

import java.io.IOException;
import java.util.ArrayList;

public class SecondaryController {

    private PcapNetworkInterface device;

    public void initialize() throws PcapNativeException, NotOpenException {
        PacketSniffer packetSniffer = new PacketSniffer();
        int maxPackets = 5;
        ArrayList<PcapPacket> packets = packetSniffer.sniffPackets(maxPackets, device);
        System.out.println(packets);
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
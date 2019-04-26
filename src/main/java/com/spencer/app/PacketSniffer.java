package com.spencer.app;

import java.util.ArrayList;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.PcapPacket;
import org.pcap4j.util.NifSelector;

/**
 * PacketSniffer
 */
public class PacketSniffer {

  public PcapNetworkInterface getNetworkDeviceList() {
    PcapNetworkInterface devices = null;
    try {
      devices = new NifSelector().selectNetworkInterface();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return devices;
  }

  public ArrayList<PcapPacket> sniffPackets(int maxPackets, PcapNetworkInterface device)
      throws PcapNativeException, NotOpenException {
    ArrayList<PcapPacket> packetList = new ArrayList<PcapPacket>();
    // Open handle to capture packets
    int snapshotLength = 65536;
    int readTimeout = 50;
    PromiscuousMode mode = PromiscuousMode.PROMISCUOUS;
    final PcapHandle handle = device.openLive(snapshotLength, mode, readTimeout);

    // Set packet listener
    PacketListener packetListener = packet -> {
      packetList.add(packet);
    };

    try {
      handle.loop(maxPackets, packetListener);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    handle.close();

    return packetList;
  }
}
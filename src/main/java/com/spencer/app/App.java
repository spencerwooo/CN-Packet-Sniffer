package com.spencer.app;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.util.NifSelector;

/**
 * Hello world!
 *
 */
public class App {

  public static PcapNetworkInterface getNetworkDeviceList() {
    PcapNetworkInterface devices = null;
    try {
      devices = new NifSelector().selectNetworkInterface();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return devices;
  }

  public static void main(String[] args) throws PcapNativeException, NotOpenException {
    PcapNetworkInterface device = getNetworkDeviceList();

    if (device == null) {
      System.out.println("No device selected, exiting.");
      System.exit(0);
    }

    System.out.println(device.getName() + "[" + device.getDescription() + "]");

    // Open handle to capture packets
    int snapshotLength = 65536;
    int readTimeout = 50;
    PromiscuousMode mode = PromiscuousMode.PROMISCUOUS;
    final PcapHandle handle = device.openLive(snapshotLength, mode, readTimeout);

    // Set packet listener
    PacketListener packetListener = packet -> {
      System.out.println(packet.getTimestamp());
      System.out.println(packet);
    };

    try {
      int maxPackets = 5;
      handle.loop(maxPackets, packetListener);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    handle.close();
  }
}

package com.spencer.app;

import java.util.ArrayList;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapPacket;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {

  private static final int maxPackets = 5;

  public void start(Stage stage) throws PcapNativeException, NotOpenException {
    PacketSniffer packetSniffer = new PacketSniffer();
    PcapNetworkInterface device = packetSniffer.getNetworkDeviceList();
    ArrayList<PcapPacket> packetList = new ArrayList<PcapPacket>();

    if (device == null) {
      System.out.println("No device selected, exiting.");
      System.exit(0);
    }

    // System.out.println(device.getName() + "[" + device.getDescription() + "]");

    Label l = new Label(device.getName() + "[" + device.getDescription() + "]");

    Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();

    // packetList = packetSniffer.sniffPackets(maxPackets, device);
    // System.out.println(packetList);
  }

  public static void main(String[] args)  {
    launch();
  }
}

package com.spencer.app;

import org.pcap4j.core.PcapNetworkInterface;
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
    public static void main(String[] args) throws Exception {
        PcapNetworkInterface device = getNetworkDeviceList();
        System.out.println(device);
    }
}

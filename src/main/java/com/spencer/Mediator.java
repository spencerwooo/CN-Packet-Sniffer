package com.spencer;

import org.pcap4j.core.PcapNetworkInterface;

public class Mediator {
    private final PcapNetworkInterface device;

    public Mediator(PcapNetworkInterface device) {
        this.device = device;
    }

    public PcapNetworkInterface getDevice() {
        return device;
    }


}

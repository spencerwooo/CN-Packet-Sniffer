package com.spencer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.pcap4j.core.PcapNetworkInterface;

import java.io.IOException;

public class SecondaryController {

    private PcapNetworkInterface device;

    @FXML
    private Label deviceLabel;

    void setDevice(PcapNetworkInterface device) {
        this.device = device;
        String deviceLabelPretext = "Packets on device: ";
        deviceLabel.setText(deviceLabelPretext + device.getName());
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
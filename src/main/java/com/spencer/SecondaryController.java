package com.spencer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.pcap4j.core.*;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.packet.EthernetPacket;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SecondaryController implements Initializable {

    private PcapNetworkInterface device;

    private ArrayList<PcapPacket> packetList = new ArrayList<>();

    @FXML
    private Label deviceLabel;
    @FXML
    private Label timestampLabelView;
    @FXML
    private Label payloadLabelView;

    @FXML
    private ListView<String> packetListView;

    private ObservableList<String> observableList = FXCollections.observableArrayList();

    void setDevice(PcapNetworkInterface device) {
        this.device = device;
        String deviceLabelPretext = "Packets on device: ";
        deviceLabel.setText(deviceLabelPretext + device.getName());
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void startSniffing() throws PcapNativeException, NotOpenException {
        int maxPackets = 5;

        // Open handle to capture packets
        int snapshotLength = 65536;
        int readTimeout = 1000;
        PromiscuousMode mode = PromiscuousMode.PROMISCUOUS;
        PcapHandle handle = device.openLive(snapshotLength, mode, readTimeout);

//        String filter = "tcp port 80";
//        handle.setFilter(filter, BpfProgram.BpfCompileMode.OPTIMIZE);

        PacketListener packetListener = packet -> {
            System.out.println(packet);
            packetList.add(packet);

            if (packet.contains(EthernetPacket.class)) {
                observableList.add(packet.get(EthernetPacket.class).getHeader().toString());
//                System.out.println(packet.get(EthernetPacket.class).getHeader().getSrcAddr());
            } else {
                observableList.add(packet.toString());
            }
        };

        try {
            handle.loop(maxPackets, packetListener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        handle.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) throws NullPointerException {
        packetListView.setItems(observableList);

        packetListView.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            int index = packetListView.getSelectionModel().getSelectedIndex();
            PcapPacket selectedPacket = packetList.get(index);

            DataHandler dataHandler = new DataHandler();

            timestampLabelView.setText(dataHandler.timestampFormatter(selectedPacket.getTimestamp()));
            payloadLabelView.setText(selectedPacket.getPayload().toString());
        });
    }
}
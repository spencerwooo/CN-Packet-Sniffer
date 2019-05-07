package com.spencer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private ScrollPane scrollPaneView;

    @FXML
    private TableView<PacketTableItem> packetTableView;
    @FXML
    private TableColumn<PacketTableItem, String> packetTime;
    @FXML
    private TableColumn<PacketTableItem, String> packetSource;
    @FXML
    private TableColumn<PacketTableItem, String> packetDestination;
    @FXML
    private TableColumn<PacketTableItem, String> packetType;

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
        int maxPackets = 10;

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

            DataHandler dataHandler = new DataHandler();

            EthernetPacket.EthernetHeader ethernetHeader = packet.get(EthernetPacket.class).getHeader();

            String packetTimestamp = dataHandler.timestampFormatter(packet.getTimestamp());
            String packetSourceAddr = ethernetHeader.getSrcAddr().toString();
            String packetDestinationAddr = ethernetHeader.getDstAddr().toString();
            String packetType = ethernetHeader.getType().toString();

            packetTableView.getItems().add(new PacketTableItem(packetTimestamp, packetSourceAddr, packetDestinationAddr, packetType));

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
        scrollPaneView.setFitToWidth(true);

        packetTime.setCellValueFactory(new PropertyValueFactory<>("packetTimestamp"));
        packetSource.setCellValueFactory(new PropertyValueFactory<>("packetSourceAddr"));
        packetDestination.setCellValueFactory(new PropertyValueFactory<>("packetDestinationAddr"));
        packetType.setCellValueFactory(new PropertyValueFactory<>("packetType"));

        packetTableView.getSelectionModel().selectedIndexProperty().addListener((number) -> {
            int index = packetTableView.getSelectionModel().getSelectedIndex();
            PcapPacket selectedPacket = packetList.get(index);

            DataHandler dataHandler = new DataHandler();

            timestampLabelView.setText(dataHandler.timestampFormatter(selectedPacket.getTimestamp()));
            payloadLabelView.setText(selectedPacket.getPayload().toString());
        });
    }
}
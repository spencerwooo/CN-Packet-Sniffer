package com.spencer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.pcap4j.core.PcapNetworkInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrimaryController {

    @FXML
    private ListView<String> deviceListView;
    @FXML
    private Label selectedDevice;

    private ArrayList<String> listViewData = new ArrayList<>();
    private ObservableList observableList = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        PacketSniffer packetSniffer = new PacketSniffer();
        List<PcapNetworkInterface> allDevices = packetSniffer.getNetworkDeviceList();

        for (PcapNetworkInterface device : allDevices) {
            listViewData.add(device.toString());
        }

        observableList.addAll(listViewData);
        deviceListView.setItems(observableList);

        deviceListView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> selectedDevice.setText(t1));
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}

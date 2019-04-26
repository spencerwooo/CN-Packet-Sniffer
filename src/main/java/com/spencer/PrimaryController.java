package com.spencer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private List<PcapNetworkInterface> allDevices;

    public void initialize() throws IOException {
        PacketSniffer packetSniffer = new PacketSniffer();
        allDevices = packetSniffer.getNetworkDeviceList();

        for (PcapNetworkInterface device : allDevices) {
            listViewData.add(device.getName());
        }

        observableList.addAll(listViewData);
        deviceListView.setItems(observableList);

        deviceListView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            int index = deviceListView.getSelectionModel().getSelectedIndex();
            selectedDevice.setText(allDevices.get(index).toString());
        });
    }

    @FXML
    private void switchToSecondary() throws IOException {
        int index = deviceListView.getSelectionModel().getSelectedIndex();
        System.out.println(index);

        if (index < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You didn't select anything.");
            alert.showAndWait();
        } else {
            PcapNetworkInterface selectedDevice = allDevices.get(index);
            System.out.println(selectedDevice);

            App.setRoot("secondary");
        }

    }

}

package com.spencer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private Label deviceNameLabel;
    @FXML
    private Label deviceDescriptionLabel;
    @FXML
    private Label deviceAddressLabel;
    @FXML
    private Label deviceLinkLayerAddressLabel;
    @FXML
    private Label deviceLoopBackLabel;
    @FXML
    private Label deviceUpLabel;
    @FXML
    private Label deviceRunningLabel;
    @FXML
    private Label deviceLocalLabel;

    private ArrayList<String> listViewData = new ArrayList<>();
    private ObservableList<String> observableList = FXCollections.observableArrayList();
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
            PcapNetworkInterface selectedDevice = allDevices.get(index);

            System.out.println(selectedDevice.toString());

            deviceNameLabel.setText(selectedDevice.getName());
            deviceDescriptionLabel.setText(selectedDevice.getDescription());
            deviceAddressLabel.setText(selectedDevice.getAddresses().toString());
            deviceLinkLayerAddressLabel.setText(selectedDevice.getLinkLayerAddresses().toString());
            deviceLoopBackLabel.setText(Boolean.toString(selectedDevice.isLoopBack()));
            deviceUpLabel.setText(Boolean.toString(selectedDevice.isUp()));
            deviceRunningLabel.setText(Boolean.toString(selectedDevice.isRunning()));
            deviceLocalLabel.setText(Boolean.toString(selectedDevice.isLocal()));
        });
    }

    @FXML
    private void switchToSecondary() throws IOException {
        int index = deviceListView.getSelectionModel().getSelectedIndex();

        if (index < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You didn't select anything.");
            alert.showAndWait();
        } else {
            PcapNetworkInterface selectedDevice = allDevices.get(index);

            FXMLLoader loader = new FXMLLoader(App.class.getResource("secondary.fxml"));
            Parent parent = loader.load();

            SecondaryController secondaryController = loader.getController();
            secondaryController.setDevice(selectedDevice);

            Scene scene = deviceNameLabel.getScene();
            scene.setRoot(parent);
        }

    }

}

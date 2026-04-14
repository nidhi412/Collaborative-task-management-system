package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Member;

import java.net.URL;
import java.util.ResourceBundle;

public class MemberController implements Initializable {

    @FXML
    private TableView<Member> tbVMembers;

    @FXML
    private TableColumn<Member, String> tcName;

    @FXML
    private TableColumn<Member, String> tcMobile;

    @FXML
    private TableColumn<Member, String> tcEmail;

    @FXML
    private TableColumn<Member, String> tcAction;

    private ObservableList<Member> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tcAction.setCellValueFactory(new PropertyValueFactory<>("action"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcMobile.setCellValueFactory(new PropertyValueFactory<>("phone"));

        data = FXCollections.observableArrayList(
            new Member("Philip Awini", "8073569713", "pawini@lakeheadu.ca", "call"),
            new Member("Nidhi Patel", "07245654", "npatel@gail.com", "edit")
        );

        tbVMembers.setItems(data);
    }
}
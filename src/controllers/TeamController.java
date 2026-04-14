package controllers;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Member;

import java.net.URL;
import java.util.ResourceBundle;

public class TeamController implements Initializable {

	@FXML
    private Button inviteAdminButton;

    @FXML
    private Button inviteMemberButton;

    @FXML
    private Button saveAdminButton;

    @FXML
    private Button saveMemberButton;

    @FXML
    private TableView<Member> tbVMembers;

    @FXML
    private TableView<Member> tbVAdmin;

    @FXML
    private TableColumn<Member, String> tcName;

    @FXML
    private TableColumn<Member, String> tcMobile;

    @FXML
    private TableColumn<Member, String> tcEmail;

    @FXML
    private TableColumn<Member, String> tcAction;

    @FXML
    private TableColumn<Member, String> tcName1;

    @FXML
    private TableColumn<Member, String> tcMobile1;

    @FXML
    private TableColumn<Member, String> tcEmail1;

    @FXML
    private TableColumn<Member, String> tcAction1;

    @FXML
    private Button teamButton;

    private ObservableList<Member> data;
    private ObservableList<Member> data2;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	 // Initialize columns for tbVMembers
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcMobile.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        // Initialize columns for tbVAdmin
        tcName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcMobile1.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tcEmail1.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcAction1.setCellValueFactory(new PropertyValueFactory<>("action"));

        // Add sample data for members
        data = FXCollections.observableArrayList(
                new Member("Philip Awini", "8073569713", "pawini@lakeheadu.ca", "Remove"),
                new Member("Nidhi Patel", "07245654", "npatel@gail.com", "Remove")
        );

        // Add sample data for admins
        data2 = FXCollections.observableArrayList(
                new Member("Nidhi Patel", "07245654", "npatel@gail.com", "Manage")
        );

        // Set data to tables
        tbVMembers.setItems(data);
        tbVAdmin.setItems(data2);

        // Set custom cell factory for the action columns
        setActionCellFactory(tcAction);
        setActionCellFactory(tcAction1);
    }

    /**
     * Set a custom CellFactory for the Action column to display buttons.
     */
    private void setActionCellFactory(TableColumn<Member, String> column) {
        column.setCellFactory(col -> new TableCell<>() {
            private final Button actionButton = new Button();

            @Override
            protected void updateItem(String action, boolean empty) {
                super.updateItem(action, empty);

                if (empty || action == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    actionButton.setText(action);
                    actionButton.setOnAction(event -> {
                        Member member = getTableView().getItems().get(getIndex());
                        if ("Remove".equals(action)) {
                            getTableView().getItems().remove(member); // Remove row
                            System.out.println(member.getName() + " removed.");
                        } else if ("Manage".equals(action)) {
                            System.out.println("Manage clicked for: " + member.getName());
                        }
                    });
                    setGraphic(actionButton);
                    setText(null);
                }
            }
        });
    }
}
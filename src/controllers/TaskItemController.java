package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
//import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
//import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

public class TaskItemController implements Initializable {

    @FXML
    private Button taskStatus;

    @FXML
    private ImageView icon;

    @FXML
    private Label taskName;
    
    //private TasksModel taskModel;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    }
    
    public void setTaskName(TasksModel models) {
    	taskName.setText( models.getTaskName());
    	taskStatus.setText( models.getTaskStatus());
       	icon.setImage(models.getIcon());
    
       	/*
       	 ContextMenu menu = new ContextMenu();
    	if(models.getTaskStatus().equalsIgnoreCase("In Progress"))
    	{
    		menu.getItems().add(new MenuItem("Set Task Complete"));
    	} else {
    		menu.getItems().add(new MenuItem("Set Task In Progress"));
    	}
    	taskName.setContextMenu(menu);
       	 */
    }
    

}


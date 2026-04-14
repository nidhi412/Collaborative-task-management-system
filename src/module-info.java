/**
 * 
 */
/**
 * 
 */
module ESOF3050 {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
//    requires java.base;

    exports client_server.client;
    exports client_server.server;
   // exports main;
    exports controllers;
    exports models;
    exports util;
    exports dao;

    opens controllers to javafx.fxml;
    opens models to javafx.fxml;
    
}

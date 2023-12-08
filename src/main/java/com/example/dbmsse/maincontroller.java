package com.example.dbmsse;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class maincontroller implements Initializable {

    @FXML
    private Button table1b;
    @FXML
    private Label table1l;

    @FXML
    private Button table2b;
    @FXML
    private Label table2l;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        table1b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try{
                    Socket socket = new Socket("127.0.0.1", 8000); //connect here
                    System.out.println("connected to server");
                    //br=new BufferedReader(new InputStreamReader(socket.getInputStream())); //read form socket
                    PrintWriter prnt = new PrintWriter(socket.getOutputStream()); //write to socket
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream out= new ObjectOutputStream(socket.getOutputStream());
                    String c="whatever";
                    String fina = "usn1" + " " + "pass1"+" "+c+" "+1;
                    prnt.println(fina);
                    prnt.flush();



                    int rec=(int) in.readObject();
                    if(rec==5){
                        DBUtils.changescene(event,"suc.fxml","donebbb",null,null);
                    }
                    if (rec == 6) {

                        table1l.setText("Unavailable");
                    }
                }
                catch(Exception e){

                }

            }
        });


        table2b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try{
                    Socket socket = new Socket("127.0.0.1", 8000); //connect here
                    System.out.println("connected to server");
                    //br=new BufferedReader(new InputStreamReader(socket.getInputStream())); //read form socket
                    PrintWriter prnt = new PrintWriter(socket.getOutputStream()); //write to socket
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream out= new ObjectOutputStream(socket.getOutputStream());
                    String c="whatever";
                    String fina = "usn1" + " " + "pass1"+" "+c+" "+2;
                    prnt.println(fina);
                    prnt.flush();



                    int rec=(int) in.readObject();
                    if(rec==5){
                        DBUtils.changescene(event,"suc.fxml","donebbb",null,null);
                    }
                    if(rec==6){
                        table2l.setText("Unavailable");
                    }
                }
                catch(Exception e){

                }

            }
        });
        
    }
}
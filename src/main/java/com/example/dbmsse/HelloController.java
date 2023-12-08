package com.example.dbmsse;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;




public class HelloController implements Initializable{
    @FXML
    private Label welcomeText;

    @FXML
    private Button loginbtn;
    @FXML
    private Button signupbtn;
    @FXML
    private TextField usntf;
    @FXML
    private PasswordField passf;

    @FXML
    private Label loginMessageLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        signupbtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    DBUtils.changescene(event,"signup.fxml","SignUp",null,null);
                }
            });

        loginbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Socket socket = new Socket("127.0.0.1", 8000); //connect here
                    System.out.println("connected to server");
                    //br=new BufferedReader(new InputStreamReader(socket.getInputStream())); //read form socket
                    PrintWriter prnt = new PrintWriter(socket.getOutputStream()); //write to socket
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream out= new ObjectOutputStream(socket.getOutputStream());
                    String usn1 = usntf.getText().toString();
                    String pass1 = passf.getText().toString();
                    String c="login";
                    String fina = usn1 + " " + pass1+" "+c+" "+"nun";
                    usntf.setText("");
                    passf.setText("");
                    prnt.println(fina);
                    prnt.flush();
                    int rec=(int) in.readObject();
                    if(rec==2){
                        DBUtils.changescene(event,"testy.fxml","donebbb",null,null);
                    }
                    if(rec==3){
                        loginMessageLabel.setText("User not found!!");
                    }
                    if(rec==4){
                        loginMessageLabel.setText("Password is incorrect!!");
                    }
                    else{
                        System.out.println("no success");
                    }
                } catch (Exception e) {

                }
            }
        });

    }

}
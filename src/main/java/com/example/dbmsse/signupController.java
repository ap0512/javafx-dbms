package com.example.dbmsse;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class signupController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private Button loginbtn_sn;
    @FXML
    private Button signupbtn;
    @FXML
    private TextField usn_sn_tf;
    @FXML
    private PasswordField pass_sn_f;

    @FXML
    private Label loginMessageLabel;

    public signupController() {
    }

    public void signupbtnOnAction(ActionEvent e){
        if(usn_sn_tf.getText().isBlank()==true || pass_sn_f.getText().isBlank()==true){
            loginMessageLabel.setText("Enter the fields!");
        }
        else{
            loginMessageLabel.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        signupbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!usn_sn_tf.getText().trim().isEmpty() || !pass_sn_f.getText().trim().isEmpty()){
                    try {
                        Socket socket = new Socket("127.0.0.1", 8000); //connect here
                        System.out.println("connected to server");
                        //br=new BufferedReader(new InputStreamReader(socket.getInputStream())); //read form socket
                        PrintWriter prnt = new PrintWriter(socket.getOutputStream()); //write to socket
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream out= new ObjectOutputStream(socket.getOutputStream());
                        String usn1 = usn_sn_tf.getText().toString();
                        String pass1 = pass_sn_f.getText().toString();
                        String c="signup";
                        String fina = usn1 + " " + pass1+" "+c+" "+"nun";
                        usn_sn_tf.setText("");
                        pass_sn_f.setText("");
                        prnt.println(fina);
                        prnt.flush();
                        int rec=(int) in.readObject();
                        if(rec==1){
                            DBUtils.changescene(event,"testy.fxml","donebbb",null,null);
                        }
                        if(rec==5){
                            loginMessageLabel.setText("Username not available");
                        }
                        else{
                            System.out.println("no success");
                        }
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
                }

                else{
                    loginMessageLabel.setText("Enter the fields!");
                }
            }
        });
        loginbtn_sn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changescene(actionEvent,"hello-view.fxml","successful",null,null);
            }
        });

    }
}
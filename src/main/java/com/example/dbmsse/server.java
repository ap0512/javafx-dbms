package com.example.dbmsse;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class server {

    ServerSocket server;
    Socket socket;
    ObjectOutputStream out;

    Connection conn;
    ResultSet rslt;
    PreparedStatement prst;

    public server() {

        try {
            server = new ServerSocket(8000);
            System.out.println("server is listening");
            while (true) {
                socket = server.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter prnt = new PrintWriter(socket.getOutputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                String ss = br.readLine();

                Multiclient mltclient = new Multiclient(ss);
                new Thread(mltclient).start();

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public class Multiclient implements Runnable {
        String usnn;
        String passs;

        String choice;

        String tableno;


        public Multiclient(String ss) {
            String[] det = ss.split(" ");
            usnn = det[0];
            passs = det[1];
            choice = det[2];
            tableno= det[3];
        }

        @Override
        public void run() {
            if(choice.equals("whatever")) {
                try {

                    Connection connection = null;
                    PreparedStatement psInsert = null;
                    PreparedStatement psCheckUserExists = null;
                    PreparedStatement reserveadd=null;
                    ResultSet resultSet = null;
                    try {
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java-fx",
                                "root", "#commander0512");
                        psCheckUserExists = connection.prepareStatement("SELECT tableno FROM tables WHERE reserved = 0 and tableno = ?");
                        psCheckUserExists.setString(1, tableno);
                        resultSet = psCheckUserExists.executeQuery();
                        if (resultSet.isBeforeFirst()) {
                            reserveadd = connection.prepareStatement("update tables set reserved='1' WHERE  tableno = ?");
                            reserveadd.setString(1, tableno);
                            reserveadd.executeUpdate();
                            System.out.println("Table is free!");
                            int choice = 5;
                            out.writeObject(choice);

                        } else {
                            int choice = 6;
                            out.writeObject(choice);
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (resultSet != null) {
                            try {
                                resultSet.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (psCheckUserExists != null) {
                            try {
                                psCheckUserExists.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (psInsert != null) {
                            try {
                                psInsert.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (connection != null) {
                            try {
                                connection.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if(choice.equals("signup")){
                try {

                    Connection connection = null;
                    PreparedStatement psInsert = null;
                    PreparedStatement psCheckUserExists = null;
                    ResultSet resultSet = null;
                    try {
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java-fx",
                                "root", "#commander0512");
                        psCheckUserExists = connection.prepareStatement("SELECT * FROM info WHERE username = ?");
                        psCheckUserExists.setString(1, usnn);
                        resultSet = psCheckUserExists.executeQuery() ;
                        if (resultSet .isBeforeFirst()) {



                            System.out.println("User already exists!");
                            int choice = 5;
                            out.writeObject(choice);

//                            Alert alert = new Alert(Alert.AlertType.ERROR);
//                            alert.setContentText ("You cannot use this username.");
//                            alert.show();
                        } else {
                            psInsert = connection.prepareStatement ( "INSERT INTO info (username, password) VALUES (?, ?)");
                            psInsert.setString( 1, usnn);
                            psInsert.setString( 2, passs);
                            psInsert.executeUpdate();
                            int choice = 1;
                            out.writeObject(choice);


                        }
                    } catch (SQLException e) {
                        e.printStackTrace ();
                    } finally {
                        if (resultSet != null) {
                            try {
                                resultSet.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (psCheckUserExists != null) {
                            try {
                                psCheckUserExists.close ();
                            } catch (SQLException e) {
                                e.printStackTrace ();
                            }
                        }
                        if (psInsert != null) {
                            try {
                                psInsert.close();
                            } catch (SQLException e) {
                                e.printStackTrace ();
                            }
                        }
                        if (connection != null) {
                            try {
                                connection.close ();
                            } catch (SQLException e) {
                                e.printStackTrace() ;
                            }
                        }
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("you a failure");
            }


            if(choice.equals("login")){
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java-fx", "root", "#commander0512");
                    preparedStatement = connection.prepareStatement("SELECT password FROM info WHERE username = ?");
                    preparedStatement.setString(1, usnn);
                    resultSet = preparedStatement.executeQuery();
                    if (!resultSet.isBeforeFirst()) {
                        System.out.println("User not found in the database!");
                        int choicee = 3;
                        out.writeObject(choicee);


//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setContentText("Provided credentials are incorrect!");
//                        alert.show();
                    } else {
                        while (resultSet.next()) {
                            String retrievedPassword = resultSet.getString("password");

                            if (retrievedPassword.equals(passs)) {
                                int choicee = 2;
                                out.writeObject(choicee);


                            } else {
                                System.out.println("Passwords did not match!");
                                int choicee = 4;
                                out.writeObject(choicee);

//                                Alert alert = new Alert(Alert.AlertType.ERROR);
//                                alert.setContentText("The provided credentials are incorrect!");
//                                alert.show();
                            }
                        }

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (resultSet != null) {
                        try {
                            resultSet.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (preparedStatement != null) {
                        try {
                            preparedStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            else{
                System.out.println("you failed");
            }


        }
    }

    public static void main(String[] args) {
        server so=new server();

    }


}

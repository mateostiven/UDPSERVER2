package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws SocketException {


        final int PUERTO = 8050;
        byte[] buffer = new byte[38];

        Connection con;
        Statement st;




        try {


            System.out.println("Server has been started");
            DatagramSocket UDPSocket = new DatagramSocket(PUERTO);



            while (true) {


                DatagramPacket request = new DatagramPacket(buffer, buffer.length);

                UDPSocket.receive(request);

                String message = new String(request.getData());
                System.out.println(message);

                String[] split = message.split(",");




                try {


                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://taxi.cvejcfpsnjtv.us-west-2.rds.amazonaws.com:3306/taxi", "admin", "Personaje_26");
                    st = con.createStatement();



                    String query = "INSERT INTO datos (Fecha, Latitud, Longitud)VALUES("+split[0]+","+split[2]+","+split[1]+")";
                    st.executeUpdate(query);



             
                    



                } catch (Exception ex) {
                    ex.printStackTrace();


                }


            }

        } catch (SocketException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
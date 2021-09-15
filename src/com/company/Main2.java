package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main2 {

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

                System.out.println(Arrays.toString(split));



                try {


                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi2", "root", "");
                    st = con.createStatement();


                    LocalDate date = LocalDate.now();



                    System.out.print(date);

                    String query = "INSERT INTO datos2 (Fecha_Hora, Longitud, Latitud)VALUES(NOW(),NOW(),"+split[1]+", "+split[2]+")";
                    st.executeUpdate(query);



             
                    



                } catch (Exception ex) {
                    ex.printStackTrace();


                }


            }

        } catch (SocketException ex) {
            Logger.getLogger(Main2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
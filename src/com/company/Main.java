package com.company;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;




public class Main {

    public static void main(String[] args) throws SocketException {

        Config C = new Config();


        final int PUERTO = 8050;
        byte[] buffer = new byte[2000];

        Connection con;
        Statement st;




        try {

            String Host = C.Host();
            String Usuario = C.Usuario();
            String Clave = C.Clave();




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
                    con = DriverManager.getConnection(Host, Usuario, Clave);
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

package com.sw.banca.model;

import java.util.ArrayList;
import java.util.List;

public final class Bank {
    private static boolean INIT = false;
    private static List<Client> clientList = new ArrayList<>();

    private Bank(){
        throw new UnsupportedOperationException();
    }

    public static void initialize() {
        if(!INIT)
        {
            INIT = true;
            Bank.addClient(new Client(100, 1000));
            Bank.addClient(new Client(200, 2000));
            Bank.addClient(new Client(300, 3000));
        }
        else
        {
            System.out.println("Bank has already been initialized!");
        }
    }

    public static List<Client> getClientList(){
        return clientList;
    }

    public static void addClient(Client client){
        clientList.add(client);
    }

    public static boolean isRegistered(Client client){
        for(Client c: clientList){
            if(c.getCnp() == client.getCnp() && c.getPin() == client.getPin()){
                return true;
            }
        }
        return false;
    }

    public static void deleteClient(int cnp, int pin){
        for(Client c: clientList){
            if(c.getCnp() == cnp && c.getPin() == pin){
                clientList.remove(c);
                break;
            }
        }
    }

//    public static void deleteClient(int cnp, int pin){
//        for(Client c: clientList){
//            if(c.getCnp() == cnp && c.getPin() == pin){
//                clientList.remove(c);
//                break;
//            }
//        }
//    }

    public static void viewClients(){
        for(Client c: clientList){
            System.out.println("( " + c.getCnp() + ", " + c.getPin() + " )");
        }
    }
}

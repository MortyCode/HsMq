package com.hsms.mqserver;


import com.hsms.mqserver.reactor.ObjectReactor;

/**
 * @author ：河神
 * @date ：Created in 2021/6/4 3:46 下午
 */
public class ServerStartup {

    public static void main(String[] args) {
        start(args);
    }

    public static void start(String[] args){
        try {
            String start =
                    " _   _   _____       ___  ___   _____    \n" +
                    "| | | | /  ___/     /   |/   | /  _  \\   \n" +
                    "| |_| | | |___     / /|   /| | | | | |   \n" +
                    "|  _  | \\___  \\   / / |__/ | | | | | |   \n" +
                    "| | | |  ___| |  / /       | | | |_| |_  \n" +
                    "|_| |_| /_____/ /_/        |_| \\_______|  \n"+
                    "v 1.0.0 河神大人 \n";
            System.out.println(start);

            ObjectReactor baseServer = new ObjectReactor(9001);
            baseServer.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}

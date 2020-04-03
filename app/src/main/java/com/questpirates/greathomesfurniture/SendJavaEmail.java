package com.questpirates.greathomesfurniture;


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendJavaEmail {

    Session session = null;
    String tos, subjs, msgs, users;
    public Context context;

    public SendJavaEmail(String tos, String subjs, String msgs, String users) {
        this.tos = tos;
        this.subjs = subjs;
        this.msgs = msgs;
        this.users = users; //Username
        //this.context = context;
    }

    public void sendEmail() {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory,host", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ghfinc20@gmail.com", "Bl@nk3t20");
                //noreply.maarga@gmail.com
                //Test@maarga123
            }
        });

        RetriveFeedTask task = new RetriveFeedTask();
        task.execute();




    }




    class RetriveFeedTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("ghfinc20@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tos));
                message.setSubject(subjs);
                //String below = "<i> Be Sure to utilize the ticket/pass on the same day to avoid expiration. <br><br>  Please note, Tickets/Passes once purchased/scanned cannot be cancelled/modified under any circumstances. </i>";
                String signature = "Thanks, <br> Team Great Homes Furniture!";
                String below2 = "<i> You received this email because you purchased/utilized a Product from GHF Application. <br> If you haven't purchased/not the intended receipents please <a href=\"https://docs.google.com/forms/d/e/1FAIpQLSdDztWOFi7t1iX_1BwJL7O5xr0111R0whixCQJZUG1yhenuHw/viewform\"> Click Here </a>.</i>";
                String body = "Dear User, <br> <br> Thank you for your interest in GHF. <br> <br> " +
                        "Your Query has been received, our representative will get back to you in few a moment " +
                        "via in-app chat message / call/ email <br> <br> " + signature + "<br> <br> <br>" + below2;
                message.setContent(body, "text/html; charset=utf-8");

                Transport.send(message);


                Log.d("Email", "Sent Successfully");
            } catch (Exception e) {
                Log.d("Email", e + "");
            }

            return null;
        }


        @Override
        protected void onPostExecute(String result) {
        }


    }
}



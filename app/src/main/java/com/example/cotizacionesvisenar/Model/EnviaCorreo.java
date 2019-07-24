package com.example.cotizacionesvisenar.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviaCorreo extends AsyncTask<Void,Void,Void> {
    private Context contexto;
    private Session De_Sesion;

    private String A_Correo;
    private String C_Correo;
    private String A_Asunto;
    private String A_Mensaje;

    private ProgressDialog progreso;

    public EnviaCorreo(Context cContexto, String cCorreo, String ccCorreo, String cAsunto, String cMensaje) {
        this.contexto = cContexto;
        this.A_Correo = cCorreo;
        this.C_Correo = ccCorreo;
        this.A_Asunto = cAsunto;
        this.A_Mensaje = cMensaje;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progreso = ProgressDialog.show(contexto, "Enviando cotización al correo electrónico", "Espere...", false, false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progreso.dismiss();
        Toast.makeText(contexto, "Cotización enviada", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        Properties props = new Properties();
        /*=========================================================================================
                                         PARA GMAIL
         Requisito: se debe activar "Permitir que aplicaciones menos seguras accedan a tu cuenta"
         https://www.google.com/settings/security/lesssecureapps
          ===========================================================================================*/
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        De_Sesion = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Configuracion.DE_CORREO, Configuracion.DE_PASSWORD);
            }
        });

        try {
            MimeMessage mm = new MimeMessage(De_Sesion);
            mm.setFrom(new InternetAddress(Configuracion.DE_CORREO));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(A_Correo));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(C_Correo));
            mm.setSubject(A_Asunto);
            mm.setText(A_Mensaje);
            Transport.send(mm);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

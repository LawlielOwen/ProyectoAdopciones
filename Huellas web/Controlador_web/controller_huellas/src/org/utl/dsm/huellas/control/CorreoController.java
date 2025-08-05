package org.utl.dsm.huellas.control;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class CorreoController {
    public void enviarCorreo(String correoDestino, String asunto, String contenido) {
        try {
            String remitente = "dayronlawl@gmail.com";
            String contra = "wtcfgsplvzhvsneb"; 

            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", "587");

            Session session = Session.getInstance(p, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, contra);
                }
            });

            Message mensajeCorreo = new MimeMessage(session);
            mensajeCorreo.setFrom(new InternetAddress(remitente));
            mensajeCorreo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
            mensajeCorreo.setSubject(asunto);
            mensajeCorreo.setText(contenido);

            Transport.send(mensajeCorreo);

            System.out.println("Correo enviado con éxito.");

        } catch (MessagingException e) {
            System.out.println("❌ Error al enviar el correo:");
            e.printStackTrace();
        }
    }
}

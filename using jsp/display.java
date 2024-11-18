/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.io.*;
import java.util.Base64;
import java.util.Properties;
import javax.servlet.annotation.MultipartConfig;

/**
 *
 * @author Swetha
 */
@MultipartConfig
public class display extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        // Get the Base64 image string from the form
        String base64Image = request.getParameter("base64Image");

        if (base64Image != null && !base64Image.isEmpty()) {
            // Decode the Base64 string
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

            // Save the decoded image as a file (for email attachment)
            File imageFile = new File(getServletContext().getRealPath("/") + "uploaded_image.jpg");
            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                fos.write(decodedBytes);
            }

            // Send an email with the form data (name, email, message) and image as attachment
            sendEmailWithAttachment(name, email, message, imageFile);

            // Send a response back to the client
                response.setContentType("text/html");
                response.getWriter().println("<script type=\"text/javascript\">");
                response.getWriter().println("alert('email sent successfully.');");
                response.getWriter().println("document.getElementById('uploadForm').reset();");
               response.getWriter().println("window.location.href = window.location.href;");  // Reload the page
                response.getWriter().println("</script>");
        } else {
                response.setContentType("text/html");
                response.getWriter().println("<script type=\"text/javascript\">");
                response.getWriter().println("alert('No image uploaded.');");
               response.getWriter().println("document.getElementById('uploadForm').reset();"); // Reload the page
                response.getWriter().println("</script>");
        }

    }
    private void sendEmailWithAttachment(String name, String email, String messageContent, File imageFile) {
     
       

        // Set up the email properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a session with the provided username and password
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("tkvasanthi2709@gmail.com", "xrlk vexe flcz tkio");
            }
        });

        try {
            // Create a new email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tkvasanthi2709@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Image Upload and Form Data");

            // Create a multipart message
            MimeMultipart multipart = new MimeMultipart();

            // Text part of the email (including name, email, and message content)
            MimeBodyPart textPart = new MimeBodyPart();
            String emailBody = "Name: " + name + "\n" +
                               "Email: " + email + "\n" +
                               "Message: " + messageContent;
            textPart.setText(emailBody);

            // File attachment part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(imageFile);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(imageFile.getName());

            // Add both parts to the multipart
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            // Set the content of the message
            message.setContent(multipart);

            // Send the email
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

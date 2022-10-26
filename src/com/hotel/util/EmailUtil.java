package com.hotel.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import com.hotel.dao.MasterConfigDAO;
import com.hotel.dto.PlacedOrderItemDTO;

public class EmailUtil {
	
	private static final String SRV_PROVIDER= "SRV_PROVIDER";
	
	public void sendEmail(LocalDate date, Map<String, Integer> orderMap, Map<String, String> fileMap) {
		MasterConfigDAO configDao= new MasterConfigDAO();
		
		String to = configDao.selectMasterConfigValue(SRV_PROVIDER);
		String from = "hotelrsc8@gmail.com";
        String host = "smtp.gmail.com";
        String pwd = "cjxgmxfqikgmfscc";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.mime.encodefilename", "true");
        properties.put("mail.mime.encodeparameters", "false");
        properties.put("mail.mime.splitlongparameters", "false");

        // Get the Session objectn and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pwd);
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            List<String> toMails = Arrays.asList(to.split(",", -1));
            for (String toMail : toMails) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
            }
            message.setSubject("Order for "+date);
            message.setContent(createOrderTable(orderMap),"text/html");

            // add excel attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            String file = fileMap.get("filePath").concat(fileMap.get("fileName"));
            try {
                attachmentPart.attachFile(new File(file));
                attachmentPart.setFileName(MimeUtility.encodeText(fileMap.get("fileName"), "UTF-8", null));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

	}
	
	private String createOrderTable(Map<String, Integer> orderMap) {
		StringBuilder sb = new StringBuilder();
        sb.append("<html><body><table><thead><tr><th>Item</th><th>Total Quantity</th></tr></thead><tbody>");
        
        for (Map.Entry<String, Integer> pairs : orderMap.entrySet()) {
        	sb.append("<tr> <td>");
        	sb.append(pairs.getKey());
        	sb.append("</td>");
        	sb.append("<td>");
        	sb.append(pairs.getValue());
        	sb.append("</td> </tr>");
        }
        sb.append("</tbody></table></body></html>");
        
        return sb.toString();
	}

}

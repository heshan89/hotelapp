package com.hotel.util;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hotel.dao.MasterConfigDAO;

public class EmailUtil {
	
	private static final String SRV_PROVIDER= "SRV_PROVIDER";
	
	public void sendEmail(LocalDate date, Map<String, Integer> orderMap) {
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
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Order for "+date);
            message.setContent(createOrderTable(orderMap),"text/html");
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

package our_ruzaevka;

import static com.sun.prism.impl.PrismSettings.debug;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Hello world!
 *
 */
public class Send
{
private String text;
private String subject;
    public Send() {
    }

    public Send(String text,String subject) {
        this.text = text;
        this.subject = subject;
    }
    
    
    public int sendfile(String to ,String from,String host,String filename,boolean attach,boolean debug){
    Authenticator authenticator = new Authenticator(); 
		
	// create some properties and get the default Session
	Properties props = System.getProperties();
	//props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        //props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable","true"); 
        //props.put("mail.smtp.auth.mechanisms","NTLM");
        props.put("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
        	
	//Session session = Session.getInstance(props, null);
        
        // Get the Session object.
      Session session = Session.getInstance(props,
         authenticator);

	session.setDebug(debug);
	
	try {
	    // create a message
	    MimeMessage msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress(from));
	    InternetAddress[] address = {new InternetAddress(to)};
	    msg.setRecipients(Message.RecipientType.TO, address);
	    msg.setSubject(subject);

	    // create and fill the first message part
	    MimeBodyPart mbp1 = new MimeBodyPart();
	    mbp1.setText(text);

	    // create the second message part
	    MimeBodyPart mbp2 = new MimeBodyPart();

	    

	    // create the Multipart and add its parts to it
	    Multipart mp = new MimeMultipart();
	    mp.addBodyPart(mbp1);
            if(attach){
	    mp.addBodyPart(mbp2);
            // attach the file to the message
	    mbp2.attachFile(filename);

	    /*
	     * Use the following approach instead of the above line if
	     * you want to control the MIME type of the attached file.
	     * Normally you should never need to do this.
	     *
	    FileDataSource fds = new FileDataSource(filename) {
		public String getContentType() {
		    return "application/octet-stream";
		}
	    };
	    mbp2.setDataHandler(new DataHandler(fds));
	    mbp2.setFileName(fds.getName());
	     */
            }

	    // add the Multipart to the message
	    msg.setContent(mp);

	    // set the Date: header
	    msg.setSentDate(new Date());

	    /*
	     * If you want to control the Content-Transfer-Encoding
	     * of the attached file, do the following.  Normally you
	     * should never need to do this.
	     *
	    msg.saveChanges();
	    mbp2.setHeader("Content-Transfer-Encoding", "base64");
	     */

	    // send the message
	    Transport.send(msg);
            
	} catch (MessagingException mex) {
	    mex.printStackTrace();
	    Exception ex = null;
	    if ((ex = mex.getNextException()) != null) {
		ex.printStackTrace();
	    }
	}catch(IOException ioex) {
            ioex.printStackTrace();
        }
    
        
        return 0;
    }
    static class Authenticator extends javax.mail.Authenticator {
private PasswordAuthentication authentication;

public Authenticator() {
String username ="true.luch@gmail.com";
String password = "muhamedjanovgmail";
authentication = new PasswordAuthentication(username, password);
}
protected PasswordAuthentication getPasswordAuthentication() {
return authentication;
}
}
    public static void main(String[] args) {
        Send s=new Send("kosta","kosta моста n черепах");
        s.sendfile("muh_kosta@mail.ru","true.luch@mail.ru" ,"smtp.gmail.com", null,false,true);
                
    }
}

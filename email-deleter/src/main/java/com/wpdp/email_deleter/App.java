package com.wpdp.email_deleter;

import java.io.Console;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class App 
{
	public static void main(String[] args) {
		Console console = System.console();
		String mailStoreType = "pop3";
	    String username = console.readLine("Enter username:");
	    String password = console.readLine("Enter password:");
	    
	    delete(mailStoreType, username, password);
	}
    
    static void delete(String storeType, String user, String password) {
    	try {
    		// get the session object
    	    Properties properties = new Properties();
    	    properties.put("mail.store.protocol", "pop3");
    	    properties.put("mail.pop3s.host", "pop.gmail.com");
    	    properties.put("mail.pop3s.port", "995");
    	    properties.put("mail.pop3.starttls.enable", "true");
    	    Session emailSession = Session.getDefaultInstance(properties);
    	    
    	    // create the POP3 store object and connect with the pop server
    	    Store store = emailSession.getStore("pop3s");
    	    store.connect("pop.gmail.com", user, password);

    	    // create the folder object and open it
    	    Folder inbox = store.getFolder("INBOX");
	    Folder[] f = store.getDefaultFolder().list("*");
    	    inbox.open(Folder.READ_WRITE);
    	    int inboxMessageCount = inbox.getMessageCount();
    	    // Message[] messages = inbox.getMessages();
    	    // for (int i = 0; i < inboxMassegeCount; i++) {
    	    	// messages[i].setFlag(Flags.Flag.DELETED, true);
    	    // }
	    for(Folder fd : f) {
		System.out.println("Folder name is: " + fd.getName());
	    }
    	    System.out.println("Messages in inbox are: " + inboxMessageCount);
    	    // inbox.expunge();  	         
    	    // expunges the folder to remove messages which are marked deleted
    	    inbox.close(true);
    	    store.close();
    	    } catch (MessagingException e) {
    	    	e.printStackTrace();
    	    }
    }
}

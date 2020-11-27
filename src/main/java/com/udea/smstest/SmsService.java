
package com.udea.smstest;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class SmsService {
     private final String ACCOUNT_SID ="";

	    private final String AUTH_TOKEN = "";

	    private final String FROM_NUMBER = "+13";
    
    public void send(SmsPojo sms){
    Twilio.init(ACCOUNT_SID,AUTH_TOKEN );
    Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), 
       sms.getMessage()).create();
    System.out.println("Este es mi id: " + message.getSid());
    }
    
    public void receive(MultiValueMap<String,String> smscallback){}
    
}

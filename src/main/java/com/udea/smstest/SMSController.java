
package com.udea.smstest;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
        
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class SMSController {
    
  @Autowired
SmsService service;  
    
  @Autowired
  private SimpMessagingTemplate webSocket;
  
  private final String TOPIC_DESTINATION ="/lesson/sms";
  
  @RequestMapping(value="/sms", method = RequestMethod.POST,
   consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
  public void smsSubmit(@RequestBody SmsPojo sms){
    try{
       service.send(sms);
    }catch(Exception e){
   webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": Error enviando SMS: " + e.getMessage());
      throw e;
    }
      
  webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": SMS ha sido enviado! " + sms.getTo());
      
  }
    private String getTimeStamp() {
      return DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss").format(LocalDateTime.now());
    }
    
     @RequestMapping(value="/smscallback", method = RequestMethod.POST,
   consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
   public void smsCallback(@RequestBody MultiValueMap<String, String> map){
     service.receive(map);
     webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": Twilio ha realizado un Callback request-  Esto fue lo que llego:  "+ map.toString());
   
   } 
    
  
}

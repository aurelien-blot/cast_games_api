package com.castruche.cast_games_api.service.util;

import com.castruche.cast_games_api.dto.util.MailObjectDto;
import com.castruche.cast_games_api.dto.UserDto;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class MailService{

    private SettingService settingService;

    public MailService(SettingService settingService) {
        this.settingService = settingService;
    }

    public void sendMailForMailVerification(UserDto user, String verificationToken){
        MailObjectDto mailObjectDto = new MailObjectDto();
        mailObjectDto.setSenderEmail(settingService.getNoReplyEmail());
        mailObjectDto.setSenderName(settingService.getNoReplyName());
        mailObjectDto.setReceiverEmail(user.getEmail());
        mailObjectDto.setReceiverName(user.getFirstName());
        mailObjectDto.setTemplateId(Integer.parseInt(settingService.getMailVerificationId()));
        JSONObject variables = new JSONObject();
        String frontBaseUrl = settingService.getFrontBaseUrl();
        if(null!=frontBaseUrl && !frontBaseUrl.isEmpty()){
            variables.put("confirmation_link", frontBaseUrl+"/confirm-mail?token=" + verificationToken);
        }
        variables.put("username", user.getUsername());
        mailObjectDto.setVariables(variables);
        this.sendMail(mailObjectDto);
    }

    private void sendMail(MailObjectDto mailObjectDto){

        try {
            String apiKey = settingService.getApiKey();
            String apiSecret = settingService.getApiSecret();
            if(null==apiKey || apiKey.isEmpty() || null==apiSecret || apiSecret.isEmpty()){
                throw new MailjetException("Mailjet API key or secret is not set");
            }
            MailjetClient client;
            MailjetRequest request;
            MailjetResponse response;
            client = new MailjetClient(
                    apiKey,
                    apiSecret,
                    new ClientOptions("v3.1"));
            request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                                .put("Email", mailObjectDto.getSenderEmail())
                                            .put("Name", mailObjectDto.getSenderName()))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", mailObjectDto.getReceiverEmail())
                                                    .put("Name", mailObjectDto.getReceiverName())))
                                    .put(Emailv31.Message.TEMPLATEID, mailObjectDto.getTemplateId())
                                    .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                                    .put(Emailv31.Message.VARIABLES, mailObjectDto.getVariables())
                            ));
            response= client.post(request);
            if(response.getStatus() != 200){
                throw new MailjetException(response.getData().toString());
            }
        }
        catch (MailjetException | MailjetSocketTimeoutException e) {
            e.printStackTrace();
        }
    }
}

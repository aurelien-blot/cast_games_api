package com.castruche.cast_games_api.service;

import com.castruche.cast_games_api.configuration.JwtUtil;
import com.castruche.cast_games_api.dao.UserRepository;
import com.castruche.cast_games_api.dto.UserDto;
import com.castruche.cast_games_api.dto.login.LoginResponseDto;
import com.castruche.cast_games_api.dto.login.LoginUserDto;
import com.castruche.cast_games_api.dto.standardResponse.BooleanResponseDto;
import com.castruche.cast_games_api.entity.User;
import com.castruche.cast_games_api.formatter.UserFormatter;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.BooleanUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MailService{

    @Value("${mailjet.api.key}")
    private String apiKey;
    @Value("${mailjet.api.secret}")
    private String apiSecret;
    @Value("${mail.noreply.email}")
    private String noReplyEmail;
    @Value("${mail.noreply.name}")
    private String noReplyName;
    @Value("${front.baseUrl}")
    private String frontBaseUrl;
    @Value("${mailjet.verify.mail.id}")
    private String mailVerificationId;

    public void sendMailForMailVerification(UserDto user, String verificationToken){
        String href = frontBaseUrl+"/verify/mail?token=" + verificationToken;
        Integer templateId = Integer.parseInt(mailVerificationId);
        this.sendNoReplyMail(user.getEmail(), user.getFirstName(), templateId);
    }

    private void sendNoReplyMail(String receiverEmail, String receiverName, Integer templateId){
        this.sendMail(noReplyEmail, noReplyName, receiverEmail, receiverName, templateId);
    }
    private void sendMail(String senderEmail, String senderName, String receiverEmail, String receiverName, Integer templateId){
        try {
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
                                            .put("Email", senderEmail)
                                            .put("Name", senderName))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", receiverEmail)
                                                    .put("Name", receiverName)))
                                    .put(Emailv31.Message.TEMPLATEID, templateId)
                                    .put(Emailv31.Message.TEMPLATELANGUAGE, true)
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

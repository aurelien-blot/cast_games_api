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

    public void sendMailForMailVerification(UserDto user){
        this.sendMail();
    }

    private void sendMail(){
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
                                            .put("Email", "aurelien.blot@gmail.com")
                                            .put("Name", "Aurélien"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", "aurelien.blot@gmail.com")
                                                    .put("Name", "Aurélien")))
                                    .put(Emailv31.Message.SUBJECT, "Greetings from Mailjet.")
                                    .put(Emailv31.Message.TEXTPART, "My first Mailjet email")
                                    .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger 1, welcome to <a href='https://www.mailjet.com/'>Mailjet</a>!</h3><br />May the delivery force be with you!")
                                    .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
            response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());
        }
        catch (MailjetException | MailjetSocketTimeoutException e) {
            e.printStackTrace();
        }
    }
}

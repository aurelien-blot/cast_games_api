package com.castruche.cast_games_api.service.util;

import com.castruche.cast_games_api.dto.util.MailObjectDto;
import com.castruche.cast_games_api.dto.user.UserDto;
import com.castruche.cast_games_api.entity.util.Setting;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class MailService{

    private static final Logger logger = LogManager.getLogger(MailService.class);
    private final String VAR_APP_LINK = "app_link";
    private static final String VAR_USERNAME = "username";
    private static final String VAR_CONFIRMATION_LINK = "confirmation_link";
    private static final String VAR_RESET_PASSWORD_LINK = "reset_password_link";
    private static final String DAYS_SINCE_ACCOUNT_CREATION = "days_since_account_creation";
    private static final String DAYS_BEFORE_DELETION = "days_before_deletion";
    private static final String CONTACT_REQUESTER_USERNAME = "contact_requester_username";
    private final SettingService settingService;

    public MailService(SettingService settingService) {
        this.settingService = settingService;
    }

    public void sendMailForMailVerification(UserDto user, String verificationToken){
        Integer delayMax = settingService.getDeletingUnverifiedAccountDelay();
        MailObjectDto mailObjectDto = initNoReplyMailObject(user);
        mailObjectDto.setTemplateId(Integer.parseInt(settingService.getMailVerificationId()));
        mailObjectDto.getVariables().put(VAR_CONFIRMATION_LINK, mailObjectDto.getVariables().get(VAR_APP_LINK)+"/confirm-mail?token=" + verificationToken);
        mailObjectDto.getVariables().put(DAYS_BEFORE_DELETION, delayMax);
        this.sendMail(mailObjectDto);
    }

    public void sendMailForVerificationReminder(UserDto user, String verificationToken, long daysBetween){
        Integer delayMax = settingService.getDeletingUnverifiedAccountDelay();
        MailObjectDto mailObjectDto = initNoReplyMailObject(user);
        mailObjectDto.setTemplateId(Integer.parseInt(settingService.getMailVerificationReminderId()));
        mailObjectDto.getVariables().put(VAR_CONFIRMATION_LINK, mailObjectDto.getVariables().get(VAR_APP_LINK)+"/confirm-mail?token=" + verificationToken);
        mailObjectDto.getVariables().put(DAYS_SINCE_ACCOUNT_CREATION, daysBetween);
        mailObjectDto.getVariables().put(DAYS_BEFORE_DELETION, delayMax);
        this.sendMail(mailObjectDto);
    }

    public void sendMailForRegistration(UserDto user){
        MailObjectDto mailObjectDto = initNoReplyMailObject(user);
        mailObjectDto.setTemplateId(Integer.parseInt(settingService.getMailRegistrationId()));
        this.sendMail(mailObjectDto);
    }

    public void sendMailForPasswordReset(UserDto user, String resetPasswordToken){
        MailObjectDto mailObjectDto = initNoReplyMailObject(user);
        mailObjectDto.setTemplateId(Integer.parseInt(settingService.getMailResetPasswordId()));
        mailObjectDto.getVariables().put(VAR_RESET_PASSWORD_LINK, mailObjectDto.getVariables().get(VAR_APP_LINK)+"/reset-password?token=" + resetPasswordToken);

        this.sendMail(mailObjectDto);
    }

    public void sendMailForAccountDeletion(UserDto user){
        MailObjectDto mailObjectDto = initNoReplyMailObject(user);
        mailObjectDto.setTemplateId(Integer.parseInt(settingService.getMailAccountDeletionId()));
        this.sendMail(mailObjectDto);
    }

    public void sendContactRequestMail(UserDto user, String requesterUsername){
        MailObjectDto mailObjectDto = initNoReplyMailObject(user);
        mailObjectDto.setTemplateId(Integer.parseInt(settingService.getMailRequestContactId()));
        mailObjectDto.getVariables().put(CONTACT_REQUESTER_USERNAME, requesterUsername);
        this.sendMail(mailObjectDto);
    }
    public void sendContactInvitationMail(UserDto user, String email){
        MailObjectDto mailObjectDto = initNoReplyMailByMailObject(user, email);
        mailObjectDto.setTemplateId(Integer.parseInt(settingService.getMailRequestContactByMailId()));
        this.sendMail(mailObjectDto);
    }

    private MailObjectDto initNoReplyMailObject(UserDto user){
        MailObjectDto mailObjectDto = new MailObjectDto();
        mailObjectDto.setSenderEmail(settingService.getNoReplyEmail());
        mailObjectDto.setSenderName(settingService.getNoReplyName());
        mailObjectDto.setReceiverEmail(user.getEmail());
        mailObjectDto.setReceiverName(user.getFirstName());
        JSONObject variables = new JSONObject();
        mailObjectDto.setVariables(variables);
        String frontBaseUrl = settingService.getFrontBaseUrl();
        mailObjectDto.getVariables().put(VAR_APP_LINK, frontBaseUrl);
        mailObjectDto.getVariables().put(VAR_USERNAME, user.getUsername());
        return mailObjectDto;
    }

    private MailObjectDto initNoReplyMailByMailObject(UserDto user, String email){
        MailObjectDto mailObjectDto = new MailObjectDto();
        mailObjectDto.setSenderEmail(settingService.getNoReplyEmail());
        mailObjectDto.setSenderName(settingService.getNoReplyName());
        mailObjectDto.setReceiverEmail(email);
        JSONObject variables = new JSONObject();
        mailObjectDto.setVariables(variables);
        String frontBaseUrl = settingService.getFrontBaseUrl();
        mailObjectDto.getVariables().put(VAR_APP_LINK, frontBaseUrl);
        mailObjectDto.getVariables().put(VAR_USERNAME, user.getUsername());
        return mailObjectDto;
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
            logger.error("Mailjet error: " + e.getMessage());
        }
    }
}

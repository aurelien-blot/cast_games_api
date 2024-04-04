package com.castruche.cast_games_api.service.util;

import com.castruche.cast_games_api.dao.util.SettingRepository;
import com.castruche.cast_games_api.entity.util.Setting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    private static final Logger logger = LogManager.getLogger(SettingService.class);
    private SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public Setting getSettingByShortName(String shortName) {
        return settingRepository.findByShortName(shortName);
    }

    public String getSettingValueByShortName(String shortName) {
        Setting setting = settingRepository.findByShortName(shortName);
        if(setting != null) {
            return setting.getValue();
        }
        return null;
    }

    public String getApiKey() {
        return getSettingValueByShortName("mailjet_api_key");
    }

    public String getApiSecret() {
        return getSettingValueByShortName("mailjet_api_secret");
    }

    public String getNoReplyEmail() {
        return getSettingValueByShortName("mail_noreply_email");
    }

    public String getNoReplyName() {
        return getSettingValueByShortName("mail_noreply_name");
    }

    public String getFrontBaseUrl() {
        return getSettingValueByShortName("front_base_url");
    }

    public String getMailVerificationId() {
        return getSettingValueByShortName("mailjet_verify_mail_id");
    }
    public String getMailVerificationReminderId() {
        return getSettingValueByShortName("mailjet_verify_mail_reminder_id");
    }
    public String getMailRegistrationId() {
        return getSettingValueByShortName("mailjet_registration_id");
    }
    public String getMailAccountDeletionId() {
        return getSettingValueByShortName("mailjet_account_deletion_id");
    }
    public String getMailResetPasswordId() {
        return getSettingValueByShortName("mailjet_reset_password_id");
    }
    public Integer getTentativesBeforeBlocking() {
        String result =  getSettingValueByShortName("tentatives_before_blocking");
        try {
            return Integer.parseInt(result);
        } catch (NumberFormatException e) {
            logger.error("Error while parsing tentatives_before_blocking setting", e);
        }
        return null;
    }

}

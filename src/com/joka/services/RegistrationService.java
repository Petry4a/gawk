package com.joka.services;

import com.joka.domain.Registration;
import com.joka.persistence.PersistenceService;
import com.joka.utils.EmailValidator;

import java.util.Date;

/**
 * @author jonas
 */
public class RegistrationService {

    private static final String ERROR_EMAIL_VALIDATION_FAILED = "E-mail validation failed.";
    private static final String ERROR_EMAIL_ALL_READY_REGISTERED = "E-mail is already registered.";

    private static RegistrationService instance;

    private RegistrationService() {}

    public static RegistrationService getInstance() {

        if(instance == null) {
            instance = new RegistrationService();
        }

        return instance;
    }

    public void registerEmail(String email) {

        if(email == null) {
            throw new IllegalStateException(ERROR_EMAIL_VALIDATION_FAILED);
        }

        if(!(new EmailValidator().validate(email))) {
            throw new IllegalStateException(ERROR_EMAIL_VALIDATION_FAILED);
        }

        Registration registration = PersistenceService.getInstance().getRegistrationByEmail(email);
        if(registration != null) {
            throw new IllegalStateException(ERROR_EMAIL_ALL_READY_REGISTERED);
        }

        Registration newRegistration = new Registration();
        newRegistration.setEmail(email);
        newRegistration.setRegistrationDate(new Date());

        PersistenceService.getInstance().saveRegistration(newRegistration);

    }
}

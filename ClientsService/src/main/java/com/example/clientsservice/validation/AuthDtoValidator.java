package com.example.clientsservice.validation;

import com.example.clientsservice.common.validation.BaseValidator;
import com.example.clientsservice.dto.request.AuthDto;
import com.example.clientsservice.exception.TemplateException;
import com.example.clientsservice.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthDtoValidator implements BaseValidator<AuthDto> {

    private final UserRepository userRepository;

    @Autowired
    public AuthDtoValidator(
            UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validate(AuthDto obj) {
        validateUniqueCode(obj);
    }

    private void validateUniqueCode(AuthDto authDto) {
        var user = userRepository
                .findByLogin(authDto.getLogin())
                .orElse(null);

        if(user != null) {
            var checkPassword = DigestUtils.md5Hex(authDto.getPassword()).equals(user.getPassword());

            if(user.isActive() == false)
                throw new TemplateException("You are blocked");

            if(checkPassword == false) {
                var countAttempts = user.getAttemptsBlocking() +1;
                user.setAttemptsBlocking(countAttempts);
                userRepository.save(user);
                if(user.getAttemptsBlocking() < 3) {
                    int count = 3 - user.getAttemptsBlocking();
                    throw new TemplateException("Wrong password, attempts left " + count +"");
                }
                else{
                    user.setActive(false);
                    userRepository.save(user);
                    throw new TemplateException("You are blocked");
                }
            }
        }
        else
            throw new TemplateException("Not found login");

        user.setAttemptsBlocking(0);
        userRepository.save(user);
    }
}
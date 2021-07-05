package com.example.clientsservice.validation;

import com.example.clientsservice.common.validation.BaseValidator;
import com.example.clientsservice.domain.Client;
import com.example.clientsservice.dto.request.ClientDto;
import com.example.clientsservice.exception.FieldNotUniqueException;
import com.example.clientsservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
    public class ClientDtoValidator implements BaseValidator<ClientDto> {

    private static final String FIELD_INN = "inn";
    private static final String FIELD_EMAIL = "email";

        private final ClientRepository clientRepository;

        @Autowired
        public ClientDtoValidator(
                ClientRepository clientRepository) {
            this.clientRepository = clientRepository;
        }

        @Override
        public void validate(ClientDto obj) {
            validateUniqueCode(obj);
        }

        private void validateUniqueCode(ClientDto clientDto) {

           var checkInnClient = this.clientRepository
                   .findAll()
                   .stream().anyMatch(x -> !x.getId().equals(clientDto.getId()) && x.getInn().equals(clientDto.getInn()));

            if (checkInnClient) {
                throw new FieldNotUniqueException(FIELD_INN);
            }

            var checkEmailClient = this.clientRepository
                    .findAll()
                    .stream().anyMatch(x -> !x.getId().equals(clientDto.getId()) && x.getEmail().equals(clientDto.getEmail()));

            if (checkEmailClient) {
                throw new FieldNotUniqueException(FIELD_EMAIL);
            }

        }
    }

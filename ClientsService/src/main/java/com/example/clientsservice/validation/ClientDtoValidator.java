package com.example.clientsservice.validation;

import com.example.clientsservice.common.validation.BaseValidator;
import com.example.clientsservice.dto.request.ClientDto;
import com.example.clientsservice.exception.TemplateException;
import com.example.clientsservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
    public class ClientDtoValidator implements BaseValidator<ClientDto> {

    private static final String FIELD_INN = "Данный ИНН уже существует в базе";
    private static final String FIELD_CON_NUMBER = "Некорректное контрольное число ИНН";
    private static final String FIELD_EMAIL = "Данный email уже существует в базе";
    private static final String FIELD_KPP = "Данный КПП уже существует в базе";

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

            var checkControlNumberInn = checkInn(clientDto.getInn());

            if(!checkControlNumberInn){
                throw new TemplateException(FIELD_CON_NUMBER);
            }

           var checkInnClient = this.clientRepository
                   .findAll()
                   .stream().anyMatch(x -> !x.getId().equals(clientDto.getId()) && x.getInn().equals(clientDto.getInn()));

            if (checkInnClient) {
                throw new TemplateException(FIELD_INN);
            }

            var checkEmailClient = this.clientRepository
                    .findAll()
                    .stream().anyMatch(x -> !x.getId().equals(clientDto.getId()) && x.getEmail().equals(clientDto.getEmail()));

            if (checkEmailClient) {
                throw new TemplateException(FIELD_EMAIL);
            }

            var checkKppClient = this.clientRepository
                    .findAll()
                    .stream().anyMatch(x -> !x.getId().equals(clientDto.getId()) && x.getKpp().equals(clientDto.getKpp()));

            if (checkKppClient) {
                throw new TemplateException(FIELD_KPP);
            }

        }
    public static final Integer[] MULT_N1 = {7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
    public static final Integer[] MULT_N2 = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
    public static final Integer[] MULT_N =  {2, 4, 10, 3, 5, 9, 4, 6, 8};

    public static boolean checkInn(String innStr) {
        Boolean valid;
        Integer[] inn = stringToIntArray(innStr);

        Integer innSize = inn.length;

        switch (innSize) {
            case 12:
                Integer N1 = getChecksum(inn,MULT_N1);
                Integer N2 = getChecksum(inn,MULT_N2);

                valid = (inn[inn.length-1].equals(N2) && inn[inn.length-2].equals(N1));
                break;
            case 10:
                Integer N = getChecksum(inn,MULT_N);
                valid = (inn[inn.length-1].equals(N));
                break;
            default:
                valid = false;
                break;
        }
        return valid;
    }

    public static Integer[] stringToIntArray(String src) {
        char[] chars = src.toCharArray();
        ArrayList<Integer> digits = new ArrayList<Integer>();
        for (char aChar : chars) {
            digits.add(Character.getNumericValue(aChar));
        }
        return digits.toArray(new Integer[digits.size()]);
    }

    public static Integer getChecksum(Integer[] digits, Integer[] multipliers) {
        int checksum = 0;
        for (int i=0; i<multipliers.length; i++) {
            checksum+=(digits[i]*multipliers[i]);
        }
        return (checksum % 11) % 10;
    }
    }

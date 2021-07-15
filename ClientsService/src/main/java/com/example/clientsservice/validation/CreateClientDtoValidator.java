package com.example.clientsservice.validation;

import com.example.clientsservice.common.validation.BaseValidator;
import com.example.clientsservice.dto.request.CreateClientDto;
import com.example.clientsservice.exception.TemplateException;
import com.example.clientsservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CreateClientDtoValidator implements BaseValidator<CreateClientDto> {

    private static final String FIELD_INN = "Данный ИНН уже существует в базе";
    private static final String FIELD_CON_NUMBER = "Некорректное контрольное число ИНН";
    private static final String FIELD_EMAIL = "Данный email уже существует в базе";
    private static final String FIELD_KPP = "Данный КПП уже существует в базе";
    private static final String FIELD_LOGIN = "Данный Логин уже существует в базе";

    private final ClientRepository clientRepository;

    @Autowired
    public CreateClientDtoValidator(
            ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void validate(CreateClientDto obj) {
        validateUniqueCode(obj);
    }

    private void validateUniqueCode(CreateClientDto CreateClientDto) {

        var checkLogin = this.clientRepository
                .findAll()
                .stream()
                .anyMatch(x -> x.getUser().getLogin().equals(CreateClientDto.getLogin()));

        if(checkLogin){
            throw new TemplateException((FIELD_LOGIN));
        }

        var checkControlNumberInn = checkInn(CreateClientDto.getInn());

        if(!checkControlNumberInn){
            throw new TemplateException(FIELD_CON_NUMBER);
        }

        var checkInnClient = this.clientRepository
                .findAll()
                .stream().anyMatch(x -> x.getInn().equals(CreateClientDto.getInn()));

        if (checkInnClient) {
            throw new TemplateException(FIELD_INN);
        }

        var checkEmailClient = this.clientRepository
                .findAll()
                .stream().anyMatch(x -> x.getEmail().equals(CreateClientDto.getEmail()));

        if (checkEmailClient) {
            throw new TemplateException(FIELD_EMAIL);
        }

        var checkKppClient = this.clientRepository
                .findAll()
                .stream().anyMatch(x -> x.getKpp().equals(CreateClientDto.getKpp()));

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

import React, { useState } from 'react';
import './Registration.scss';
import useFormState from '../../../../common/customHooks/useFormState';
import { register } from '../../../../api/auth';
import { AxiosError, AxiosResponse } from 'axios';
import { RegistrationResponse } from '../../../../api/models/response/auth-response.model';
import { CSSTransition } from 'react-transition-group';
import ErrorModal from '../../../ui/ErrorModal/ErrorModal';
import { useHistory } from 'react-router';
import SuccessModal from '../../../ui/SuccessModal/SuccessModal';

const Registration = () => {

    const history = useHistory()

    const account = {
        email: useFormState(''),
        fio: useFormState(''),
        fullAddress: useFormState(''),
        fullNameOrg: useFormState(''),
        inn: useFormState(''),
        kpp: useFormState(''),
        login: useFormState(''),
        password: useFormState(''),
        shortNameOrg: useFormState('')
    }

    const [success, setSuccess] = useState("");
    const [error, setError] = useState("");

    const registerAccount = ({email, fio, fullAddress, fullNameOrg, inn, kpp, login, password, shortNameOrg}:any, e:any) => {
        e.preventDefault();
        register({
            email: email.value,
            fio: fio.value,
            fullAddress: fullAddress.value,
            fullNameOrg: fullNameOrg.value,
            inn: inn.value,
            kpp: kpp.value,
            login: login.value,
            password: password.value,
            shortNameOrg: shortNameOrg.value
        })
        .then((response:AxiosResponse<RegistrationResponse>) => {
            setSuccess('Вы успешно зарегистрированы')
            setTimeout(() => {
                setSuccess("")
                history.push('/authorization')
            }
            , 3000);
        })
        .catch((err:AxiosError) => {
            setError(err.response?.data.message)
            setTimeout(() => setError(""), 5000);
        })
    }

    return (
        <form noValidate className="registration-form" onSubmit={(e) => registerAccount(account, e)}>
            <CSSTransition in={error.length !== 0} timeout={300} unmountOnExit classNames="show-hide-animation">
                <ErrorModal>
                    { error }
                </ErrorModal>
            </CSSTransition>
            <CSSTransition in={success.length !== 0} timeout={300} unmountOnExit classNames="show-hide-animation">
                <SuccessModal>
                    { success }
                </SuccessModal>
            </CSSTransition>
            <h1>Регистрация</h1>
            <fieldset>
                <h3>Данные об организации</h3>
                <label>
                    ИНН
                    <input type="number" name="inn" {...account.inn} placeholder="Введите ИНН организации" className="main-input" />
                </label>
                <label>
                    КПП
                    <input type="text" name="kpp" {...account.kpp} placeholder="Введите КПП" className="main-input" />
                </label>
                <label>
                    Полное { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } наименование { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } организации
                    <input type="text" name="full" {...account.fullNameOrg} placeholder="Введите полное наименование" className="main-input" />
                </label>
                <label>
                    Краткое { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } наименование { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } организации
                    <input type="text" name="short" {...account.shortNameOrg} placeholder="Введите краткое наименование" className="main-input" />
                </label>
                <label>
                    Адрес организации
                    <input type="text" name="address" {...account.fullAddress} placeholder="Введите адрес организации" className="main-input" />
                </label>
            </fieldset>
            <fieldset>
                <h3>Данные пользователя</h3>
                <label>
                    ФИО
                    <input type="text" name="name" {...account.fio} placeholder="Введите ФИО" className="main-input" />
                </label>
                <label>
                    Логин
                    <input type="text" name="login" {...account.login} placeholder="Введите логин" className="main-input" />
                </label>
                <label>
                    Пароль
                    <input type="password" name="password" {...account.password} placeholder="Введите пароль" className="main-input" />
                </label>
                <label>
                    E-mail
                    <input type="email" name="email" {...account.email} placeholder="Введите адрес эл. почты" className="main-input" />
                </label>
            </fieldset>
            <div className="common-controls">
                <button className="common-btn transparent-btn" onClick={() => history.goBack()}>Назад</button>
                <button type="submit" className="common-btn" onClick={(event) => registerAccount(account, event)}>Зарегистрироваться</button>
            </div>
        </form>
    );
}

export default Registration;
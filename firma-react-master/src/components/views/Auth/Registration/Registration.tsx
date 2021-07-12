import React, { useState } from 'react';
import './Registration.scss';
import useFormState from '../../../../common/customHooks/useFormState';
import { register } from '../../../../api/auth';
import { AxiosResponse } from 'axios';
import { RegistrationResponse } from '../../../../api/models/response/auth-response.model';
import { CSSTransition } from 'react-transition-group';
import ErrorModal from '../../../ui/ErrorModal/ErrorModal';
import { useHistory } from 'react-router';

const Registration = () => {

    const account = {
        firstName: useFormState(''),
        secondName: useFormState(''),
        thirdName: useFormState(''),
        email: useFormState(''),
        password: useFormState(''),
        address: useFormState(''),
        passport: useFormState('')
    }

    const [error, setError] = useState(false);

    const history = useHistory()

    const registerAccount = ({firstName, secondName, thirdName, email, password, address, passport}:any, e:any) => {
        e.preventDefault();
        register({
            firstName: firstName.value,
            secondName: secondName.value,
            thirdName: thirdName.value,
            email: email.value,
            password: password.value,
            address: address.value,
            passport: passport.value
        })
        .then((response:AxiosResponse<RegistrationResponse>) => {
            alert('Success')
        })
        .catch(err => {
            setError(true)
            setTimeout(() => setError(false), 3500);
        })
    }

    return (
        <form noValidate className="auth-form registration-form" onSubmit={(e) => registerAccount(account, e)}>
            <CSSTransition in={error} timeout={300} unmountOnExit classNames="show-hide-animation">
                <div className="error-modal-container"><ErrorModal /></div>
            </CSSTransition>
            <h1>Регистрация</h1>
            <fieldset>
                <h3>Данные об организации</h3>
                <label>
                    ИНН
                    <input type="number" name="name" {...account.firstName} placeholder="Введите ИНН организации" className="main-input" />
                </label>
                <label>
                    КПП
                    <input type="text" name="login" placeholder="Введите КПП" className="main-input" />
                </label>
                <label>
                    Полное { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } наименование { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } организации
                    <input type="password" name="password" {...account.password} placeholder="Введите полное наименование" className="main-input" />
                </label>
                <label>
                    Краткое { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } наименование { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } организации
                    <input type="email" name="email" {...account.email} placeholder="Введите краткое наименование" className="main-input" />
                </label>
                <label>
                    Адрес организации
                    <input type="text" name="address" {...account.address} placeholder="Введите адрес организации" className="main-input" />
                </label>
            </fieldset>
            <fieldset>
                <h3>Данные пользователя</h3>
                <label>
                    ФИО
                    <input type="text" name="name" {...account.firstName} placeholder="Введите КПП" className="main-input" />
                </label>
                <label>
                    Логин
                    <input type="text" name="login" placeholder="Введите полное наименование" className="main-input" />
                </label>
                <label>
                    Пароль
                    <input type="password" name="password" {...account.password} placeholder="Введите краткое наименование" className="main-input" />
                </label>
                <label>
                    E-mail
                    <input type="email" name="email" {...account.email} placeholder="Введите краткое наименование" className="main-input" />
                </label>
            </fieldset>
            <div className="common-controls">
                <button className="common-btn transparent-btn" onClick={() => history.goBack()}>Назад</button>
                <button type="submit" className="common-btn">Зарегистрироваться</button>
            </div>
        </form>
    );
}

export default Registration;
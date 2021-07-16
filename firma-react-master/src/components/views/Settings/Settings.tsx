import React, { useState } from 'react';
import './Settings.scss';
import useFormState from '../../../common/customHooks/useFormState';
import { AxiosResponse } from 'axios';
import { CSSTransition } from 'react-transition-group';
import ArrowLeftIcon from '../../../assets/images/icons/arrow-left.svg'
import { useHistory } from 'react-router';

const Settings = () => {

    const account = {
        firstName: useFormState(''),
        secondName: useFormState(''),
        thirdName: useFormState(''),
        email: useFormState(''),
        password: useFormState(''),
        address: useFormState(''),
        passport: useFormState('')
    }

    const history = useHistory()

    const [error, setError] = useState(false);

    // const registerAccount = ({firstName, secondName, thirdName, email, password, address, passport}:any, e:any) => {
    //     e.preventDefault();
    //     register({
    //         firstName: firstName.value,
    //         secondName: secondName.value,
    //         thirdName: thirdName.value,
    //         email: email.value,
    //         password: password.value,
    //         address: address.value,
    //         passport: passport.value
    //     })
    //     .then((response:AxiosResponse<RegistrationResponse>) => {
    //         alert('Success')
    //     })
    //     .catch(err => {
    //         setError(true)
    //         setTimeout(() => setError(false), 3500);
    //     })
    // }

    return (
        <div className="settings">
            <button className="back-btn" onClick={() => history.goBack()}><img src={ArrowLeftIcon} alt="" /> Назад</button>
            <form>
                <h1>Настройки</h1>
                <fieldset>
                    <h3>Данные об организации</h3>
                    <label>
                        ИНН
                        <input type="number" name="name" {...account.firstName} placeholder="Введите ИНН организации" className="main-input" readOnly />
                    </label>
                    <label>
                        КПП
                        <input type="text" name="login" placeholder="Введите КПП" className="main-input" readOnly />
                    </label>
                    <label>
                        Полное { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } наименование { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } организации
                        <input type="password" name="password" {...account.password} placeholder="Введите полное наименование" className="main-input" readOnly />
                    </label>
                    <label>
                        Краткое { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } наименование { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } организации
                        <input type="email" name="email" {...account.email} placeholder="Введите краткое наименование" className="main-input" readOnly />
                    </label>
                    <label>
                        Адрес организации
                        <input type="text" name="address" {...account.address} placeholder="Введите адрес организации" className="main-input" readOnly />
                    </label>
                </fieldset>
                <fieldset>
                    <h3>Данные пользователя</h3>
                    <label>
                        ФИО
                        <input type="text" name="name" {...account.firstName} placeholder="Введите КПП" className="main-input" readOnly />
                    </label>
                    <label>
                        Логин
                        <input type="text" name="login" placeholder="Введите полное наименование" className="main-input" readOnly />
                    </label>
                    <label>
                        Пароль
                        <input type="password" name="password" {...account.password} placeholder="Введите краткое наименование" className="main-input" readOnly />
                    </label>
                    <label>
                        E-mail
                        <input type="email" name="email" {...account.email} placeholder="Введите краткое наименование" className="main-input" readOnly />
                    </label>
                </fieldset>
                <button className="common-btn">СОХРАНИТЬ</button>
            </form>
        </div>
    )
}

export default Settings;
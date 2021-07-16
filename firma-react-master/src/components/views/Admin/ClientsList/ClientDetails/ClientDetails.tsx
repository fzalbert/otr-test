import React, { useState, useEffect } from 'react';
import { useHistory, useParams } from 'react-router';
import './ClientDetails.scss';
import ArrowLeftIcon from '../../../../../assets/images/icons/arrow-left.svg';
import useFormState from '../../../../../common/customHooks/useFormState';
import ClientsAPI from '../../../../../api/clients';
import { AxiosResponse } from 'axios';
import { ClientDetailsResponse, ClientResponse } from '../../../../../api/models/response/client-response.model';

const ClientDetails = () => {
    const history = useHistory()

    const { clientId } = useParams<{ clientId: string }>()

    const [req, doReq] = useState(false)

    const clientData = {
        email: useFormState(''),
        fio: useFormState(''),
        fullAddress: useFormState(''),
        fullNameOrg: useFormState(''),
        inn: useFormState(''),
        kpp: useFormState(''),
        login: useFormState(''),
        shortNameOrg: useFormState('')
    }

    const getClient = () => {
        ClientsAPI.getClientById(+clientId)
            .then((response:AxiosResponse<ClientDetailsResponse>) => {
                doReq(true)
                clientData.email.onChange({target: { value: response.data.email }})
                clientData.fio.onChange({target: { value: response.data.fio }})
                clientData.fullAddress.onChange({target: { value: response.data.fullAddress }})
                clientData.fullNameOrg.onChange({target: { value: response.data.fullNameOrg }})
                clientData.inn.onChange({target: { value: response.data.inn }})
                clientData.kpp.onChange({target: { value: response.data.kpp }})
                clientData.login.onChange({target: { value: response.data.login }})
                clientData.shortNameOrg.onChange({target: { value: response.data.shortNameOrg }})
            }).catch(err => console.log(err))
    }

    useEffect(() => {
        !req ? getClient() : null
    })

    return (
        <div className="client-details">
            <button className="back-btn" onClick={() => history.goBack()}><img src={ArrowLeftIcon} alt="" /> Назад</button>
            <h1>Просмотр пользователя</h1>
            <fieldset>
                <label>
                    ИНН
                    <input type="number" name="inn" {...clientData.inn} placeholder="Введите ИНН организации" className="main-input" readOnly />
                </label>
                <label>
                    КПП
                    <input type="text" name="kpp" {...clientData.kpp} placeholder="Введите КПП" className="main-input" readOnly />
                </label>
                <label>
                    Полное { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } наименование { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } организации
                    <input type="text" name="full" {...clientData.fullNameOrg} placeholder="Введите полное наименование" className="main-input" readOnly />
                </label>
                <label>
                    Краткое { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } наименование { window.document.documentElement.clientWidth < 1640 ? <br /> : '' } организации
                    <input type="text" name="short" {...clientData.shortNameOrg} placeholder="Введите краткое наименование" className="main-input" readOnly />
                </label>
                <label>
                    Адрес организации
                    <input type="text" name="address" {...clientData.fullAddress} placeholder="Введите адрес организации" className="main-input" readOnly />
                </label>
            </fieldset>
            <fieldset>
                <label>
                    ФИО
                    <input type="text" name="name" {...clientData.fio} placeholder="Введите ФИО" className="main-input" readOnly />
                </label>
                <label>
                    Логин
                    <input type="text" name="login" {...clientData.login} placeholder="Введите логин" className="main-input" readOnly />
                </label>
                <label>
                    E-mail
                    <input type="email" name="email" {...clientData.email} placeholder="Введите адрес эл. почты" className="main-input" readOnly />
                </label>
            </fieldset>
        </div>
    )
}

export default ClientDetails;
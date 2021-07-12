import React, { useState, useEffect } from 'react';
import './ClientsList.scss';
import activityIcon from '../../../assets/images/sidebar/activity.svg';
import creditCardIcon from '../../../assets/images/sidebar/credit-card.svg';
import { CSSTransition } from 'react-transition-group';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory } from 'react-router';
import { NavLink } from 'react-router-dom';
import { AxiosResponse } from 'axios';
import LogotypeIcon from '../../../assets/images/logotype.svg';
import Filter from '../../../ui/Filter/Filter';

const activityStyle = {
    // backgroundImage: `url(${activityIcon})`
}

const ClientsList = (props:any) => {
    const history = useHistory()

    return (
        <div className="clients-list">
            <table className="custom-table">
                <thead>
                    <tr>
                        <th>№</th>
                        <th>ФИО</th>
                        <th>E-mail</th>
                        <th>Организация</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {
                        [0,1,2,3,4,5,6].map(item => (
                            <React.Fragment key={item}>
                                <tr>
                                    <td aria-label="№">{ 24 }</td>
                                    <td aria-label="ФИО">{ 'Иванов Иван Иванович' }</td>
                                    <td aria-label="E-mail">{ 'jackson.graham@example.com' }</td>
                                    <td aria-label="Организация">{ 'ООО “АБВГД ЕЁЖЗ”' }</td>
                                    <td><button className="common-btn" onClick={() => history.push('/appeal')}>Подробнее</button></td>
                                    <td><button className="common-btn transparent-btn" onClick={() => history.push('/appeal')}>ЗАБЛОКИРОВАТЬ</button></td>
                                </tr>
                                <tr className="spacer"></tr>
                            </React.Fragment>
                        ))
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ClientsList;
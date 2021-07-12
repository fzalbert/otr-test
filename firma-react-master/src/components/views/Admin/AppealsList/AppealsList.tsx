import React, { useState, useEffect } from 'react';
import './AppealsList.scss';
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

const AppealsList = (props:any) => {
    const history = useHistory()

    return (
        <div className="appeals-list">
            <Filter filterList={[0,1,2,3,4,5,6]} />
            <button className="common-btn update-btn">ОБНОВИТЬ</button>
            <table className="custom-table">
                <thead>
                    <tr>
                        <th>№</th>
                        <th>Дата создания</th>
                        <th>Статус</th>
                        <th>Тема</th>
                        <th>Организация</th>
                        <th>Исполнитель</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {
                        [0,1,2,3,4,5,6].map(item => (
                            <React.Fragment key={item}>
                                <tr>
                                    <td aria-label="№">{ 24 }</td>
                                    <td aria-label="Дата создания">{ '24.05.2021 18:30' }</td>
                                    <td aria-label="Статус"><span className="status">{ 'НА  РАССМОТРЕНИИ' }</span></td>
                                    <td aria-label="Тема">{ 'Тема обращения в одну-две строки. длина - 400 пикселей' }</td>
                                    <td aria-label="Организация">{ 'ООО “Рога и Копыта”' }</td>
                                    <td aria-label="Исполнитель">{ 'Иванов Иван' }</td>
                                    <td><button className="common-btn" onClick={() => history.push('/appeal')}>ПОДРОБНЕЕ</button></td>
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

export default AppealsList;
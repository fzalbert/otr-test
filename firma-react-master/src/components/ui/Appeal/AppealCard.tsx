import React, { useState, useEffect } from 'react';
import './AppealCard.scss';
import { useHistory } from 'react-router';
import { AppealItemClientModel, AppealItemModel } from '../../../api/models/response/appeals-response.model';

const AppealCard = (props:AppealItemClientModel) => {
    const history = useHistory();

    const appealStatus:string[] = ['Создано', 'На рассмотрении', 'Выполнено', 'Отклонено']
    const appealStatusClasses:string[] = ['not-proccessing-status', 'proccessing-status', 'complete-status', 'reject-status']

    return (
        <div className="appeal-card">
            <span className={"status " + appealStatusClasses[props.statusAppeal]}>{ appealStatus[props.statusAppeal] }</span>
            <h5>{ props.theme.name }</h5>
            <p>
                { props.description.slice(0,250) }
            </p>
            <button className="common-btn" onClick={() => history.push(`/appeal/${props.id}`)}>ПОДРОБНЕЕ</button>
        </div>
    )
}

export default AppealCard;
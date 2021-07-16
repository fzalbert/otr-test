import React, { useState, useEffect } from 'react';
import './AppealCard.scss';
import activityIcon from '../../../assets/images/sidebar/activity.svg';
import creditCardIcon from '../../../assets/images/sidebar/credit-card.svg';
import { CSSTransition } from 'react-transition-group';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory } from 'react-router';
import { NavLink } from 'react-router-dom';
import { AxiosResponse } from 'axios';
import { UserModel } from '../../../api/models/user.model';
import LogotypeIcon from '../../../assets/images/logotype.svg';
import { AppealItemClientModel, AppealItemModel } from '../../../api/models/response/appeals-response.model';

const activityStyle = {
    // backgroundImage: `url(${activityIcon})`
}

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
            {/* <span className="status">НА  РАССМОТРЕНИИ</span>
            <h5>Тема обращения в одну-две строки</h5>
            <p>
                Как уже неоднократно упомянуто, стремящиеся вытеснить традиционное производство, 
                нанотехнологии лишь добавляют фракционных разногласий и подвергнуты целой серии независимых исследований. 
                С учётом сложившейся международной обстановки, консультация...
            </p> */}
        </div>
    )
}

export default AppealCard;
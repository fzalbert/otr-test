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

const activityStyle = {
    // backgroundImage: `url(${activityIcon})`
}

const AppealCard = (props:any) => {
    const history = useHistory();

    return (
        <div className="appeal-card">
            <span className="status">НА  РАССМОТРЕНИИ</span>
            <h5>Тема обращения в одну-две строки</h5>
            <p>
                Как уже неоднократно упомянуто, стремящиеся вытеснить традиционное производство, 
                нанотехнологии лишь добавляют фракционных разногласий и подвергнуты целой серии независимых исследований. 
                С учётом сложившейся международной обстановки, консультация...
            </p>
            <button className="common-btn" onClick={() => history.push('/appeal')}>ПОДРОБНЕЕ</button>
        </div>
    )
}

export default AppealCard;
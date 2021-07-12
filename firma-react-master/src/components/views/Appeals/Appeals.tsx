import React, { useState, useEffect } from 'react';
import './Appeals.scss';
import activityIcon from '../../../assets/images/sidebar/activity.svg';
import creditCardIcon from '../../../assets/images/sidebar/credit-card.svg';
import { CSSTransition } from 'react-transition-group';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory } from 'react-router';
import { NavLink } from 'react-router-dom';
import { AxiosResponse } from 'axios';
import { UserModel } from '../../../api/models/user.model';
import LogotypeIcon from '../../../assets/images/logotype.svg';
import AppealCard from '../../ui/Appeal/AppealCard';

const activityStyle = {
    // backgroundImage: `url(${activityIcon})`
}

const Appeals = (props:any) => {
    const history = useHistory()

    return (
        <div className="appeals-component">
            <div className="heading">
                <h1>Ваши обращения</h1>
                <button className="common-btn" onClick={() => history.push('/new-appeal')}>НОВОЕ ОБРАЩЕНИЕ</button>
            </div>
            <div className="appeals-container">
                {
                    [0,1,2,3,4,5,6,7,8].map(item => <div className="appeal-container"><AppealCard /></div>)
                }
            </div>
        </div>
    )
}

export default Appeals;
import React, { useState, useEffect } from 'react';
import './AppealCreation.scss';
import activityIcon from '../../../assets/images/sidebar/activity.svg';
import creditCardIcon from '../../../assets/images/sidebar/credit-card.svg';
import { CSSTransition } from 'react-transition-group';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory } from 'react-router';
import { NavLink } from 'react-router-dom';
import { AxiosResponse } from 'axios';
import { UserModel } from '../../../api/models/user.model';
import LogotypeIcon from '../../../assets/images/logotype.svg';
import ArrowLeftIcon from '../../../assets/images/icons/arrow-left.svg';

const activityStyle = {
    // backgroundImage: `url(${activityIcon})`
}

const AppealCreation = (props:any) => {
    const [subjectState, setSubjectState] = useState(false)
    const [subject, setSubject] = useState(0)

    const history = useHistory()

    return (
        <div className="appeal-creation-component">
            <button className="back-btn" onClick={() => history.goBack()}><img src={ArrowLeftIcon} alt="" /> Назад</button>
            <h1>Новое обращение</h1>
            <label className="select-label" onFocus={() => setSubjectState(!subjectState)} onBlur={() => setSubjectState(false)}>
                Тема обращения
                <input type="text" className="main-input" placeholder="Выберите тему из списка предложенных" />
                {
                    subjectState ?
                        <ul className="select-list">
                            <li className="select-list_item" onMouseDown={() => setSubject(0)}>Тема 1</li>
                            <li className="select-list_item" onMouseDown={() => setSubject(1)}>Тема 2</li>
                            <li className="select-list_item" onMouseDown={() => setSubject(2)}>Тема 3</li>
                        </ul> :
                        null
                }
            </label>
            <label>
                Текст обращения
                <textarea name="appeal-text" className="main-input" cols={30} rows={10} placeholder="Введите текст обращения"></textarea>
            </label>
            {
                ![].length ? 
                    <div className="attachments-container">
                        <button>Прикрепить файл</button>
                        <div className="attachments">
                            <div className="attachment">
                                Смета 2234 Нижний новгород...dox
                            </div>
                        </div>
                    </div> :
                    null
            }
            <label>
                Срок реализации проекта
                <input type="text" className="main-input" placeholder="Введите срок" />
            </label>
            <label>
                Вид категории затрат
                <input type="text" className="main-input" placeholder="Введите категорию затрат" />
            </label>
            <label>
                ТН ВЭД
                <input type="text" className="main-input" placeholder="Введите  ТН ВЭД" />
            </label>
            <label>
                Сумма расходов по заявке
                <input type="text" className="main-input" placeholder="Введите  ТН ВЭД" />
            </label>
            <button className="common-btn">СОЗДАТЬ</button>
        </div>
    )
}

export default AppealCreation;
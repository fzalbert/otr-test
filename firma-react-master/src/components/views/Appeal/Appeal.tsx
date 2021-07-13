import React, { useState, useEffect } from 'react';
import './Appeal.scss';
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

const Appeal = (props:any) => {

    const history = useHistory()

    return (
        <div className="appeal-component">
            <button className="back-btn" onClick={() => history.goBack()}><img src={ArrowLeftIcon} alt="" /> Назад</button>
            <h1>Обращение</h1>
            <span className="status">СОЗДАНО</span>
            {
                false ?
                    <div className="response-container">
                        <h5>Тема обращения в одну-две строки с каким-то ограничением по количеству символов</h5>
                        <span className="creation-date">Создано: 23.01.2021 18:45</span>
                        <p>
                            Как уже неоднократно упомянуто, стремящиеся вытеснить традиционное производство, нанотехнологии лишь добавляют 
                            фракционных разногласий и подвергнуты целой серии независимых исследований. С учётом сложившейся международной 
                            обстановки, консультация...Как уже неоднократно упомянуто, стремящиеся вытеснить традиционное производство, 
                            нанотехнологии лишь добавляют фракционных разногласий и подвергнуты целой серии независимых исследований. 
                        </p>                
                    </div> :
                    null
            }
            <span className="creation-date">Создано: 23.01.2021 18:45</span>
            <h5>Тема обращения в одну-две строки с каким-то ограничением по количеству символов</h5>
            <p>
                Как уже неоднократно упомянуто, стремящиеся вытеснить традиционное производство, 
                нанотехнологии лишь добавляют фракционных разногласий и подвергнуты целой серии независимых исследований. 
                С учётом сложившейся международной обстановки, консультация...Как уже неоднократно упомянуто, стремящиеся 
                вытеснить традиционное производство, нанотехнологии лишь добавляют фракционных разногласий и подвергнуты 
                целой серии независимых исследований. С учётом сложившейся международной обстановки, консультация с широким
                активом играет определяющее значение для распределения внутренних резервов и ресурсов. А ещё предприниматели
                в сети интернет представляют собой не что иное, как квинтэссенцию победы маркетинга над разумом и должны
                быть смешаны с не уникальными данными до степени совершенной неузнаваемости, из-за чего возрастает их 
                статус бесполезности. Повседневная практика показывает, что убеждённость некоторых оппонентов позволяет 
                выполнить важные задания по разработке форм воздействия. Лишь активно развивающиеся страны третьего мира 
                представлены в исключительно положительном свете. В частности, новая модель организационной деятельности 
                требует определения и уточнения вывода текущих активов!
            </p>
            {
                [].length ? 
                    <div className="attachments-container">
                        <h6>Прикрепленные файлы</h6>
                        <div className="attachments">
                            <div className="attachment">
                                Смета 2234 Нижний новгород...dox
                            </div>
                        </div>
                    </div> :
                    null
            }
            <ul className="data-list">
                <li className="data-item">
                    Срок реализации проекта <span className="data-item_value">24.05.2021</span>
                </li>
                <li className="data-item">
                    Вид категории затрат <span className="data-item_value">Категория</span>
                </li>
                <li className="data-item">
                    ТН ВЭД <span className="data-item_value">24438-354</span>
                </li>
                <li className="data-item">
                    Сумма расходов по заявке <span className="data-item_value">100000 р.</span>
                </li>
            </ul>
        </div>
    )
}

export default Appeal;
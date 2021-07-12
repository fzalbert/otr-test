import React, { useState } from 'react';
import './Filter.scss';
// import arrowIcon from '../../../assets/images/icons/arrow.svg';
import filterIcon from '../../../assets/images/icons/filter.svg';
import '../../../assets/libs/react-dates/lib/initialize';
import { DateRangePicker } from '../../../assets/libs/react-dates';
import moment from 'moment';

const Filter = (props:{filterList:any}) => {
    const [sortingVision, setSortingVision] = useState(false)
    const [statusVision, setStatusVision] = useState(false)
    const [subjectVision, setSubjectVision] = useState(false)

    return(
        <div className="filter">
            <label className="select-label">
                Сортировка
                <input type="text" className="main-input" placeholder="Сначала новые" />
                {
                    sortingVision ?
                        <ul className="select-list">
                            <li className="select-list_item">Сначала новые</li>
                            <li className="select-list_item">Сначала старые</li>
                            <li className="select-list_item">Сначала на рассмотрении</li>
                        </ul> :
                        null
                }
            </label>
            <label className="select-label">
                Статус
                <input type="text" className="main-input" placeholder="Все" />
                {
                    statusVision ?
                        <ul className="select-list">
                            <li className="select-list_item">Сначала новые</li>
                            <li className="select-list_item">Сначала старые</li>
                            <li className="select-list_item">Сначала на рассмотрении</li>
                        </ul> :
                        null
                }
            </label>
            <label className="select-label">
                Тема обращения
                <input type="text" className="main-input" placeholder="Все" />
                {
                    subjectVision ?
                        <ul className="select-list">
                            <li className="select-list_item">Сначала новые</li>
                            <li className="select-list_item">Сначала старые</li>
                            <li className="select-list_item">Сначала на рассмотрении</li>
                        </ul> :
                        null
                }
            </label>
        </div>
    );
}

export default Filter;
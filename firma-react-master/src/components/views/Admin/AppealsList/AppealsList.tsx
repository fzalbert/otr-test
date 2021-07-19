import React, { useState, useEffect } from 'react';
import './AppealsList.scss';
import { CSSTransition } from 'react-transition-group';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory } from 'react-router';
import { AxiosResponse } from 'axios';
import Filter from '../../../ui/Filter/Filter';
import AppealsAPI from '../../../../api/appeals';
import { AppealItemModel } from '../../../../api/models/response/appeals-response.model';
import { setAppealsList, setSortList } from '../../../../store/actions/appeals-actions';
import Moment from 'react-moment';
import { FilterRequest } from '../../../../api/models/request/filter-body-request.model';
import { Link } from 'react-router-dom';

const AppealsList = (props:any) => {

    const appealsState: AppealItemModel[] = useSelector((state:any) => state.AppealsReducer)

    const history = useHistory()
    const dispatch = useDispatch()

    const appealStatus:string[] = ['Создано', 'На рассмотрении', 'Выполнено', 'Отклонено']
    const appealStatusClasses:string[] = ['not-proccessing-status', 'proccessing-status', 'complete-status', 'reject-status']

    const [req, doReq] = useState(false)

    const getAppealsList = (fitlerBody: FilterRequest) => {
        doReq(true)
        AppealsAPI.getFilterForAdmin(fitlerBody)
            .then((response:AxiosResponse<AppealItemModel[]>) => {
                console.log(response.data)
                dispatch(setAppealsList(response.data))
            })
            .catch(err => console.log(err))
    }

    useEffect(() => {
        if(!req) {
            getAppealsList({
                themeId: null,
                date: null,
                statusAppeal: null,
                employeeId: null,
                nameOrg: null
            });
        }
    })


    return (
        <div className="appeals-list">
            <Filter 
                filterType={0}
                sortingEvent={(type) => {
                    dispatch(setSortList(type))
                }}
                filterEvent={(event: FilterRequest) => getAppealsList(event)}
            />
            {
                appealsState.length > 0 ? <table className="custom-table">
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
                            appealsState?.map((item: AppealItemModel, index) => 
                                <React.Fragment key={item.id}>
                                    <tr>
                                        <td aria-label="№">{ item.id }</td>
                                        <td aria-label="Дата создания"><Moment format="DD.MM.YYYY hh:mm">{ item.createDate }</Moment></td>
                                        <td aria-label="Статус"><span  className={"status " + appealStatusClasses[item.statusAppeal]}>{ appealStatus[item.statusAppeal] }</span></td>
                                        <td aria-label="Тема">{ item.theme.name.length > 60 ? item.theme.name.slice(0,60) + '...' : item.theme.name }</td>
                                        <td aria-label="Организация">{ item.nameOrg }</td>
                                        <td aria-label="Исполнитель"><Link to={`/admin/staff/${item.employeeId}`}>{ item.employeeId }</Link></td>
                                        <td><button className="common-btn" onClick={() => history.push(`/admin/appeal/${item.id}`)}>ПОДРОБНЕЕ</button></td>
                                    </tr>
                                    <tr className="spacer"></tr>
                                </React.Fragment>
                            )
                        }
                    </tbody>
                </table> : 
                <h1 className="no-data"> Нет обращений по заданным параметрам</h1>
            }
        </div>
    )
}

export default AppealsList;
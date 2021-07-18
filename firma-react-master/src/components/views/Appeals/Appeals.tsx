import React, { useState, useEffect } from 'react';
import './Appeals.scss';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory } from 'react-router';
import { AxiosResponse } from 'axios';
import AppealCard from '../../ui/Appeal/AppealCard';
import AppealsAPI from '../../../api/appeals';
import { AppealItemClientModel } from '../../../api/models/response/appeals-response.model';
import { setClientAppealsList, setSortList } from '../../../store/actions/appeals-actions';
import Filter from '../../ui/Filter/Filter';
import { FilterRequest } from '../../../api/models/request/filter-body-request.model';

const Appeals = (props:any) => {
    const appealsState: AppealItemClientModel[] = useSelector((state:any) => state.AppealsReducer)

    const history = useHistory()
    const dispatch = useDispatch()

    const [req, doReq] = useState(false)

    const getAppealsList = (filterBody: FilterRequest) => {
        doReq(true)
        AppealsAPI.getFilterForClient(filterBody)
            .then((response:AxiosResponse<AppealItemClientModel[]>) => {
                dispatch(setClientAppealsList(response.data))
                console.log(response.data)
            })
            .catch(err => console.log(err))
    }

    useEffect(() => {
        if(!req) {
            getAppealsList({
                themeId: null,
                date: null,
                statusAppeal: null
            });
        }
    }, [req, appealsState])

    return (
        <div className="appeals-component">
            <Filter 
                filterType={1}
                sortingEvent={(type: number) => 
                    dispatch(setSortList(type))
                }
                filterEvent={(event: FilterRequest) => getAppealsList(event)}
            />
            <div className="heading">
                <h1>Ваши обращения</h1>
                <button className="common-btn" onClick={() => history.push('/new-appeal')}>НОВОЕ ОБРАЩЕНИЕ</button>
            </div>
            <div className="appeals-container">
                {
                    appealsState.length ?
                        appealsState.map((item:AppealItemClientModel) => <div className="appeal-container"><AppealCard {...item} key={item.id} /></div>) :
                        <h1 className="no-data"> Нет обращений по заданным параметрам</h1>
                }
            </div>
        </div>
    )
}

export default Appeals;
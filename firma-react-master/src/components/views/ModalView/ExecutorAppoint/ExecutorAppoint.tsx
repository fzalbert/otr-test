import { AxiosResponse } from 'axios';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { EmployeeResponse } from '../../../../api/models/response/employee-response.model';
import StaffsAPI from '../../../../api/staffs';
import TasksAPI from '../../../../api/tasks';
import './ExecutorAppoint.scss';

const ExecutorAppoint = (props:{toggleExecutorModal: () => void}) => {

    const { id } = useParams<{id: string}>();

    const [executors,setExecutors] = useState<EmployeeResponse[]>([])
    const [executor,setExecutor] = useState<EmployeeResponse>()

    const [req, doReq] = useState(false)

    const getExecutors = () => {
        StaffsAPI.getAllEmployees()
            .then((response:AxiosResponse<EmployeeResponse[]>) => {
                doReq(true)
                setExecutors(response.data)
            })
            .catch(err => console.log(err))
    }

    const appointExecutor = () => {
        if(!executor) {
            return
        }
        TasksAPI.appoint(executor?.id as number, +id)
            .then((response:AxiosResponse<void>) => {
                props.toggleExecutorModal()
            })
            .catch(err => console.log(err))
    }

    useEffect(() => {
        !req ? getExecutors() : null;
    })

    return (
        <div className="executors-container">
            <h5>Выберите из списка исполнителей</h5>
            <div className="executors-list">
                {
                    executors.map(item => 
                        <div className="executor" key={item.id}>
                            {/* <label className="radio-label"></label> */}
                            <input type="radio" name="executor" value={item.id as number} onChange={() => setExecutor(item)} />
                            <span>{ (item.lastName as string)?.split(' ').slice(0,2).join(' ') }</span>
                        </div>
                    )
                }
            </div>
            <button className="common-btn" onClick={() => appointExecutor()}>НАЗНАЧИТЬ</button>
        </div>
    )
}

export default ExecutorAppoint;
import React, { useState, useEffect } from 'react';
import './Staffs.scss';
import { useHistory } from 'react-router';
import { AxiosResponse } from 'axios';
import StaffsAPI from '../../../../api/staffs';
import { EmployeeResponse } from '../../../../api/models/response/employee-response.model';

const StaffsList = (props:any) => {
    const history = useHistory()

    const [staffsList, setStaffsList] = useState<EmployeeResponse[]>([]);

    const [req, doReq] = useState(false)

    const roles:string[] = ['Суперадмин', 'Админ', 'Сотрудник']

    const getStaffs = () => {
        doReq(true)
        StaffsAPI.getAllEmployees()
            .then((res:AxiosResponse<EmployeeResponse[]>) => {
                setStaffsList(res.data)
            })
            .catch(err => console.log(err))
    }

    const toggleBlock = (employee: EmployeeResponse) => {
        (employee.active ?
            StaffsAPI.blockById(employee.id as number) :
            StaffsAPI.unblockById(employee.id as number)
        ).then((response:AxiosResponse<void>) => {
            let index = staffsList.findIndex(item => item.id === employee.id)
            employee.active = !employee.active;
            setStaffsList([...staffsList.slice(0,index), employee, ...staffsList.slice(index + 1)])
        }).catch(err => console.log(err))
    }

    useEffect(() => {
        !req ? getStaffs() : null
    })

    return (
        <div className="staffs-list">
            <button className="common-btn creation-btn" onClick={() => history.push('/admin/staff-creation')}>Создать нового</button>
            <table className="custom-table">
                <thead>
                    <tr>
                        <th>№</th>
                        <th>Имя</th>
                        <th>Фамилия</th>
                        <th>E-mail</th>
                        <th>Логин</th>
                        <th>Роль</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {
                        staffsList.map(item => (
                            <React.Fragment key={item.id}>
                                <tr>
                                    <td aria-label="№">{ item.id }</td>
                                    <td aria-label="Имя">{ item.firstName }</td>
                                    <td aria-label="Фамилия">{ item.lastName }</td>
                                    <td aria-label="E-mail">{ item.email }</td>
                                    <td aria-label="Логин">{ item.login }</td>
                                    <td aria-label="Роль">{ roles[item.role] }</td>
                                    <td><button className="common-btn" onClick={() => history.push(`/admin/staff/${item.id}`)}>Изменить</button></td>
                                    <td><button className={`common-btn transparent-btn${!item.active ? ' blocked' : ''}`} onClick={() => toggleBlock(item)}>{ item.active ? 'ЗА' : 'РАЗ' }БЛОКИРОВАТЬ</button></td>
                                </tr>
                                <tr className="spacer"></tr>
                            </React.Fragment>
                        ))
                    }
                </tbody>
            </table>
        </div>
    )
}

export default StaffsList;
import React, { useState, useEffect } from 'react';
import './StaffCreation.scss';
import { useHistory, useParams } from 'react-router';
import { EmployeeResponse } from '../../../../../api/models/response/employee-response.model';
import useFormState from '../../../../../common/customHooks/useFormState';
import ArrowLeftIcon from '../../../../../assets/images/icons/arrow-left.svg';
import StaffsAPI from '../../../../../api/staffs';
import { AxiosError, AxiosResponse } from 'axios';
import { CSSTransition } from 'react-transition-group';
import ErrorModal from '../../../../ui/ErrorModal/ErrorModal';

const StaffCreation = (props: any) => {
    const [roleVision, toggleRoleVision] = useState(false)
    const [role, setRole] = useState(0)

    const { employeeId } = useParams<{ employeeId: string }>()

    const history = useHistory()

    const [error, setError] = useState("");

    const [employeeData, setEmployeeData] = useState<EmployeeResponse>()

    const employee = {
        firstName: useFormState(""),
        lastName: useFormState(""),
        login: useFormState(""),
        password: useFormState(""),
        email: useFormState(""),
        role: useFormState(0)
    }

    const roles:string[] = ['Суперадмин', 'Админ', 'Сотрудник']

    const [req, doReq] = useState(false);

    const getEmployeeById = () => {
        StaffsAPI.getEmployeeById(+employeeId)
            .then((response:AxiosResponse<EmployeeResponse>) => {
                doReq(true)
                setEmployeeData(response.data)
                employee.firstName.onChange({target: { value: response.data.firstName }})
                employee.lastName.onChange({target: { value: response.data.lastName }})
                employee.login.onChange({target: { value: response.data.login }})
                employee.email.onChange({target: { value: response.data.email }})
                employee.role.onChange({target: { value: response.data.role }})
                setRole(response.data.role)
            })
            .catch((err:AxiosError) => {
                console.log(err)
                setError(err.response?.data.message)
                setTimeout(() => setError(""), 5000);
            })
    }

    const addEmployee = () => {
        StaffsAPI.create({
            firstName: employee.firstName.value,
            lastName: employee.lastName.value,
            login: employee.login.value,
            password: employee.password.value,
            email: employee.email.value,
            roleType: employee.role.value
        }).then((response:AxiosResponse<EmployeeResponse>) => {
            history.push('/admin/staffs')
        }).catch((err:AxiosError) => {
            console.log(err)
            setError(err.response?.data.message)
            setTimeout(() => setError(""), 5000);
        })
    }

    const updateEmployee = () => {
        StaffsAPI.update(+employeeId, {
            firstName: employee.firstName.value,
            lastName: employee.lastName.value,
            login: employee.login.value,
            email: employee.email.value,
            roleType: employee.role.value
        }).then((response:AxiosResponse<EmployeeResponse>) => {
            history.push('/admin/staffs')
        }).catch((err:AxiosError) => {
            console.log(err)
            setError(err.response?.data.message)
            setTimeout(() => setError(""), 5000);
        })
    }

    useEffect(() => {
        !req && employeeId ?
            getEmployeeById() :
            null
    })

    return (
        <div className="staff-creation-component">
            <CSSTransition in={error.length !== 0} timeout={300} unmountOnExit classNames="show-hide-animation">
                <ErrorModal>
                    { error }
                </ErrorModal>
            </CSSTransition>
            <button className="back-btn" onClick={() => history.goBack()}><img src={ArrowLeftIcon} alt="" /> Назад</button>
            <h1>{ employeeId ? 'Редактирование пользователя' : 'Новый пользователь' }</h1>
            <label>
                Имя
                <input type="text" name="name" {...employee.firstName} placeholder="Введите имя" className="main-input"  />
            </label>
            <label>
                Фамилия
                <input type="text" name="name" {...employee.lastName} placeholder="Введите фамилию" className="main-input"  />
            </label>
            <label>
                Логин
                <input type="text" name="login" {...employee.login} placeholder="Введите логин" className="main-input"  />
            </label>
            {
                !employeeId ?
                    <label>
                        Пароль
                        <input type="text" name="login" {...employee.password} placeholder="Введите пароль" className="main-input"  />
                    </label> :
                    null
            }
            <label>
                E-mail
                <input type="email" name="email" {...employee.email} placeholder="Введите email" className="main-input"  />
            </label>
            <label className="select-label" onFocus={() => toggleRoleVision(!roleVision)} onBlur={() => toggleRoleVision(false)}>
                Роль
                <input type="text" className="main-input" value={roles?.find((item:string, index: number) => index === role)} placeholder="Выберите роль" readOnly />
                {
                    roleVision ?
                        <ul className="select-list">
                            { 
                                roles.map((item: string, index: number) => 
                                    <li className="select-list_item" key={index} onMouseDown={() => {
                                        setRole(index)
                                        employee.role.onChange({target: { value: index }})
                                    }}>{ item }</li>
                                )
                            }
                        </ul> :
                        null
                }
            </label>
            <button className="common-btn" onClick={() => employeeId ? updateEmployee() : addEmployee()}>СОЗДАТЬ</button>
        </div>
    )
}

export default StaffCreation;
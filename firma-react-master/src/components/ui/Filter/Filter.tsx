import React, { useEffect, useState } from 'react';
import './Filter.scss';
// import arrowIcon from '../../../assets/images/icons/arrow.svg';
import '../../../assets/libs/react-dates/lib/initialize';
import ThemeAPI from '../../../api/theme';
import { AxiosResponse } from 'axios';
import { ThemeResponse } from '../../../api/models/response/theme-response.model';
import { useDispatch, useSelector } from 'react-redux';
import { setThemesList } from '../../../store/actions/themes-actions';
import useFormState from '../../../common/customHooks/useFormState';
import StaffsAPI from '../../../api/staffs';
import { EmployeeResponse } from '../../../api/models/response/employee-response.model';
import { setStaffsList } from '../../../store/actions/staffs-actions';
import useDebounce from '../../../common/customHooks/useDebounce';
import { FilterRequest } from '../../../api/models/request/filter-body-request.model';

const Filter = (props:{sortingEvent: (type: number) => void, filterEvent: (filterBody:FilterRequest) => void, filterType: number}) => {
    // filterType 0 - Admin, 1 - Client
    const [sortingVision, setSortingVision] = useState(false)
    const [statusVision, setStatusVision] = useState(false)
    const [subjectVision, setSubjectVision] = useState(false)
    const [executorVision, setExecutorVision] = useState(false)

    const [sorting, setSorting] = useState(0)
    const [status, setStatus] = useState<number | null>(null)
    const [subject, setSubject] = useState<number | null>(0)
    const [executorId, setExecutorId] = useState<number | null>(null)
    const date = useFormState(null)
    const orgName = useFormState('')

    const dispatch = useDispatch()

    const sortList = ['Сначала новые', 'Сначала старые']
    const statusesList = ['Все', 'Создано', 'На рассмотрении', 'Выполнено', 'Отклонено']

    const executorsList = useSelector((state: any) => state.StaffsReducer)

    const [reqSubjects, doReqSubjects] = useState(false)
    const [reqExecutors, doReqExecutors] = useState(false)

    const themesList = useSelector((state: any) => state.ThemesReducer)

    const getSubjects = () => {
        ThemeAPI.getAllThemes()
            .then((response:AxiosResponse<ThemeResponse[]>) => {
                doReqSubjects(true)
                dispatch(setThemesList([{
                    id: null,
                    name: "Все темы"
                }, ...response.data]))
            })
            .catch(err => console.log(err))
    }

    const getExecutors = () => {
        StaffsAPI.getAllEmployees()
            .then((response: AxiosResponse<EmployeeResponse[]>) => {
                doReqExecutors(true)
                dispatch(setStaffsList([{
                    id: null,
                    firstName: "Все",
                    lastName:"исполнители",
                    login:"",
                    email:"",
                    role:0,
                    active: true
                }, ...response.data]))
            }).catch(err => console.log(err))
    }

    const emitFilterEvent = () => {
        props.filterEvent(
            props.filterType === 0 ?
            {
                themeId: subject,
                date: date.value,
                statusAppeal: status,
                employeeId: executorId,
                nameOrg: orgName.value
            } :
            {
                themeId: subject,
                date: date.value,
                statusAppeal: status
            }
        )
    }

    const debouncedFilter = useDebounce(orgName, 500)

    useEffect(() => {
        if(!reqSubjects && (!themesList || !themesList?.length)) {
            getSubjects()
        }
        if(!reqExecutors && (!executorsList || !executorsList?.length)) {
            getExecutors()
        }
    }, [subject, executorId,status,sorting,date,debouncedFilter])


    return(
        <div className="filter">
            <label className="select-label" onFocus={() => setSortingVision(!sortingVision)} onBlur={() => setSortingVision(false)}>
                Сортировка
                <input type="text" className="main-input" value={sortList[sorting]}  placeholder="Сначала новые" readOnly />
                {
                    sortingVision ?
                        <ul className="select-list">
                            { 
                                sortList?.map((item: string, index: number) => 
                                    <li className="select-list_item" key={item} onMouseDown={() => {
                                        setSorting(index)
                                        props.sortingEvent(index);
                                    }}>{item}</li>
                                ) 
                            }
                        </ul> :
                        null
                }
            </label>
            <label className="select-label" onFocus={() => setStatusVision(!statusVision)} onBlur={() => setStatusVision(false)}>
                Статус
                <input type="text" className="main-input" value={statusesList[status === null ? 0 : status + 1]}  placeholder="Все" readOnly />
                {
                    statusVision ?
                        <ul className="select-list">
                            { 
                                statusesList?.map((item: string, index: number) => 
                                    <li className="select-list_item" key={item} onMouseDown={() => {
                                        index-1 === -1 ? setStatus(null) : setStatus(index-1)
                                    }}>{item}</li>
                                ) 
                            }
                        </ul> :
                        null
                }
            </label>
            <label className="select-label" onFocus={() => setSubjectVision(!subjectVision)} onBlur={() => setSubjectVision(false)}>
                Тема обращения
                <input type="text" className="main-input" value={themesList?.find((t: ThemeResponse) => t.id === subject)?.name}  placeholder="Все" readOnly />
                {
                    subjectVision ?
                        <ul className="select-list">
                            { 
                                themesList?.map((item: ThemeResponse, index: number) => 
                                    <li className="select-list_item" key={item.id} onMouseDown={() => setSubject(item.id)}>{item.name}</li>
                                ) 
                            }
                        </ul> :
                        null
                }
            </label>
            <label>
                Дата создания
                <input type="date" className="main-input" {...date} placeholder="Дата" />
            </label>
            {
                location.hash.slice(2,7) === 'admin' ?
                    <React.Fragment>
                        <label>
                            Организация
                            <input type="text" className="main-input" {...orgName} placeholder="Название" />
                        </label>
                        <label className="select-label" onFocus={() => setExecutorVision(!executorVision)} onBlur={() => setExecutorVision(false)}>
                            Исполнитель
                            <input type="text" className="main-input" value={`${executorsList?.find((t: EmployeeResponse) => t.id === executorId)?.firstName} ${executorsList?.find((t: EmployeeResponse) => t.id === executorId)?.lastName}`} readOnly />
                            {
                                executorVision ?
                                    <ul className="select-list">
                                        { 
                                            executorsList?.map((item: EmployeeResponse, index: number) => 
                                                <li className="select-list_item" key={item.id} onMouseDown={() => setExecutorId(item.id)}>{`${item.firstName} ${item.lastName}`}</li>
                                            ) 
                                        }
                                    </ul> :
                                    null
                            }
                        </label>
                    </React.Fragment> :
                    null
            }
            <button className="common-btn" onClick={() => emitFilterEvent()}>Обновить</button>
        </div>
    );
}

export default Filter;
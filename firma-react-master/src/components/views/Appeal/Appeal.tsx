import React, { useState, useEffect } from 'react';
import './Appeal.scss';
import { useHistory, useParams } from 'react-router';
import { AxiosResponse } from 'axios';
import ArrowLeftIcon from '../../../assets/images/icons/arrow-left.svg';
import AppealsAPI from '../../../api/appeals';
import { AppealItemClientModel, AppealItemModel, TaskStatusEnum } from '../../../api/models/response/appeals-response.model';
import Moment from 'react-moment';
import ModalView from '../ModalView/ModalView';
import ExecutorAppoint from '../ModalView/ExecutorAppoint/ExecutorAppoint';
import TasksAPI from '../../../api/tasks';
import Report from '../ModalView/Report/Report';

const Appeal = (props:any) => {

    const { id } = useParams<{id: string}>();
    const history = useHistory()

    const [isAdmin, setIsAdmin] = useState(false)

    const [appeal, setAppeal] = useState<AppealItemClientModel>(new AppealItemClientModel())

    const [req, doReq] = useState(false)

    const [executorModal, toggleExecutorModal] = useState(false)
    const [reportModal, toggleReportModal] = useState(false)

    const [reportIsApprove, toggleReportIsApprove] = useState(false)

    const appealStatus:string[] = ['Создано', 'На рассмотрении', 'Выполнено', 'Отклонено']
    const appealStatusClasses:string[] = ['not-proccessing-status', 'proccessing-status', 'complete-status', 'reject-status']

    const getAppealById = () => {
        doReq(true)
        AppealsAPI.getAppealById(+id)
            .then((response:AxiosResponse<AppealItemClientModel>) => {
                response.data.createDate = new Date((response.data.createDate as string).slice(0,19))
                response.data.report ? response.data.report.createDate = new Date((response.data.report.createDate as string)?.slice(0,19)) : null
                setAppeal(response.data)
            })
            .catch(err => console.log(err))
    }

    const takeTask = () => {
        TasksAPI.takeTask(appeal.id)
            .then((response:AxiosResponse<void>) => {
                getAppealById()
            })
            .catch(err => console.log(err))
    }

    const check = (status: number) => {
        AppealsAPI.checkAppeal(appeal.id, status)
            .then((response:AxiosResponse<any>) => {
                getAppealById();
            })
            .catch(err => console.log(err))
    }

    const sendReport = (isApprove: boolean) => {
        toggleReportIsApprove(isApprove)
        setTimeout(() => {
            toggleReportModal(true);
        }, 0);
    }

    const renderSwitchCase = () => {
        if(!appeal.lastTask.isOver && !appeal.lastTask.employeeId && appeal.statusAppeal < 2) {
            return (
                <React.Fragment>
                    <button className="common-btn" onClick={() => takeTask()}>ВЗЯТЬ В РАБОТУ</button>
                    <button className="common btn transparent-btn" onClick={() => toggleExecutorModal(!executorModal)}>НАЗНАЧИТЬ ИСПОЛНИТЕЛЯ</button>
                </React.Fragment>
            )
        } else {
            switch(appeal.lastTask.taskStatus) {
                case TaskStatusEnum.NEEDCHECK:
                    return <React.Fragment>
                        <button className="common-btn execute-btn" onClick={() => check(3)}>ВЫПОЛНИТЬ</button>
                        <button className="common-btn reject-btn"  onClick={() => check(2)}>ОТКЛОНИТЬ</button>
                        <button className="common-btn transparent-btn" onClick={() => check(1)}>НЕОБХОДИМО ИЗМЕНИТЬ</button>
                    </React.Fragment>
                case TaskStatusEnum.NEEDUPDATE:
                    return <button className="common-btn transparent-btn" onClick={() => history.push(`${isAdmin ? '/admin' : ''}/edit-appeal/${id}`)}>ИЗМЕНИТЬ</button>
                case TaskStatusEnum.NEEDREJECT:
                    return <button className="common-btn transparent-btn" onClick={() => sendReport(false)}>НАПИСАТЬ ОТЧЕТ ОБ ОТКЛОНЕНИИ</button>
                case TaskStatusEnum.NEEDSUCCESS:
                    return <button className="common-btn transparent-btn" onClick={() => sendReport(true)}>НАПИСАТЬ ОТЧЕТ ОБ УСПЕШНОМ ЗАВЕРШЕНИИ</button>
                default:
                    return <React.Fragment></React.Fragment>
            }
        }
    }

    useEffect(() => {
        setIsAdmin(location.hash.slice(2,7) === 'admin')
        !req ? getAppealById() : null
    })

    return (
        <div className="appeal-component">
            <button className="back-btn" onClick={() => history.goBack()}><img src={ArrowLeftIcon} alt="" /> Назад</button>
            <h1>Обращение</h1>
            {
                isAdmin ?
                    <div className="common-controls">
                        {
                            renderSwitchCase()
                        }
                    </div> :
                    null
            }
            {
                executorModal ?
                    <ModalView closeEvent={() => toggleExecutorModal(false)}>
                        <ExecutorAppoint toggleExecutorModal={() => {
                            toggleExecutorModal(!executorModal)
                            getAppealById()
                        }}></ExecutorAppoint>
                    </ModalView> :
                    null
            }
            {
                reportModal ?
                    <ModalView closeEvent={() => toggleReportModal(false)}>
                        <Report isApprove={reportIsApprove} toggleReportModal={() => {
                            toggleReportModal(!reportModal)
                            getAppealById()
                        }}></Report>
                    </ModalView> :
                    null
            }
            <span className={"status " + appealStatusClasses[appeal?.statusAppeal]}>{ appealStatus[appeal?.statusAppeal] }</span>
            {
                appeal?.report && appeal.report.id !== null ?
                    <div className="response-container">
                        <h5>Ответ</h5>
                        <span className="creation-date">Создано: <Moment format="DD.MM.YYYY hh:mm">{ appeal?.report.createDate }</Moment></span>
                        <p>
                            { appeal.report.text }
                        </p>                
                    </div> :
                    null
            }
            <span className="creation-date">Создано: <Moment format="DD.MM.YYYY hh:mm">{ appeal?.createDate }</Moment></span>
            <h5>{ appeal?.theme.name.slice(0,65) }</h5>
            <p>
                { appeal?.description }
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
                    Срок реализации проекта <span className="data-item_value"><Moment format="DD.MM.YYYY">{ appeal?.startDate }</Moment></span>
                </li>
                <li className="data-item">
                    Вид категории затрат <span className="data-item_value">{ appeal?.costCat.name }</span>
                </li>
                <li className="data-item">
                    ТН ВЭД <span className="data-item_value">{ appeal?.tnved.code }</span>
                </li>
                <li className="data-item">
                    Сумма расходов по заявке <span className="data-item_value">{ appeal?.amount } р.</span>
                </li>
            </ul>
        </div>
    )
}

export default Appeal;
import { AxiosResponse } from 'axios';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { EmployeeResponse } from '../../../../api/models/response/employee-response.model';
import ReportsAPI from '../../../../api/report';
import StaffsAPI from '../../../../api/staffs';
import TasksAPI from '../../../../api/tasks';
import useFormState from '../../../../common/customHooks/useFormState';
import './Report.scss';

const Report = (props:{isApprove: boolean, toggleReportModal: () => void}) => {

    const { id } = useParams<{id: string}>();

    const report = useFormState("")

    const [req, doReq] = useState(false)

    const sendReport = () => {
        ReportsAPI.approveOrReject(props.isApprove, +id, report.value)
            .then((response:AxiosResponse<void>) => {
                props.toggleReportModal()
            })
            .catch(err => console.log(err))
    }

    return (
        <div className="reports-container">
            <h5>ВЫПОЛИТЬ ОБРАЩЕНИЕ</h5>
            <h6>ОТЧЕТ</h6>
            <textarea name="" cols={30} rows={10} {...report}></textarea>
            <button className="common-btn" onClick={() => sendReport()}>ВЫПОЛНИТЬ</button>
        </div>
    )
}

export default Report;
import { AxiosResponse } from 'axios';
import React from 'react';
import { useParams } from 'react-router';
import ReportsAPI from '../../../../api/report';
import useFormState from '../../../../common/customHooks/useFormState';
import './Report.scss';

const Report = (props:{isApprove: boolean, toggleReportModal: () => void}) => {

    const { id } = useParams<{id: string}>();

    const report = useFormState("")

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
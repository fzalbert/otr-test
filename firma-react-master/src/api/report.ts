import { ReportResponse } from "./models/response/report-response.model"
import axios, { AxiosResponse } from "axios"
import { environment } from "./environments/environment"

const ReportsAPI = {
    approveOrReject: (isApprove: boolean, appealId: number, text: string):Promise<AxiosResponse<void>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/reports/approve-or-reject`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                isApprove: isApprove,
                appealId: appealId,
                text: text
            }
        })
    },
    getReportById: (Id: number):Promise<AxiosResponse<ReportResponse>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/reports/by-id`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                Id: Id
            }
        })
    },
    getReportList: ():Promise<AxiosResponse<ReportResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/reports/list`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    },
    getReportByStatus: (status: string):Promise<AxiosResponse<ReportResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/reports/by-status`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params:{
                status: status
            }
        })
    },


}

export default ReportsAPI;
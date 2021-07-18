import { EmployeeResponse, ExecutorResponse } from "./models/response/employee-response.model"
import { EmployeeRequest } from "./models/request/employee-request.model"

import axios, { AxiosResponse } from "axios"
import { environment } from "./environments/environment"

const StaffsAPI = {
    getAllEmployees: ():Promise<AxiosResponse<EmployeeResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}employee/api/employee/list`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    },
    getEmployeeById: (clientId: number):Promise<AxiosResponse<EmployeeResponse>> => {
        return axios.get(`${environment.apiEndPoint}employee/api/employee/by-id`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                id: clientId
            }
        })
    },
    deleteEmployeeById: (clientId: number):Promise<AxiosResponse<boolean>> => {
        return axios.delete(`${environment.apiEndPoint}employee/api/employee/delete`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                clientId: clientId
            }
        })
    },
    unblockById: (employeeId: number):Promise<AxiosResponse<void>> => {
        return axios.get(`${environment.apiEndPoint}employee/api/employee/unblock`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                id: employeeId
            }
        })
    },
    blockById: (employeeId: number):Promise<AxiosResponse<void>> => {
        return axios.get(`${environment.apiEndPoint}employee/api/employee/block`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                id: employeeId
            }
        })
    },
    create: (employeeData: EmployeeRequest):Promise<AxiosResponse<EmployeeResponse>> => {
        return axios.post(`${environment.apiEndPoint}employee/api/employee/create`, employeeData, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    },
    update: (employeeId: number, employeeData: EmployeeRequest):Promise<AxiosResponse<EmployeeResponse>> => {
        return axios.post(`${environment.apiEndPoint}employee/api/employee/update`, employeeData, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                employeeId: employeeId
            }
        })
    },
}
export default StaffsAPI;
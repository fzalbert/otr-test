import axios, { AxiosResponse } from "axios"
import { environment } from "./environments/environment"

const TasksAPI = {
    appoint: (employeeId: number, appealId: number):Promise<AxiosResponse<void>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/tasks/appoint`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                employeeId: employeeId,
                appealId: appealId
            }
        })
    },
    takeTask: (appealId: number):Promise<AxiosResponse<void>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/tasks/take`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                appealId: appealId
            }
        })
    },


}

export default TasksAPI;
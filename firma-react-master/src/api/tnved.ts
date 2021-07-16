import { TnvedResponse } from "./models/response/tnved-response.model"
import axios, { AxiosResponse } from "axios"
import { environment } from "./environments/environment"

const TnvedAPI = {
    getAllTnved: ():Promise<AxiosResponse<TnvedResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/tnved/list`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    },
    getTnvedById: (tnvedId: number):Promise<AxiosResponse<TnvedResponse>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/tnved/by-id`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                tnvedId: tnvedId
            }
        })
    },


}

export default TnvedAPI;
import { FilterRequest } from './models/request/filter-body-request.model';
import { AppealItemModel, AppealItemClientModel, TaskStatusEnum } from './models/response/appeals-response.model';
import axios, { AxiosResponse } from "axios"
import { environment } from "./environments/environment"

const AppealsAPI = {
    getAllAppeals: ():Promise<AxiosResponse<AppealItemModel[]>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/appeals/short-list`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    },
    getClientAppeals: (clientId: number):Promise<AxiosResponse<AppealItemClientModel[]>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/appeals/by-client-id`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                clientId: clientId
            }
        })
    },
    getFilterForAdmin: (req: FilterRequest):Promise<AxiosResponse<AppealItemModel[]>> => {
        return axios.post(`${environment.apiEndPoint}appeal/api/appeals/filter-for-admin`, req, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    },
    getFilterForClient: (req: FilterRequest):Promise<AxiosResponse<AppealItemClientModel[]>> => {
        return axios.post(`${environment.apiEndPoint}appeal/api/appeals/filter-for-client`, req, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    },
    getAppealById: (id: number):Promise<AxiosResponse<AppealItemClientModel>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/appeals/by-id`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                id: id
            }
        })
    },
    createAppeal: (formData:FormData):Promise<AxiosResponse<AppealItemClientModel>> => {
        return axios.post(`${environment.apiEndPoint}appeal/api/appeals/create`, formData,{
            headers: {
                'Authorization' : localStorage.getItem('token'),
                'content-type': 'multipart/form-data'
            }
        })
    },
    updateAppeal: (appealId: number, body: any):Promise<AxiosResponse<AppealItemClientModel>> => {
        return axios.post(`${environment.apiEndPoint}appeal/api/appeals/update`, body,{
            headers: {
                'Authorization' : localStorage.getItem('token')
            },
            params: {
                'appeal-id': appealId
            }
        })
    },
    updateMyAppeal: (appealId: number, body: any):Promise<AxiosResponse<AppealItemClientModel>> => {
        return axios.post(`${environment.apiEndPoint}appeal/api/appeals/update-my`, body,{
            headers: {
                'Authorization' : localStorage.getItem('token')
            },
            params: {
                'appeal-id': appealId
            }
        })
    },
    checkAppeal: (id: number, status: TaskStatusEnum):Promise<AxiosResponse<void>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/appeals/check`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                id: id,
                status: status
            }
        })
    },
    getCatCostList: ():Promise<AxiosResponse<{id: number, name: string}[]>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/catcost/list`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    },
    uploadAppealPhotos: (appealId: number, body: any):Promise<AxiosResponse<void>> => {
        return axios.post(`${environment.apiEndPoint}appeal/api/files/upload`, body,{
            headers: {
                'Authorization' : localStorage.getItem('token')
            },
            params: {
                'appeal-id': appealId
            }
        })
    },
    downloadAppealFile: (fileId: number):Promise<AxiosResponse<Blob>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/files/download`, {
            responseType: 'blob',
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                'id': fileId
            }
        })
    },

}

export default AppealsAPI;
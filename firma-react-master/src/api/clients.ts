import { ClientDetailsResponse, ClientResponse } from "./models/response/client-response.model"
import { ClientRequest } from "./models/request/client-request.model"
import axios, { AxiosResponse } from "axios"
import { environment } from "./environments/environment"

const ClientsAPI = {
    getAllClients: ():Promise<AxiosResponse<ClientResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}client/api/clients/list`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    },
    getClientById: (clientId: number):Promise<AxiosResponse<ClientDetailsResponse>> => {
        return axios.get(`${environment.apiEndPoint}client/api/clients/by-id`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                id: clientId
            }
        })
    },
    deleteClientById: (clientId: number):Promise<AxiosResponse<boolean>> => {
        return axios.delete(`${environment.apiEndPoint}client/api/clients/delete`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
               id: clientId
            }
        })
    },
    unblockById: (clientId: number):Promise<AxiosResponse<void>> => {
        return axios.get(`${environment.apiEndPoint}client/api/ban/unblock`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                id: clientId
            }
        })
    },
    blockById: (clientId: number):Promise<AxiosResponse<void>> => {
        return axios.get(`${environment.apiEndPoint}client/api/ban/block`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                id: clientId
            }
        })
    },
    update: (clientData: ClientRequest):Promise<AxiosResponse<ClientRequest>> => {
        return axios.put(`${environment.apiEndPoint}api/clients/update`, clientData, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    },
    put: (newPassword: string, clientId: number):Promise<AxiosResponse<boolean>> => {
        return axios.put(`${environment.apiEndPoint}api/clients/update`, {
            newPassword: newPassword,
            clientId: clientId
        }, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    }

}

export default ClientsAPI;
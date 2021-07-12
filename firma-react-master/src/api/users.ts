import { ClientModel, UserModel } from './models/user.model';
import { environment } from './environments/environment';
import axios, { AxiosResponse } from 'axios';

const UsersAPI = {

    getUsers: (pageSize: number, pageNumber: number, search:string, status: number):Promise<AxiosResponse<UserModel[]>> => {
        return axios.get(`${environment.apiEndPoint}/AdminProfile/list`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                PageSize: pageSize,
                PageNumber: pageNumber,
                search: search,
                userStatus: status
            }
        });
    },

    getUserById: (id: number):Promise<AxiosResponse<UserModel>> => {
        return axios.get(`${environment.apiEndPoint}/AdminProfile/id`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                id: id
            }
        });
    },

    updateUser: (userId: number, form:FormData):Promise<AxiosResponse<UserModel>> => {
        return axios.post(`${environment.apiEndPoint}/AdminProfile/update`, form, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: { 
                userId: userId
            }
        });
    },

    updateClientData: (userId: number, clientData:any):Promise<AxiosResponse<ClientModel>> => {
        return axios.post(`${environment.apiEndPoint}/AdminProfile/update-client`, clientData, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: { 
                clientId: userId
            }
        });
    },

    comment: (userId: number, text:string):Promise<AxiosResponse<ClientModel>> => {
        return axios.post(`${environment.apiEndPoint}/AdminProfile/comment`, {}, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: { 
                userId: userId,
                text: text
            }
        });
    },

    blockUser: (userId: number, block: boolean):Promise<AxiosResponse<UserModel>> => {
        return axios.get(`${environment.apiEndPoint}/AdminProfile/block-user`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: { 
                id: userId,
                block: block
            }
        });
    },

    deleteUser: (userId: number):Promise<AxiosResponse<UserModel[]>> => {
        return axios.delete(`${environment.apiEndPoint}/AdminProfile/delete/${userId}`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            // params: { id: userId }
        });
    },

}

export default UsersAPI;
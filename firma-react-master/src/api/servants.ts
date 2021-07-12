import { ServantsResponse } from './models/response/servants-response.model';
import { environment } from './environments/environment';
import axios, { AxiosResponse } from 'axios';

const ServantsAPI = {

    getServants: (subCategoryId:number, pageSize: number, pageNumber: number, parentId: number | undefined, search: string):Promise<AxiosResponse<ServantsResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}/Category/list-servant/${subCategoryId}`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: { 
                parentId: 0,
                search: search,
                subCategoryId: subCategoryId,
                PageSize: pageSize,
                PageNumber: pageNumber
            } 
        });
    },

    createServant: (form: FormData):Promise<AxiosResponse> => {
        return axios.post(`${environment.apiEndPoint}/AdminCategories/create-servant`, form, {
            headers: {
                'Content-Type': 'multipart/form-data',
                'Authorization' : localStorage.getItem('token'),
            }
        });
    },
    
    changeServant: (servantId:number, form: FormData):Promise<AxiosResponse> => {
        return axios.post(`${environment.apiEndPoint}/AdminCategories/update-servant/${servantId}`, form, {
            headers: {
                'Content-Type': 'multipart/form-data',
                'Authorization' : localStorage.getItem('token'),
            },
            params: { id: servantId } 
        });
    },
    
    deleteServant: (servantId:number):Promise<AxiosResponse> => {
        return axios.delete(`${environment.apiEndPoint}/AdminCategories/delete-servant/${servantId}`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            // params: { id: servantId } 
        });
    }
}

export default ServantsAPI;
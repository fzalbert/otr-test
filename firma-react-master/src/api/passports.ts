import { PassportResponse } from './models/response/passport-response.model';
import { environment } from './environments/environment';
import axios, { AxiosResponse } from 'axios';

const PassportsAPI = {

    getPassports: (pageSize: number, pageNumber: number, passportType?: number):Promise<AxiosResponse<PassportResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}/AdminPassport/list`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                PageSize: pageSize,
                PageNumber: pageNumber,
                passportType: passportType
            }
        });
    },

    getPassport: (id:number):Promise<AxiosResponse<PassportResponse>> => {
        return axios.get(`${environment.apiEndPoint}/AdminPassport/id`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                id: id
            }
        });
    },

    verifyClient: (passportId:number, verify:boolean, deadline: string, type: number):Promise<AxiosResponse<PassportResponse>> => {
        return axios.post(`${environment.apiEndPoint}/AdminPassport/verify/${passportId}`, {
            verify: verify,
            dateDeadline: deadline,
            type: type
        }, {
            params: {
                id: passportId            },
            headers: {
                'Authorization' : localStorage.getItem('token')
            }
        })
    }

}

export default PassportsAPI;
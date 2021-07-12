import { environment } from './environments/environment';
import axios, { AxiosResponse } from 'axios';


export const AboutAPI = {
    getAboutText: ():Promise<AxiosResponse<{id: number, description: string}>> => {
        return axios.get(`${environment.apiEndPoint}/AdminCategories/desc`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    },
    updateText: (text: string):Promise<AxiosResponse<{id: number, description: string}>> => {
        return axios.post(`${environment.apiEndPoint}/AdminCategories/update-desc`, {}, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                text: text
            }
        })
    }
}
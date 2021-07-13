import { environment } from './environments/environment';
import axios, { AxiosResponse } from 'axios';
import { City } from './models/response/task-response.model';


const CitiesAPI = {
    getCitiesList: (search:string):Promise<AxiosResponse<City[]>> => {
        return axios.get(`${environment.apiEndPoint}/Account/list-city`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                search: search
            }
        })
    }
}

export default CitiesAPI;
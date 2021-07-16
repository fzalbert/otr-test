import { ThemeResponse } from "./models/response/theme-response.model"
import axios, { AxiosResponse } from "axios"
import { environment } from "./environments/environment"

const ThemeAPI = {
    create: (name: string):Promise<AxiosResponse<ThemeResponse>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/theme/create`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                name: name
            }
        })
    },
    getAllThemes: ():Promise<AxiosResponse<ThemeResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/theme/list`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    },
    getThemeById: (themeId: number):Promise<AxiosResponse<ThemeResponse>> => {
        return axios.get(`${environment.apiEndPoint}appeal/api/theme/by-id`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        })
    },


}

export default ThemeAPI;
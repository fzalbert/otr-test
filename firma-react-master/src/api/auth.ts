import { RegistrationRequest } from './models/request/registration-request.model';
import { AuthResponse, RegistrationResponse } from './models/response/auth-response.model';
import axios, { AxiosResponse } from "axios"
import { environment } from "./environments/environment"

export const auth = (login:string, password:string):Promise<AxiosResponse<AuthResponse>> => {
    return axios.post(`${environment.apiEndPoint}auth/api/login/employee`, {
        username: login,
        password: password
    })
}

export const authClient = (login:string, password:string) => {
    return axios.post(`${environment.apiEndPoint}auth/api/login/client`, {
        username: login,
        password: password
    })
}

export const register = (person:RegistrationRequest):Promise<AxiosResponse<RegistrationResponse>> => {
    return axios.post(`${environment.apiEndPoint}client/api/account/register`, {
        ...person
    })
}
import { UserModel } from './../user.model';
import { ResponseModel } from "./response.model";

export interface AuthResponse {
    user: UserModel;
    token: {token:string, expireDate:string};
}

export interface RegistrationResponse {

}

export interface Person {
    
}
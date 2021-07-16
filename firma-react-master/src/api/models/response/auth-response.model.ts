import { UserModel } from './../user.model';
import { ResponseModel } from "./response.model";

export interface AuthResponse {
    // user: UserModel;
    // token: {token:string, expireDate:string};
    
}

export class RegistrationResponse {
    constructor() {}
    public email: string="";
    public fio: string="";
    public fullAddress: string="";
    public fullNameOrg: string="";
    public inn: string="";
    public kpp: string="";
    public login: string="";
    public password: string="";
    public shortNameOrg: string="";
}

export interface Person {
    
}
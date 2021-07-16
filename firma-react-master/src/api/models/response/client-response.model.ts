import { RegistrationRequest } from './../request/registration-request.model';

export interface ClientResponse {
    id: number;
    fio: string;
    email: string;
    fullNameOrg: string;
    isActive: boolean;
}

export interface ClientDetailsResponse extends RegistrationRequest {
    id: number;
}
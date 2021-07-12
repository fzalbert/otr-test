import { UserModel } from './../user.model';

export interface PassportResponse {
    passportId: number;
    user: UserModel;
    statusConfirm: number;
    photos: string[];
    date: Date;
    dateDeadline: string;
    type: number;
}
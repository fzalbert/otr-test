import { UserModel } from './../../api/models/user.model';
export interface SetUsersAction extends UserModel {}

export interface BlockUserAction {
    userId: number;
    isActive: boolean;
}

export interface DeleteUserAction {
    userId: number;
}
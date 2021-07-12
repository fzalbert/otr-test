import { SetUsersAction, DeleteUserAction, BlockUserAction } from './../models/users-action.model';
import actionsConstants from "../models/actions-constants";

export const setUsers = (action:SetUsersAction[]) => {
    return ({
        type: actionsConstants.SET_USERS,
        data: action
    })
}

export const blockUser = (action:BlockUserAction) =>({
    type: actionsConstants.BLOCK_USER,
    ...action
})

export const setDeleteUser = (action:DeleteUserAction) =>({
    type: actionsConstants.DELETE_USER,
    ...action
})
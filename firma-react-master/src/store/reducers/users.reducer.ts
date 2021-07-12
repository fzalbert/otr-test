import actionsConstants from '../models/actions-constants';
import { SetUsersAction } from '../models/users-action.model';

export const UsersReducer = (
        state:SetUsersAction[]=[],
        action:any
    ) => {
        switch (action.type) {
            case actionsConstants.SET_USERS:
                return action.data // don't copy previous state into array
            case actionsConstants.BLOCK_USER:
                return state.map(item => item.id === action.userId ? {...item, isActive: !item.isActive} : item)
            case actionsConstants.DELETE_USER:
                return state.filter(item => item.id != action.userId)
            default:
                return state;
        }
}
import actionsConstants from "../models/actions-constants";
import { setStaffsAction } from '../models/staffs-action.model';

export const StaffsReducer = (
        state:setStaffsAction[] = [],
        action:any
    ) => {
        switch (action.type) {
            case actionsConstants.SET_STAFFS_LIST:
                return action.data // don't copy previous state into array
            default:
                return state;
        }
}
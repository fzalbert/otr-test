import { setStaffsAction } from './../models/staffs-action.model';
import actionsConstants from '../models/actions-constants';

export const setStaffsList = (action:setStaffsAction[]) => {
    return ({
        type: actionsConstants.SET_STAFFS_LIST,
        data: action
    })
}
import { setTnvedAction } from './../models/tnved-action.model';
import actionsConstants from '../models/actions-constants';

export const setTnvedList = (action:setTnvedAction[]) => {
    return ({
        type: actionsConstants.SET_TNVED_LIST,
        data: action
    })
}
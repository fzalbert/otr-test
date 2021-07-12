import { changeServantAction, createServantAction, setServantsAction } from './../models/servants-action.model';
import actionsConstants from "../models/actions-constants";
import { deleteServantAction } from '../models/servants-action.model';

export const setServants = (action:setServantsAction[]) => {
    return ({
        type: actionsConstants.SET_SERVANTS,
        data: action
    })
}

export const createServant = (action:createServantAction) => ({
    type: actionsConstants.CREATE_SERVANT,
    ...action
})

export const changeServant = (action:changeServantAction) => ({
    type: actionsConstants.CHANGE_SERVANT,
    ...action
})

export const deleteServant = (action:deleteServantAction) =>({
    type: actionsConstants.DELETE_SERVANT,
    ...action
})
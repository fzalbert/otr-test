import { setAppealsListAction, setClientAppealsAction } from './../models/appeals-action.model';
import actionsConstants from '../models/actions-constants';

export const setAppealsList = (action:setAppealsListAction[]) => {
    return ({
        type: actionsConstants.SET_APPEALS_LIST,
        data: action
    })
}

export const setClientAppealsList = (action:setClientAppealsAction[]) => {
    return ({
        type: actionsConstants.SET_CLIENT_APPEALS_LIST,
        data: action
    })
}

export const setSortList = (action:number) => {
    return ({
        type: actionsConstants.SORT_APPEALS_LIST,
        data: action
    })
}
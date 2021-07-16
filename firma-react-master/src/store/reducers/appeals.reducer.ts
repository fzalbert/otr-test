import { setAppealsListAction, setClientAppealsAction } from './../models/appeals-action.model';
import actionsConstants from "../models/actions-constants";

export const AppealsReducer = (
        state:setAppealsListAction[] | setClientAppealsAction[] = [],
        action:any
    ) => {
        switch (action.type) {
            case actionsConstants.SET_APPEALS_LIST:
                return action.data // don't copy previous state into array
            case actionsConstants.SET_CLIENT_APPEALS_LIST:
                return action.data // don't copy previous state into array
            case actionsConstants.SORT_APPEALS_LIST:
                return [...state.sort((a,b) => !action.data ? +new Date(b.createDate) - +new Date(a.createDate) : +new Date(a.createDate) - +new Date(b.createDate))] // don't copy previous state into array
            // case actionsConstants.CREATE_CATEGORY:
            //     return [
            //         ...state,
            //         CategoryReducer({id: 1, name: '', imageUrl: '', parentId: NaN, countTasks: NaN}, action)
            //     ]
            // case actionsConstants.CHANGE_CATEGORY:
            //     return state.map(item => CategoryReducer(item, action))
            // case actionsConstants.DELETE_CATEGORY:
            //     return state.filter(item => item.id != action.id)
            default:
                return state;
        }
}
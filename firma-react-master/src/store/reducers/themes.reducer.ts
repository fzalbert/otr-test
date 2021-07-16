import { setThemesAction } from './../models/themes-action.model';
import actionsConstants from "../models/actions-constants";

export const ThemesReducer = (
        state:setThemesAction[] = [],
        action:any
    ) => {
        switch (action.type) {
            case actionsConstants.SET_THEMES_LIST:
                return action.data // don't copy previous state into array
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
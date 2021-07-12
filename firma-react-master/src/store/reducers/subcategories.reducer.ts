import { setSubCategoryAction } from './../models/subcategories-action.model';
import { SubCategoryReducer } from './subcategory.reducer';
import actionsConstants from "../models/actions-constants";

export const SubCategoriesReducer = (
        state:setSubCategoryAction[]=[],
        action:any
    ) => {
        switch (action.type) {
            case actionsConstants.SET_SUBCATEGORIES:
                return action.data // don't copy previous state into array
            case actionsConstants.CREATE_SUBCATEGORY:
                return [
                    ...state,
                    SubCategoryReducer({id: 1, name: '', imageUrl: '', countTasks: NaN, parentId: NaN}, action)
                ]
            case actionsConstants.CHANGE_SUBCATEGORY:
                return state.map(item => SubCategoryReducer(item, action))
            case actionsConstants.DELETE_SUBCATEGORY:
                return state.filter(item => item.id != action.id)
            default:
                return state;
        }
}
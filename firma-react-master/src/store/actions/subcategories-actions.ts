import { setSubCategoryAction, createSubCategoryAction, changeSubCategoryAction, deleteSubCategoryAction } from '../models/subcategories-action.model';
import actionsConstants from "../models/actions-constants";

export const setSubCategories = (action:setSubCategoryAction[]) => {
    return ({
        type: actionsConstants.SET_SUBCATEGORIES,
        data: action
    })
}

export const createSubCategory = (action:createSubCategoryAction) => ({
    type: actionsConstants.CREATE_SUBCATEGORY,
    ...action
})

export const changeSubCategory = (action:changeSubCategoryAction) => ({
    type: actionsConstants.CHANGE_SUBCATEGORY,
    ...action
})

export const deleteSubCategory = (action:deleteSubCategoryAction) =>({
    type: actionsConstants.DELETE_SUBCATEGORY,
    ...action
})
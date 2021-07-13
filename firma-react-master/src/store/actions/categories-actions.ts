import { setCategoryAction, createCategoryAction, changeCategoryAction, deleteCategoryAction } from '../models/categories-action.model';
import actionsConstants from '../models/actions-constants';

export const setCategories = (action:setCategoryAction[]) => {
    return ({
        type: actionsConstants.SET_CATEGORIES,
        data: action
    })
}

export const createCategory = (action:createCategoryAction) => ({
    type: actionsConstants.CREATE_CATEGORY,
    ...action
})

export const changeCategory = (action:changeCategoryAction) => ({
    type: actionsConstants.CHANGE_CATEGORY,
    ...action
})

export const deleteCategory = (action:deleteCategoryAction) =>({
    type: actionsConstants.DELETE_CATEGORY,
    ...action
})
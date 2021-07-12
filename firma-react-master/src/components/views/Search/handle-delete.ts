import { useHistory } from 'react-router';
import { useDispatch } from 'react-redux';
import { AxiosResponse } from 'axios';
import SubCategoriesAPI from "../../../api/subcategories"
import {deleteSubCategory as buildDeleteSubCategory} from '../../../store/actions/subcategories-actions'
import {deleteCategory as buildDeleteCategory} from '../../../store/actions/categories-actions'
import {deleteServant as buildDeleteServant} from '../../../store/actions/servants-actions'
import CategoriesAPI from '../../../api/categories';
import ServantsAPI from '../../../api/servants';

const deleteSubCategory = (id: number, dispatch: any, history: any) => {
    CategoriesAPI.deleteCategory(id ? +id : 0)
        .then((response:AxiosResponse) => {
            dispatch(buildDeleteSubCategory({id: id ? +id : 0}))
        })
        .catch(err => console.log(err))
    // SubCategoriesAPI.deleteSubCategory(id)
    //     .then((response:AxiosResponse) => {
    //         dispatch(buildDeleteSubCategory({id: id ? +id : 0}))
    //     })
    //     .catch(err => console.log(err))
}

const deleteCategory = (id: number, dispatch: any, history: any) => {
    CategoriesAPI.deleteCategory(id ? +id : 0)
        .then((response:AxiosResponse) => {
            dispatch(buildDeleteCategory({id: id ? +id : 0}))
        })
        .catch(err => console.log(err))
}

const deleteServant = (id: number, dispatch: any, history: any) => {
    ServantsAPI.deleteServant(id ? +id : 0)
        .then((response:AxiosResponse) => {
            dispatch(buildDeleteServant({id: id ? +id : 0}))
        })
        .catch(err => console.log(err))
}

export const handleDelete = (id: number, searchType: number, dispatch: any, history: any) => {
    switch (searchType) {
        case 0:
            deleteCategory(id, dispatch, history)
            break;
        case 1:
            deleteSubCategory(id, dispatch, history)
            break;
        case 2:
            deleteServant(id, dispatch, history)
            break;
        default:
            break;
    }
}
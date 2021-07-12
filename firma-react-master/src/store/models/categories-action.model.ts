import { CategoriesResponse } from './../../api/models/response/categories-response.model';

export interface setCategoryAction extends CategoriesResponse {}

export interface createCategoryAction {
    id: number;
    name: string;
    imageUrl:string;
    parentId: number;
    countTasks: number;
}

// export interface createCategoryAction {
//     id: number;
//     name: string;
//     imageUrl:string;
//     countTasks: number;
// }

export interface changeCategoryAction {
    id: number;
    name: string;
}

export interface deleteCategoryAction {
    id: number;
}
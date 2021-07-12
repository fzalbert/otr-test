import { SubCategoriesResponse } from "../../api/models/response/sub-categories-response.model";

export interface setSubCategoryAction extends SubCategoriesResponse {}

export interface createSubCategoryAction extends SubCategoriesResponse {}

export interface changeSubCategoryAction {
    id: number;
    name: string;
    parentId: number;
}

// export interface changeSubCategoryAction {
//     id: number;
//     name: string;
//     categoryId: number;
// }

export interface deleteSubCategoryAction {
    id: number;
}
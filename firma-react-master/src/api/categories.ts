import { SubCategoriesResponse } from './models/response/sub-categories-response.model';
import { CategoriesResponse } from './models/response/categories-response.model';
import { environment } from './environments/environment';
import axios, { AxiosResponse } from 'axios';

const CategoriesAPI = {

    getCategories: (pageSize: number, pageNumber: number, search: string):Promise<AxiosResponse<CategoriesResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}/Category/list-category`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                PageSize: pageSize,
                PageNumber: pageNumber,
                search: search
            }
        });
    },

    getSubCategories: (pageSize: number, pageNumber: number, search: string):Promise<AxiosResponse<SubCategoriesResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}/Category/list-subs`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                PageSize: pageSize,
                PageNumber: pageNumber,
                search: search
            }
        });
    },

    getSubCategoriesById: (parentId:number, pageSize: number, pageNumber: number, search: string):Promise<AxiosResponse<SubCategoriesResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}/Category/list-subcategory/${parentId}`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                PageSize: pageSize,
                PageNumber: pageNumber,
                parentId: parentId,
                search: search
            }
        });
    },

    createCategory: (form: FormData):Promise<AxiosResponse> => {
        return axios.post(`${environment.apiEndPoint}/AdminCategories/create-category`, form, {
            headers: {
                'Content-Type': 'multipart/form-data',
                'Authorization' : localStorage.getItem('token'),
            }
        });
    },

    changeCategory: (servantId:number, form: FormData):Promise<AxiosResponse> => {
        return axios.post(`${environment.apiEndPoint}/AdminCategories/update-servant/${servantId}`, form, {
            headers: {
                'Content-Type': 'multipart/form-data',
                'Authorization' : localStorage.getItem('token'),
            },
            params: { id: servantId } 
        });
    },
    
    deleteCategory: (servantId:number):Promise<AxiosResponse> => {
        return axios.delete(`${environment.apiEndPoint}/AdminCategories/delete-servant/${servantId}`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: { id: servantId } 
        });
    }

//     getCategories: (pageSize: number, pageNumber: number, search: string):Promise<AxiosResponse<CategoriesResponse[]>> => {
//         return axios.get(`${environment.apiEndPoint}/Category/list-category`, {
//             headers: {
//                 'Authorization' : localStorage.getItem('token'),
//             },
//             params: {
//                 PageSize: pageSize,
//                 PageNumber: pageNumber,
//                 search: search
//             }
//         });
//     },
    
//     getSubCategories: (categoryId:number, pageSize: number, pageNumber: number, search: string):Promise<AxiosResponse<SubCategoriesResponse[]>> => {
//         return axios.get(`${environment.apiEndPoint}/Category/list-subcategory/${categoryId}`, {
//             headers: {
//                 'Authorization' : localStorage.getItem('token'),
//             },
//             params: {
//                 PageSize: pageSize,
//                 PageNumber: pageNumber,
//                 categoryId: categoryId,
//                 search: search
//             }
//         });
//     },
    
//     createCategory: (form: FormData):Promise<AxiosResponse> => {
//         return axios.post(`${environment.apiEndPoint}/AdminCategories/create-category`, form, {
//             headers: {
//                 'Content-Type': 'multipart/form-data',
//                 'Authorization' : localStorage.getItem('token'),
//             }
//         });
//     },
    
//     changeCategory: (categoryId:number, form: FormData):Promise<AxiosResponse> => {
//         return axios.post(`${environment.apiEndPoint}/AdminCategories/update-category/${categoryId}`, form, {
//             headers: {
//                 'Content-Type': 'multipart/form-data',
//                 'Authorization' : localStorage.getItem('token'),
//             },
//             // params: { id: categoryId } 
//         });
//     },
    
//     deleteCategory: (categoryId:number):Promise<AxiosResponse> => {
//         return axios.delete(`${environment.apiEndPoint}/AdminCategories/delete-category${categoryId}`, {
//             headers: {
//                 'Authorization' : localStorage.getItem('token'),
//             },
//             // params: { id: categoryId } 
//         });
//     }
}

export default CategoriesAPI;
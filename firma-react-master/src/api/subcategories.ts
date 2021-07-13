import { environment } from './environments/environment';
import axios, { AxiosResponse } from 'axios';

const SubCategoriesAPI = {
    
    createSubCategory: (form: FormData):Promise<AxiosResponse> => {
        return axios.post(`${environment.apiEndPoint}/AdminCategories/create-subcategory`, form, {
            headers: {
                'Content-Type': 'multipart/form-data',
                'Authorization' : localStorage.getItem('token'),
            }
        });
    },
    
    changeSubCategory: (subCategoryId:number, form: FormData):Promise<AxiosResponse> => {
        return axios.post(`${environment.apiEndPoint}/AdminCategories/update-subcategory/${subCategoryId}`, form, {
            headers: {
                'Content-Type': 'multipart/form-data',
                'Authorization' : localStorage.getItem('token'),
            },
            // params: { subCategoryId: subCategoryId } 
        });
    },
    
    deleteSubCategory: (subCategoryId:number):Promise<AxiosResponse> => {
        return axios.delete(`${environment.apiEndPoint}/AdminCategories/delete-subcategory/${subCategoryId}`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            // params: { subCategoryId: subCategoryId } 
        });
    }
}

export default SubCategoriesAPI;
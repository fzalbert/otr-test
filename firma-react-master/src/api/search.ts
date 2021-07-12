import { SearchResponse } from './models/response/search-response.model';
import { environment } from './environments/environment';
import axios, { AxiosResponse } from 'axios';

const SearchAPI = {
    getList: (pageSize: number, pageNumber: number, searchString: string, searchType:number):Promise<AxiosResponse<SearchResponse>> => {
        return axios.get(`${environment.apiEndPoint}/AdminCategories/list`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                PageSize: pageSize,
                PageNumber: pageNumber,
                searchType: searchType,
                search: searchString
            }
        })
    }
}

export default SearchAPI;
import { PackageResponse } from './models/response/package-response.model';
import { environment } from './environments/environment';
import axios, { AxiosResponse } from 'axios';

const PackagesAPI = {

    getPackages: ():Promise<AxiosResponse<PackageResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}/AdminPackage/list`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        });
    },

    getPackage: (packageId:number):Promise<AxiosResponse<PackageResponse>> => {
        return axios.get(`${environment.apiEndPoint}/AdminPackage/id`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: {
                id: packageId
            }
        });
    },

    createPackage: (packageData: PackageResponse):Promise<AxiosResponse> => {
        return axios.post(`${environment.apiEndPoint}/AdminPackage/add`, {
            name: packageData.name,
            price: packageData.price,
            countСalls: packageData.countСalls,
            packageType: packageData.packageType
        }, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            }
        });
    },
    
    changePackage: (packageId:number, packageData:{
        name: string,
        price: number,
        countСalls: number,
        packageType: number
      }):Promise<AxiosResponse<PackageResponse>> => {
        return axios.post(`${environment.apiEndPoint}/AdminPackage/update/${packageId}`, {
            name: packageData.name,
            price: packageData.price,
            countСalls: packageData.countСalls,
            packageType: packageData.packageType
        }, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            // params: { id: packageId } 
        });
    },
    
    deletePackage: (packageId:number):Promise<AxiosResponse> => {
        return axios.delete(`${environment.apiEndPoint}/AdminPackage/delete/${packageId}`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            // params: { id: packageId } 
        });
    }
}

export default PackagesAPI;
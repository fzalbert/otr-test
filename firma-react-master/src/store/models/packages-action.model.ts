import { PackageResponse } from '../../api/models/response/package-response.model';

export interface SetPackagesAction extends PackageResponse {}

export interface CreatePackageAction {
    name: string;
    price: number;
    countСalls: number;
    packageType: number;
}

export interface ChangePackageAction {
    id?: number;
    name: string;
    price: number;
    countСalls: number;
    packageType: number;
}

export interface DeletePackageAction {
    packageId: number;
}
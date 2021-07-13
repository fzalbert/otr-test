import { PackageResponse } from './response/package-response.model';
export interface UserModel {
    id: number,
    person: PersonModel,
    avatar: string,
    averageClientRating?: number,
    averageJobRating?: number,
    isActive: boolean,
    registrDate: string;
    userStatus: number;
    files: string[];
    comment: string;
    package: PackageResponse[];
    client?: ClientModel;
}

export interface PersonModel {
    name: string,
    city: {
        id: number,
        name: string
    }
    phoneNumber:number;
}

export interface Portfolio {
    clientId: number;
    photo: string;
}

export interface Review {
    reviewId: number;
    clientId: number;
    value: number;
    text: string;
    countComm: number;
}

export interface ClientModel {
    id: number;
    sexType: number;
    birthDay: Date;
    skills: string[];
    mainInformation: string;
    education: string;
    historyJob: string;
    portfolios: Portfolio[];
    reviews: Review[];
    isVerify: boolean;
}
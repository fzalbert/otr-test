import { CategoriesResponse } from './categories-response.model';
import { ServantsResponse } from './servants-response.model';
import { UserModel } from './../user.model';

export interface TaskResponse {
    id: number;
    name: string;
    description: string;
    owner: UserModel;
    city: City;
    // servant: ServantsResponse;
    servant: CategoriesResponse;
    lat: number;
    lon: number;
    timeStart: Date;
    timeEnd: Date;
    price: number;
    isCard: boolean;
    isDistanceWork: boolean;
    isOffer: boolean;
    isBanned: boolean;
    isOver: boolean;
    client: {user: UserModel};
}

export interface TasksResponse {
    id: number;
    servantId: number;
    owner: UserModel;
    name: string;
    timeStart: string;
    timeEnd: string;
    price: number;
    city: City;
    taskStatus: number;
    isBaned: boolean;
}

export interface City {
    id: number;
    name: string;
}
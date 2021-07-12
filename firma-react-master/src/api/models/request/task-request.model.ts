export interface UpdateTaskRequest {
    name: string;
    description: string;
    cityId: number | undefined;
    servantId: number;
    timeStart: string;
    timeEnd: string;
    price: number | undefined;
    isCard: boolean | undefined;
    isDistanceWork: boolean | undefined;
}
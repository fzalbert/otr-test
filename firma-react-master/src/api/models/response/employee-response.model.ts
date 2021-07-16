export interface EmployeeResponse {
    id: number | null;
    firstName: string;
    lastName: string;
    email: string;
    login: string;
    role: number;
    active: boolean;
}

export interface ExecutorResponse {
    id: number;
    lastName: string;
    email: string;
    login: string;
    role: string;
}
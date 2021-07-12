import { UpdateTaskRequest } from './models/request/task-request.model';
import { TaskResponse, TasksResponse } from './models/response/task-response.model';
import { environment } from './environments/environment';
import axios, { AxiosResponse } from 'axios';

const TasksAPI = {

    getAllTasks: (pageSize: number, pageNumber: number, search: string):Promise<AxiosResponse<TasksResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}/AdminTask/list`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: { 
                search: search,
                PageSize: pageSize,
                PageNumber: pageNumber
            } 
        });
    },

    getTasksByServantId: (servantId:number, pageSize: number, pageNumber: number, search: string):Promise<AxiosResponse<TasksResponse[]>> => {
        return axios.get(`${environment.apiEndPoint}/Task/list/${servantId}`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: { 
                servantId: servantId,
                search: search,
                PageSize: pageSize,
                PageNumber: pageNumber
            } 
        });
    },

    getTask: (taskId:number):Promise<AxiosResponse<TaskResponse>> => {
        return axios.get(`${environment.apiEndPoint}/Task/id`, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: { 
                id: taskId
            } 
        });
    },

    updateTask: (taskId:number, task: UpdateTaskRequest):Promise<AxiosResponse<TaskResponse>> => {
        return axios.post(`${environment.apiEndPoint}/Task/update/${taskId}`, {
            ...task
        }, {
            headers: {
                'Authorization' : localStorage.getItem('token'),
            },
            params: { 
                id: taskId
            } 
        });
    },

    banTask: (taskId:number, ban:boolean, text: string):Promise<AxiosResponse<TasksResponse>> => {
        return axios.get(`${environment.apiEndPoint}/AdminTask/ban/${taskId}`, {
            params: {
                id: taskId,
                bane: ban,
                text: text
            },
            headers: {
                'Authorization' : localStorage.getItem('token')
            }
        })
    },

    deleteTask: (taskId:number):Promise<AxiosResponse<any>> => {
        return axios.delete(`${environment.apiEndPoint}/AdminTask/delete/${taskId}`, {
            params: {id: taskId},
            headers: {
                'Authorization' : localStorage.getItem('token')
            }
        })
    }

}

export default TasksAPI;
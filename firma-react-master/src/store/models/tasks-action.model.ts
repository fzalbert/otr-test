import { TasksResponse } from './../../api/models/response/task-response.model';

export interface setTasksAction extends TasksResponse {}

export interface deleteTaskAction {
    taskId: number;
}
import { UpdateTaskRequest } from './../../api/models/request/task-request.model';
import actionsConstants from "../models/actions-constants";
import { deleteTaskAction, setTasksAction } from '../models/tasks-action.model';

export const setTasks = (action:setTasksAction[]) => {
    return ({
        type: actionsConstants.SET_TASKS,
        data: action
    })
}

// export const updateTask = (action: UpdateTaskRequest) => {
//     return ({
//         type: actionsConstants.UPDATE_TASK,
//         ...action
//     })
// }

export const banTask = (action:deleteTaskAction) =>({
    type: actionsConstants.BAN_TASK,
    ...action
})

export const deleteTask = (action:deleteTaskAction) =>({
    type: actionsConstants.DELETE_TASK,
    ...action
})
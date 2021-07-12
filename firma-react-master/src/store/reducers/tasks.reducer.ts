import { setTasksAction } from '../models/tasks-action.model';
import actionsConstants from '../models/actions-constants';

export const TasksReducer = (
        state:setTasksAction[]=[],
        action:any
    ) => {
        switch (action.type) {
            case actionsConstants.SET_TASKS:
                return [...action.data] // don't copy previous state into array
            // case actionsConstants.UPDATE_TASK:
            //     return state.map(item => item.id == action.id ? {...item, servant: !item.isBaned} : item)
            case actionsConstants.BAN_TASK:
                return state.map(item => item.id == action.taskId ? {...item, isBaned: !item.isBaned} : item)
            case actionsConstants.DELETE_TASK:
                return state.filter(item => item.id != action.taskId)
            default:
                return state;
        }
}
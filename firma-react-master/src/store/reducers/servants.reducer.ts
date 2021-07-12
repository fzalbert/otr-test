// import { ServantReducer } from './servant.reducer';
import { setServantsAction } from './../models/servants-action.model';
import actionsConstants from "../models/actions-constants";

// export const ServantsReducer = (
//         state:setServantsAction[]=[],
//         action:any
//     ) => {
//         switch (action.type) {
//             case actionsConstants.SET_SERVANTS:
//                 console.log(action.data, 'state')
//                 return [...action.data] // don't copy previous state into array
//             case actionsConstants.CREATE_SERVANT:
//                 return [
//                     ...state,
//                     ServantReducer({id: 1, name: '', imageUrl: '', subCategory:{id:0, name: '', imageUrl: '', countTasks: NaN, category: {id: 0, name: '', imageUrl:'', countTasks: NaN}}, countTasks: NaN}, action)
//                 ]
//             case actionsConstants.CHANGE_SERVANT:
//                 return state.map(item => ServantReducer(item, action))
//             case actionsConstants.DELETE_SERVANT:
//                 return state.filter(item => item.id != action.id)
//             default:
//                 return state;
//         }
// }
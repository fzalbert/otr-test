import { ServantsResponse } from './../../api/models/response/servants-response.model';
import actionsConstants from "../models/actions-constants";

// export const ServantReducer = (
//         state:ServantsResponse={
//             id: 1,
//             name: "",
//             imageUrl: "",
//             subCategory: {
//                 id: 0,
//                 name: "",
//                 imageUrl: "",
//                 countTasks: NaN,
//                 category: {
//                     id: 0,
//                     name: "",
//                     imageUrl: "",
//                     countTasks: NaN
//                 }
//             },
//             countTasks: NaN
//         },
//         action:any
//     ) => {
//         switch (action.type) {
//             case actionsConstants.CREATE_SERVANT:
//                 return({
//                     ...state,
//                     ...action
//                 })
//             case actionsConstants.CHANGE_SERVANT:
//                 return state.id === action.id ? ({
//                     ...state,
//                     name: action.name,
//                     imageUrl: action.imageUrl,
//                     subCategory: {
//                         id: action.subCategoryId,
//                         name: state.subCategory.name,
//                         imageUrl: state.subCategory.imageUrl,
//                         category: state.subCategory.category
//                     },
//                     countTasks: action.countTasks
//                 }) : state
//             default:
//                 break;
//         }
// }
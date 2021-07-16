import { setTnvedAction } from './../models/tnved-action.model';
import actionsConstants from "../models/actions-constants";

export const TnvedReducer = (
        state:setTnvedAction[] = [],
        action:any
    ) => {
        switch (action.type) {
            case actionsConstants.SET_TNVED_LIST:
                return action.data // don't copy previous state into array
            default:
                return state;
        }
}
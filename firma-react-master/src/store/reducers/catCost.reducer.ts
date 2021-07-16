import { setCatCostAction } from './../models/cat-cost-action.model';
import actionsConstants from "../models/actions-constants";

export const CatCostReducer = (
        state:setCatCostAction[] = [],
        action:any
    ) => {
        switch (action.type) {
            case actionsConstants.SET_CAT_COST_LIST:
                return action.data // don't copy previous state into array
            default:
                return state;
        }
}
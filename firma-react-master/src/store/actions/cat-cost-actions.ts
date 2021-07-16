import { setCatCostAction } from './../models/cat-cost-action.model';
import actionsConstants from '../models/actions-constants';

export const setCatCostList = (action:setCatCostAction[]) => {
    return ({
        type: actionsConstants.SET_CAT_COST_LIST,
        data: action
    })
}
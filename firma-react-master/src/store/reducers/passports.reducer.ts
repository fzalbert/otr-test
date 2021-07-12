import { SetPassportAction } from '../models/passport-action.model';
import actionsConstants from "../models/actions-constants";

export const PassportsReducer = (
        state:SetPassportAction[]=[],
        action:any
    ) => {
        switch (action.type) {
            case actionsConstants.SET_PASSPORTS:
                return [...action.data] // don't copy previous state into array
            case actionsConstants.VERIFY_PASSPORT:
                return state.map(item => {
                    if (item.passportId == action.passportId) {
                        item.statusConfirm = item.statusConfirm ? 0 : 1;
                        item.dateDeadline = action.dateDeadline;
                        item.type = action.type;
                    }
                    return item;
                })
            default:
                return state;
        }
}
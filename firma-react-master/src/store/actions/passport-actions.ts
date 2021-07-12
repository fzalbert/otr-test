import { SetPassportAction, VerifyPassportAction } from '../models/passport-action.model';
 import actionsConstants from "../models/actions-constants";

export const setPassports = (action:SetPassportAction[]) => {
    return ({
        type: actionsConstants.SET_PASSPORTS,
        data: action
    })
}

export const verifyPassport = (action:VerifyPassportAction) =>({
    type: actionsConstants.VERIFY_PASSPORT,
    ...action
})
import { SetPackagesAction } from "../models/packages-action.model";
import actionsConstants from "../models/actions-constants";

export const PackageReducer = (
        state:SetPackagesAction={
            id: 1,
            name: "",
            price: 0,
            countСalls: 0,
            packageType: 0
        },
        action:any
    ) => {
        switch (action.type) {
            case actionsConstants.CREATE_PACKAGE:
                return({
                    ...state,
                    ...action
                })
            case actionsConstants.CHANGE_PACKAGE:
                return state.id === action.id ? ({
                    ...state,
                    name: action.name,
                    price: action.price,
                    countCalls: action.countCalls,
                    packageType: action.packageType
                }) : state
            default:
                break;
        }
}
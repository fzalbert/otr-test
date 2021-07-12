import { PackageReducer } from './package.reducer';
import { SetPackagesAction } from './../models/packages-action.model';
import actionsConstants from "../models/actions-constants";

export const PackagesReducer = (
        state:SetPackagesAction[]=[],
        action:any
    ) => {
        switch (action.type) {
            case actionsConstants.SET_PACKAGES:
                return [...action.data] // don't copy previous state into array
            case actionsConstants.CREATE_PACKAGE:
                return [
                    ...state,
                    PackageReducer({id: 1, name: '', price: 0, countСalls: 0, packageType: 0}, action)
                ]
            case actionsConstants.CHANGE_PACKAGE:
                return state.map(item => PackageReducer(item, action))
            case actionsConstants.DELETE_PACKAGE:
                return state.filter(item => item.id != action.packageId)
            default:
                return state;
        }
}
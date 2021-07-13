import { SetPackagesAction, CreatePackageAction, ChangePackageAction, DeletePackageAction } from './../models/packages-action.model';
import actionsConstants from "../models/actions-constants";

export const setPackages = (action:SetPackagesAction[]) => {
    return ({
        type: actionsConstants.SET_PACKAGES,
        data: action
    })
}

export const createPackage = (action:CreatePackageAction) => ({
    type: actionsConstants.CREATE_PACKAGE,
    ...action
})

export const changePackage = (action:ChangePackageAction) => ({
    type: actionsConstants.CHANGE_PACKAGE,
    ...action
})

export const deletePackage = (action:DeletePackageAction) =>({
    type: actionsConstants.DELETE_PACKAGE,
    ...action
})
import { setThemesAction } from './../models/themes-action.model';
import actionsConstants from '../models/actions-constants';

export const setThemesList = (action:setThemesAction[]) => {
    return ({
        type: actionsConstants.SET_THEMES_LIST,
        data: action
    })
}
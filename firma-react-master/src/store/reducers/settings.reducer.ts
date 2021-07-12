import { Person } from './../../api/models/response/auth-response.model';
import actionsConstants from "../models/actions-constants";

interface SettingsState {
    restaurant: any;
    person: Person;
}

export const SettingsReducer = (
        state:SettingsState = {
            restaurant: {restaurantId:NaN, name:'', createDate:'', phoneNumber:'', address:''},
            person: {restaurantId: NaN, photoUrl: '',firstName: '', secondName: '', thirdName: '', email: '', address: '', passport:''}
        },
        action:any
    ):SettingsState => {
    switch (action.type) {
        case actionsConstants.SET_PROFILE_DATA:
            return {
                ...state,
                restaurant: {...action.restaurant},
                person: {...action.person}
            }
        case actionsConstants.DELETE_AVATAR:
            return {
                ...state,
                person : {
                    ...state.person,
                    photoUrl: ''
                }
            }
        case actionsConstants.SET_AVATAR:
            return {
                ...state,
                person: {
                    ...state.person,
                    photoUrl: action.photoUrl
                }
            }
        default:
            return state;
    }
}
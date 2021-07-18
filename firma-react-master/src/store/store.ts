import { StaffsReducer } from './reducers/staffs.reducer';
import { CatCostReducer } from './reducers/catCost.reducer';
import { TnvedReducer } from './reducers/tnved.reducer';
import { ThemesReducer } from './reducers/themes.reducer';
import { AppealsReducer } from './reducers/appeals.reducer';
import { AuthReducer } from './reducers/auth.reducer';
import { createStore, combineReducers } from 'redux';

const getStore = () => createStore(
    combineReducers({AuthReducer, AppealsReducer, ThemesReducer, TnvedReducer, CatCostReducer, StaffsReducer})
);

export default getStore;
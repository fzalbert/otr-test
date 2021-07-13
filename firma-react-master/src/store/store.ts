// import { ServantsReducer } from './reducers/servants.reducer';
import { TasksReducer } from './reducers/tasks.reducer';
import { SubCategoriesReducer } from './reducers/subcategories.reducer';
import { UsersReducer } from './reducers/users.reducer';
import { PassportsReducer } from './reducers/passports.reducer';
import { PackagesReducer } from './reducers/packages.reducer';
import { CategoriesReducer } from './reducers/categories.reducer';
import { SettingsReducer } from './reducers/settings.reducer';
import { AuthReducer } from './reducers/auth.reducer';
import { createStore, combineReducers } from 'redux';

const getStore = () => createStore(
    combineReducers({AuthReducer, SettingsReducer, CategoriesReducer, PackagesReducer, PassportsReducer, UsersReducer, 
    SubCategoriesReducer, TasksReducer})
);

export default getStore;
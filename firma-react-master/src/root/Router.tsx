import React from 'react';
import { Switch, Redirect, Route } from 'react-router';
import Header from '../components/layouts/Header/Header';
import Registration from '../components/views/Auth/Registration/Registration';
import SubCategory from '../components/views/SubCategories/SubCategory/SubCategory';
const ClientsList = React.lazy(() => import('../components/views/Admin/ClientsList/ClientsList'));
const StaffsList = React.lazy(() => import('../components/views/Admin/Staffs/Staffs'));
const AppealsList = React.lazy(() => import('../components/views/Admin/AppealsList/AppealsList'));
const AppealCreation = React.lazy(() => import('../components/views/AppealCreation/AppealCreation'));
const Appeal = React.lazy(() => import('../components/views/Appeal/Appeal'));
const Appeals = React.lazy(() => import('../components/views/Appeals/Appeals'));
const Authorization = React.lazy(() => import('../components/views/Auth/Authorization/Authorization'));
const Settings = React.lazy(() => import('../components/views/Settings/Settings'));
// const Tasks = React.lazy(() => import('../components/views/Tasks/Tasks'));
const Search = React.lazy(() => import('../components/views/Search/Search'));
const Users = React.lazy(() => import('../components/views/Users/Users'));

// const Home = React.lazy(() => import('../components/views/Home/Home'));


const RenderAppComponent = (props:any) => (
    <Header>
        {props}
    </Header>
);

const Router = () => {
    return(
        <Switch>
            <Route path="/authorization" component={Authorization} />
            <Route path="/registration" render={props => <Registration />} />
            <Route path="/settings" render={props => RenderAppComponent(<Settings />)} />
            <Route path="/appeals" render={props => RenderAppComponent(<Appeals />)} />
            <Route path="/appeal" render={props => RenderAppComponent(<Appeal />)} />
            <Route path="/new-appeal" render={props => RenderAppComponent(<AppealCreation />)} />
            <Route path="/admin/appeals" render={props => RenderAppComponent(<AppealsList />)} />
            <Route path="/admin/staffs" render={props => RenderAppComponent(<StaffsList />)} />
            <Route path="/admin/clients" render={props => RenderAppComponent(<ClientsList />)} />
            <Route path="/categories/category/:id/subcategory/:subCategoryId" render={props => RenderAppComponent(<SubCategory />)} />
            <Route path="/categories/category/:id/subcategory" render={props => RenderAppComponent(<SubCategory />)} />
            
            <Redirect exact from="/" to="/authorization" />
        </Switch>
    );
};

export default Router;

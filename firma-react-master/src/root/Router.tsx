import React from 'react';
import { Switch, Redirect, Route } from 'react-router';
import Header from '../components/layouts/Header/Header';
const Registration = React.lazy(() => import('../components/views/Auth/Registration/Registration'));
const ClientsList = React.lazy(() => import('../components/views/Admin/ClientsList/ClientsList'));
const ClientDetails = React.lazy(() => import('../components/views/Admin/ClientsList/ClientDetails/ClientDetails'));
const StaffsList = React.lazy(() => import('../components/views/Admin/Staffs/Staffs'));
const StaffCreation = React.lazy(() => import('../components/views/Admin/Staffs/StaffCreation/StaffCreation'));
const AppealsList = React.lazy(() => import('../components/views/Admin/AppealsList/AppealsList'));
const AppealCreation = React.lazy(() => import('../components/views/AppealCreation/AppealCreation'));
const Appeal = React.lazy(() => import('../components/views/Appeal/Appeal'));
const Appeals = React.lazy(() => import('../components/views/Appeals/Appeals'));
const Authorization = React.lazy(() => import('../components/views/Auth/Authorization/Authorization'));


const RenderAppComponent = (props:any) => (
    <Header>
        {props}
    </Header>
);

const Router = () => {
    return(
        <Switch>
            <Route path="/authorization" component={Authorization} />
            <Route path="/admin/authorization" component={Authorization} />
            <Route path="/registration" render={props => <Registration />} />
            <Route path="/appeals" render={props => RenderAppComponent(<Appeals />)} />
            <Route path="/appeal/:id" render={props => RenderAppComponent(<Appeal />)} />
            <Route path="/new-appeal" render={props => RenderAppComponent(<AppealCreation />)} />
            <Route path="/admin/staffs" render={props => RenderAppComponent(<StaffsList />)} />
            <Route path="/admin/staff/:employeeId" render={props => RenderAppComponent(<StaffCreation />)} />
            <Route path="/admin/staff-creation" render={props => RenderAppComponent(<StaffCreation />)} />
            <Route path="/admin/clients" render={props => RenderAppComponent(<ClientsList />)} />
            <Route path="/admin/client/:clientId" render={props => RenderAppComponent(<ClientDetails />)} />
            <Route path="/admin/appeals" render={props => RenderAppComponent(<AppealsList />)} />
            <Route path="/admin/appeal/:id" render={props => RenderAppComponent(<Appeal />)} />
            <Route path="/admin/edit-appeal/:id" render={props => RenderAppComponent(<AppealCreation />)} />
            {/* <Route path="/admin/new-appeal" render={props => RenderAppComponent(<AppealCreation />)} /> */}
            
            <Redirect exact from="/" to="/authorization" />
        </Switch>
    );
};

export default Router;

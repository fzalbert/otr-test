import React, { useState, useEffect } from 'react';
import './Sidebar.scss';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router';
import { NavLink } from 'react-router-dom';
import LogotypeIcon from '../../../assets/images/logotype.svg';
import { setAuthState } from '../../../store/actions/auth-actions';

const Sidebar = (props:any) => {

    const history = useHistory()
    const dispatch = useDispatch()

    const [isAdmin, setIsAdmin] = useState(false)
    let profileState = localStorage.getItem('user')

    const logout = () => {
        localStorage.removeItem('user')
        localStorage.removeItem('token')
        localStorage.removeItem('expireDate')
        dispatch(setAuthState({isLoggedIn: false, token: ''}));
        location.hash.slice(2,7) === 'admin' ? history.push('/admin/authorization') : history.push('/authorization')
    }

    useEffect(() => {
        setIsAdmin(location.hash.slice(2,7) === 'admin')
    })

    return (
        <div className="sidebar">

            <div className="logotype">
                <img src={LogotypeIcon} alt="" /> ФИРМА
            </div>
            {
                isAdmin ?
                    <nav className="admin-menu">
                        <li><NavLink to="/admin/appeals" activeClassName="active-link"><i className="icon-appeal-icon"></i> Обращения</NavLink></li>
                        <li><NavLink to="/admin/staffs" activeClassName="active-link"><i className="icon-staff-icon"></i> Сотрудники</NavLink></li>
                        <li><NavLink to="/admin/clients" activeClassName="active-link"><i className="icon-clients-icon"></i> Клиенты</NavLink></li>
                    </nav> :
                    null
            }
            <button onClick={() => logout()}><i className="icon-logout-icon"></i></button>
        </div>
    );
}

export default Sidebar;
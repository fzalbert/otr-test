import React from 'react';
import { NavLink, useHistory } from 'react-router-dom';
import './Menu.scss';
import categoriesIcon from '../../../assets/images/menu/categories.svg';
import tasksIcon from '../../../assets/images/menu/tasks-icon.svg';
import passportsIcon from '../../../assets/images/menu/passports.svg';
import usersIcon from '../../../assets/images/menu/user-icon.svg';
import packagesIcon from '../../../assets/images/menu/packages.svg';
import logo from '../../../assets/images/menu/logotype.svg';
import logoutIcon from '../../../assets/images/logout.svg';

const Menu = (props:{vision:boolean}) => {
    const links = [
        { icon: logo, path: '/tasks' },
        // { icon: homeIcon, path: '/rest' },
        { icon: tasksIcon, path: '/tasks' },
        { icon: categoriesIcon, path: '/search' },
        { icon: passportsIcon, path: '/passports' },
        { icon: usersIcon, path: '/users' },
        { icon: packagesIcon, path: '/packages' },
        // { icon: qrIcon, path: '/home/qr-code' },
    ];

    const history = useHistory();
    const handleLogoutClick = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('expireDate');
        localStorage.clear();
        history.push('/authorization');
    }
    
    return (
        <nav style={props.vision ? {left: 0} : {left: '-100%'}}>
            <div className="navigation-inner">
                <ul>
                    {links.map((link,index) => 
                        <NavLink exact to={link.path} activeClassName={link.icon != logo ? "active-link" : ''} key={link.path + index}><img src={link.icon} alt="" style={link.icon === logo ? {width: '85px'} : {width: '35px'}} /></NavLink>
                    )}
                    <a onClick={handleLogoutClick}><img src={logoutIcon} alt=""/></a>
                </ul>
            </div>
        </nav>
    ); 
};

export default Menu;
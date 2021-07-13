import React, { useState, useEffect } from 'react';
import './Sidebar.scss';
import activityIcon from '../../../assets/images/sidebar/activity.svg';
import creditCardIcon from '../../../assets/images/sidebar/credit-card.svg';
import { CSSTransition } from 'react-transition-group';
import ProfileMenu from '../ProfileMenu/ProfileMenu';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory } from 'react-router';
import { NavLink } from 'react-router-dom';
import { AxiosResponse } from 'axios';
import { UserModel } from '../../../api/models/user.model';
import LogotypeIcon from '../../../assets/images/logotype.svg';
import { setAuthState } from '../../../store/actions/auth-actions';

const activityStyle = {
    // backgroundImage: `url(${activityIcon})`
}

const Sidebar = (props:any) => {

    // const profileState = useSelector((state:any) => state.SettingsReducer);
    const history = useHistory()
    const dispatch = useDispatch()

    const [clientWidth, changeClientWidth] = useState(document.documentElement.clientWidth)
    const [menu, toggleMenu] = useState(false)
    const [wallet, walletVision] = useState(false)
    const [profileMenu, profileMenuVision] = useState(false)
    // const [profileState, setProfileState] = useState<UserModel>(Object)
    let profileState = localStorage.getItem('user')

    // const getPhoto = () => {
    //     getAvatar()
    //         .then((response:AxiosResponse<AvatarResponse>) => {
    //             dispatch(setAvatarState({photoUrl: response.data.url}))
    //         })
    //         .catch(err => console.log(err));
    // }

    const logout = () => {
        localStorage.removeItem('user')
        localStorage.removeItem('token')
        localStorage.removeItem('expireDate')
        dispatch(setAuthState({isLoggedIn: false, token: ''}));
        history.push('/authorization')
    }

    useEffect(() => {
        // if(profileState.avatar === undefined) {
        //     setProfileState()
        // }
        window.addEventListener('resize', () => {
            changeClientWidth(document.documentElement.clientWidth);
        })
    })

    // Handlers
    const showMenu = () => {
        toggleMenu(!menu);
        props.showMenuComponent(!menu);
        profileMenu && profileMenuVision(false)
        wallet && walletVision(false)
    }

    const showProfileMenu = () => {
        profileMenuVision(!profileMenu)
        wallet && walletVision(false)
    }

    return (
        <div className="sidebar">

            {/* <NavLink to="/home/withdrawal" className="withdrawal-btn"><img src={creditCardIcon} alt=""/> Transfer HUB</NavLink> */}

            {/* <button style={activityStyle} className="activity-btn" /> */}
            {/* Profile Menu */}
            <div className="logotype">
                <img src={LogotypeIcon} alt="" /> ФИРМА
            </div>
            <div className="profile-menu-toggle" onClick={showProfileMenu}>
                {profileState && JSON.parse(profileState)?.avatar ? 
                    <img src={'http://185.22.63.194:998/' + JSON.parse(profileState ? profileState : '')?.avatar} alt=" " className="avatar"/> :
                    <div className="not-avatar"><i className="icon-profile-icon"></i></div>
                }
                Александр
            </div>
            <button onClick={() => history.push('/settings')}><i className="icon-cog"></i></button>
            <button onClick={() => logout()}><i className="icon-logout-icon"></i></button>

            {/* <div className="profile-menu-container">
                <CSSTransition in={profileMenu} timeout={300} unmountOnExit classNames="show-hide-animation">
                    <ProfileMenu closeMenu={() => showMenu()} />
                </CSSTransition>
            </div> */}

            {/* Mobile Menu toggle-btn */}
    {/* // not-adaptive */}
            {/* {
                clientWidth <= 600 ? 
                    <button onClick={showMenu} className={menu ? 'burger-menu-btn burger-menu-btn__active':'burger-menu-btn'}><span></span><span></span><span></span></button> :
                    null
            } */}
        </div>
    );
}

export default Sidebar;
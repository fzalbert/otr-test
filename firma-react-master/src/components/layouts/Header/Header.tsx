import React from 'react';
import './Header.scss';
import Sidebar from '../../ui/Sidebar/Sidebar';

const Header = (props:any) => {

    return(
        <React.Fragment>
            <Sidebar />
            <div className="view-container">
                {props.children}
            </div>
        </React.Fragment>
    )
}

export default Header;
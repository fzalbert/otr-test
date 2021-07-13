import React, { useState } from 'react';
import './Header.scss';
import Menu from '../../ui/Menu/Menu';
import Sidebar from '../../ui/Sidebar/Sidebar';

const Header = (props:any) => {
    const [menu, toggleMenu] = useState(false);

    return(
        <React.Fragment>
            {/* // not-adaptive */} 
            {/* <Menu vision={document.documentElement.clientWidth <= 600 ? menu : true} /> */}
            {/* <Menu vision={true} /> */}
            {/* <Sidebar showMenuComponent={(e:any) => toggleMenu(e)} /> */}
            <Sidebar />
            <div className="view-container">
                {props.children}
            </div>
        </React.Fragment>
    )
}

export default Header;
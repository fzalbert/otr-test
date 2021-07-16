import React, { useRef } from 'react';
import './ErrorModal.scss';
import errorIcon from '../../../assets/images/icons/error-icon.svg';

const ErrorModal = (props: any) => {

    const errorRef = useRef<HTMLDivElement>(null)
    
    return(
        <div className="error-modal" style={{left: errorRef.current ? (document.documentElement.clientWidth - errorRef.current?.clientWidth) / 2 + 'px' : '0'}} ref={errorRef}>
            <img src={errorIcon} alt=""/>
            <p>{ props.children }</p>
            <p>Пожалуйста, проверьте введенные данные</p>
        </div>
    );
}

export default ErrorModal;
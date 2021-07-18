import React, { useRef } from 'react';
import './SuccessModal.scss';

const SuccessModal = (props: any) => {
    
    return(
        <div className="success-modal">
            <p>{ props.children }</p>
            {/* <p>ПУспешно</p> */}
        </div>
    );
}

export default SuccessModal;
import React, { useEffect, useState } from 'react';
import './ModalView.scss';

const ModalView = (props:any) => {
    return (
        <div className="modal-container" onClick={() => props.closeEvent()}>
            <div className="modal" onClick={(e) => e.stopPropagation()}>
                {props.children}
            </div>
        </div>
    )
}

export default ModalView;
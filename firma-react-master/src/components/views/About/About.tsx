import React, { useState, useEffect } from 'react';
import './About.scss';
import { AboutAPI } from '../../../api/about';
import { AxiosResponse, AxiosError } from 'axios';

const About:React.FC = React.memo((props:any) => {

    const [aboutText, setAboutText] = useState('')

    const [req, doReq] = useState(false)

    const getAboutText = () => {
        AboutAPI.getAboutText()
            .then((response: AxiosResponse<{id: number, description: string}>) => {
                if('code' in response) return;

                doReq(true)
                setAboutText(response.data.description)
            })
            .catch((err: AxiosError) => doReq(true))
    }

    const updateAboutText = () => {
        AboutAPI.updateText(aboutText)
            .then((response: AxiosResponse<{id: number, description: string}>) => {
                if('code' in response) return

                setAboutText(response.data.description)
            })
    }

    useEffect(() => {
        !req ? getAboutText() : null
    })

    return (
        <React.Fragment>
            <h2>О проекте</h2>
            <div className="main-textarea_with-label">
                <textarea id="about" value={aboutText} onChange={(e) => setAboutText(e.target.value)} />
                <label htmlFor="about" className={aboutText === '' ? 'null' : 'filled-input_label'}>Информация о проекте</label>
            </div>
            <button className="main-btn" onClick={updateAboutText}>Сохранить</button>
        </React.Fragment>
    )
})

export default About;
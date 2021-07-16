import React, { useEffect, useState } from 'react';
import './Authorization.scss';
import useFormState from '../../../../common/customHooks/useFormState';
import { auth, authClient } from '../../../../api/auth';
import { AuthResponse } from '../../../../api/models/response/auth-response.model';
import { AxiosError, AxiosResponse } from 'axios';
import { useDispatch } from 'react-redux';
import { setAuthState } from '../../../../store/actions/auth-actions';
import ErrorModal from '../../../ui/ErrorModal/ErrorModal';
import { CSSTransition } from 'react-transition-group';
import { useHistory } from 'react-router';
import LogotypeIcon from "../../../../assets/images/logotype.svg";

const Authorization = () => {

    const login = useFormState('');
    const password = useFormState('');

    const [error, setError] = useState('');
    const dispatch = useDispatch();

    const history = useHistory();

    const logIn = (login:string,password:string, e:any) => {
        e.preventDefault();
        location.hash.slice(2,7) === 'admin' ?
            auth(login, password)
                .then((response:AxiosResponse<AuthResponse>) => {
                    localStorage.setItem('token', `Bearer ${response.data}`);
                    // localStorage.setItem('expireDate', response.data.token.expireDate);
                    // localStorage.setItem('user', JSON.stringify(response.data.user))
                    dispatch(setAuthState({isLoggedIn: true, token: `Bearer ${response.data}`}));
                    history.push('/admin/appeals');
                })
                .catch((err:AxiosError) => {
                    console.log(err)
                    setError(err.response?.data.message)
                    setTimeout(() => setError(""), 5000);
                }) :
            authClient(login, password)
                .then((response:AxiosResponse<AuthResponse>) => {
                    localStorage.setItem('token', `Bearer ${response.data}`);
                    dispatch(setAuthState({isLoggedIn: true, token: `Bearer ${response.data}`}));
                    history.push('/appeals');
                })
                .catch((err:AxiosError) => {
                    console.log(err)
                    setError(err.response?.data.message)
                    setTimeout(() => setError(""), 5000);
                })
    }
    
    useEffect(() => {
        console.log(location.hash)
    })

    return (
        // <div className="auth-component authorization-component">
        <React.Fragment>
            <CSSTransition in={error.length !== 0} timeout={300} unmountOnExit classNames="show-hide-animation">
                <ErrorModal>{error}</ErrorModal>
            </CSSTransition>
            <form noValidate className="auth-form" onSubmit={(e) => logIn(login.value, password.value, e)}>
                <div className="logotype">
                    <img src={LogotypeIcon} alt="" /> фирма { location.hash.slice(2,7) === 'admin' ? ' - админ' : '' }
                </div>
                <input type="text" name="login" {...login} placeholder="Имя пользователя" className="main-input" />
                <input type="password" name="password" {...password} placeholder="Пароль" className="main-input" />
                <div className="common-controls" style={{justifyContent: location.hash.slice(2,7) === 'admin' ? 'center' : 'space-between'}}>
                    <button type="submit" className="common-btn">Вход</button>
                    {
                        location.hash.slice(2,7) !== 'admin' ?
                            <button type="submit" className="common-btn transparent-btn" onClick={() => history.push('/registration')}>Регистрация</button> :
                            null
                    }
                </div>
            </form>
        </React.Fragment>
        // </div>
    );
}

export default Authorization;
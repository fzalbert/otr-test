import React, { Suspense, useEffect } from 'react';
import '../common/styles/common.scss';
import './App.scss';
import { HashRouter } from 'react-router-dom';
import Router from './Router';
import ErrorBoundary from '../components/views/ErrorBoundary/ErrorBoundary';
import PreLoader from '../components/ui/PreLoader/PreLoader';
import { useDispatch } from 'react-redux';
import { setAuthState } from '../store/actions/auth-actions';

const App:React.FC = () => {

  const dispatch = useDispatch();
  const token = localStorage.getItem('token');

  useEffect(() => {
    dispatch(setAuthState({isLoggedIn: token != undefined, token: token != undefined ? token : ''}))
  })

  return (
    <div className="App">
      <HashRouter>
        <ErrorBoundary>
          <Suspense fallback={<PreLoader />}>
            <Router />
          </Suspense>
        </ErrorBoundary>
      </HashRouter>
    </div>
  );
}

export default App;

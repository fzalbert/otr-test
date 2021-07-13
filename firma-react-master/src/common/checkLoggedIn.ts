import { useSelector } from 'react-redux';
import { useHistory } from 'react-router';
import { useEffect } from 'react';

// Check if user is not logged in and go to auth
const checkLoggedIn = () => {
    const state = useSelector((state:any) => state.AuthReducer);
    const history = useHistory();
    useEffect(() => {
        if(!state.isLoggedIn && !localStorage.getItem('user')) {
            history.push('/authorization');
        }
    });
}

export default checkLoggedIn;
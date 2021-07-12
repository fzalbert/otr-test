import React, { useEffect, useState } from 'react';
import './UserReviews.scss';
import { useParams } from 'react-router';
import { AxiosResponse } from 'axios';
import { UserModel } from '../../../../../api/models/user.model';
import UsersAPI from '../../../../../api/users';
import { NavLink } from 'react-router-dom';

const UserReviews:React.FC = React.memo((props:any) => {

    const { userId } = useParams<{userId: string}>();
    const [user, setUser] = useState<UserModel | null>(null)

    const [req, doReq] = useState(false)

    const getUser = () => {
        UsersAPI.getUserById(userId ? +userId : NaN)
            .then((res:AxiosResponse<UserModel>) => {
                doReq(true)
                setUser(res.data)
            })
    }

    useEffect(() => {
        !req ? getUser() : null
    })

    return (
        <React.Fragment>
            <h2>Отзывы о пользователе {user?.person.name}</h2>
            <div className="reviews">
                {user?.client?.reviews?.map((review, index) => (
                    <div className="review-card">
                        <NavLink to={`/users/user/${review.clientId}`}>User #{review.clientId}</NavLink>
                        <p>{review.text}</p>
                    </div>
                ))}
            </div>
        </React.Fragment>
    )
})

export default UserReviews;
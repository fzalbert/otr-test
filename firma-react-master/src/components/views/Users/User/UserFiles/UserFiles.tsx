import React, { useEffect, useState } from 'react';
import './UserFiles.scss';
import { useParams } from 'react-router';
import { AxiosResponse } from 'axios';
import { UserModel } from '../../../../../api/models/user.model';
import UsersAPI from '../../../../../api/users';

const UserFiles:React.FC = React.memo((props:any) => {

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
            <h2>Файлы пользователя {user?.person.name}</h2>
            <div className="files">
                {user?.files?.map((file, index) => (
                    <React.Fragment>
                        <a href={'http://194.177.23.9:998/' + file} download={user?.person.name + 'File'} className="file-link">{user.person.name} file {index + 1}</a>
                        <br />
                    </React.Fragment>
                ))}
            </div>
        </React.Fragment>
    )
})

export default UserFiles;
import React, { useEffect, useState } from 'react';
import './User.scss';
import { useHistory, useParams } from 'react-router';
import { useDispatch } from 'react-redux';
import { AxiosResponse } from 'axios';
import checkLoggedIn from '../../../../common/checkLoggedIn';
import { ClientModel, UserModel } from '../../../../api/models/user.model';
import UsersAPI from '../../../../api/users';
import { blockUser as setBlockUser,setDeleteUser } from '../../../../store/actions/users-actions'
import userIcon from '../../../../assets/images/menu/user-icon.svg';
import moment from 'moment';
import useFormState from '../../../../common/customHooks/useFormState';
import uploadAvatarIcon from '../../../../assets/images/icons/upload-avatar.svg';
import deleteIcon from '../../../../assets/images/icons/delete.svg';
import blockIcon from '../../../../assets/images/icons/block-icon.svg';
import blockedIcon from '../../../../assets/images/icons/blocked-icon.svg';
import folderIcon from '../../../../assets/images/icons/folder-icon.svg';
import reviewIcon from '../../../../assets/images/icons/review-icon.svg';
import packageIcon from '../../../../assets/images/menu/packages.svg';
import maleIcon from '../../../../assets/images/icons/male.svg';
import womanIcon from '../../../../assets/images/icons/woman.svg';
import CitiesAPI from '../../../../api/cities';
import { City } from '../../../../api/models/response/task-response.model';
import useDebounce from '../../../../common/customHooks/useDebounce';
import { NavLink } from 'react-router-dom';
import { PackageResponse } from '../../../../api/models/response/package-response.model';

const User: React.FC = React.memo((props:any) => {

    checkLoggedIn();

    const { userId } = useParams<{userId: string}>();

    const history = useHistory();
    const [user, setUser] = useState<UserModel | null>(null)

    const form = new FormData()
    
    // const [userType, setUserType] = useState<number>(0)
    const [userStatus, setUserStatus] = useState<number>(0)
    const [optionsListVision, changeOptionsListVision] = useState(false)
    const comment = useFormState('');
    const name = useFormState('');
    const phoneNumber = useFormState('');
    const mainInfo = useFormState('');
    const education = useFormState('');
    const jobHistory = useFormState('');
    const birthDate = useFormState(moment(new Date()).format('YYYY-MM-DD'));
    const [sex, setSex] = useState(0);

    const [citiesState, setCities] = useState<any[]>()

    const [selectedCity, selectCity] = useState<City>();
    const [citySearchString, setCitySearchString] = useState('');
    const debouncedCitySearch = useDebounce(citySearchString, 500);

    const [citiesReq, doCitiesReq] = useState(false)

    const [citiesVision, toggleCitiesVision] = useState(false)
    const [packagesVision, changePackagesVision] = useState(false)

    const dispatch = useDispatch();

    const [req, doReq] = useState(false)


    const searchCity = () => {
        getCities(citySearchString)
    }

    const getUser = () => {
        UsersAPI.getUserById(userId ? +userId : NaN)
            .then((res:AxiosResponse<UserModel>) => {
                doReq(true)
                setUser(res.data)

                setUserStatus(res.data.userStatus)
                name.onChange({ target: {value: res.data.person.name} })
                phoneNumber.onChange({ target: {value: res.data.person.phoneNumber} })
                selectCity(res.data.person.city)
                setCitySearchString(res.data.person.city.name)
                res.data.comment ? comment.onChange({ target: { value: res.data.comment } }) : null
                
                if (res.data.client) {
                    let client = res.data.client
                    client.mainInformation ? mainInfo.onChange({target: { value: client.mainInformation }}) : null
                    education.onChange({target: { value: client.education }})
                    jobHistory.onChange({target: { value: client.historyJob }})
                    birthDate.onChange({target: { value: moment(client.birthDay).format('YYYY-MM-DD') }})
                    setSex(client.sexType)
                }
            })
    }

    const updateUserData = () => {
        updateUser()
        user?.client ? updateClientData() : null
    }

    const updateUser = (onlyAvatar?: boolean, deleteAvatar?: boolean) => {
        if (onlyAvatar) {
            form.append('Name', `${user?.person.name}`)
            form.append('PhoneNumber', `${user?.person.phoneNumber}`)
            form.append('CityId', `${user?.person.city.id}`)
            form.append('UserStatus', `${user?.userStatus}`)
            form.append('isActivated', `${user?.isActive}`)
        } else {
            form.append('Name', name.value)
            form.append('PhoneNumber', phoneNumber.value)
            form.append('CityId', selectedCity ? selectedCity.id + '' : '')
            form.append('UserStatus', `${userStatus}`)
            form.append('isActivated', `${user?.isActive}`)
            form.append('Avatar', ``)
        }
        form.append('isDeleteAvatar', deleteAvatar ? deleteAvatar + '' : false + '')
        UsersAPI.updateUser(userId ? +userId : NaN, form)
            .then((response: AxiosResponse<UserModel>) => {
                if('code' in response) return;

                if(user) {
                    let updatedUser = user;
                    updatedUser.avatar = response.data.avatar

                    setUser({...user, ...updatedUser})
                }
            })
    }

    const updateClientData = () => {
        let client = {
            mainInformation: mainInfo.value,
            education: education.value,
            birthDay: birthDate.value + 'T13:59:30.751Z',
            sexType: sex,
            historyJob: jobHistory.value
        }
        UsersAPI.updateClientData(user && user.client ? user.client.id : NaN, client)
            .then((res:AxiosResponse<ClientModel>) => {

            })
    }

    const saveComment = () => {
        UsersAPI.comment(userId ? +userId : NaN, comment.value)
            .then((res:AxiosResponse<any>) => {
                
            })
    }

    const blockUser = (id:number, block: boolean) => {
        UsersAPI.blockUser(id, block)
            .then((res:AxiosResponse<UserModel>) => {
                dispatch(setBlockUser({userId: id, isActive: !block}))
                setUser({...(user as UserModel), isActive: !user?.isActive})
                console.log(res.data)
            })
    }

    const deleteUser = () => {
        UsersAPI.deleteUser(userId ? +userId : 0)
            .then((res:AxiosResponse<UserModel[]>) => {
                dispatch(setDeleteUser({userId: userId ? +userId : 0}))
            })
    }

    const onSelectFile = (e:any) => {
        if (e.target.files && e.target.files[0]) {
            form.append('Avatar', e.target.files[0]);
            updateUser(true)
        }
    }

    const deleteAvatar = () => {
        form.append('Avatar', "'delete'");
        updateUser(true, true)
    }

    const getCities = (search: string = '') => {
        CitiesAPI.getCitiesList(search)
            .then((res:AxiosResponse<City[]>) => {
                doCitiesReq(true)
                setCities(res.data)
            })
    }

    const onChangeCity = (city: any) => {
        selectCity(city)
        setCitySearchString(city.name)
    }

    const changeUserStatus = (status: number) => {
        setUserStatus(status)
        changeOptionsListVision(false)
    }

    const getUserStatus = (status: number) => {
        switch (status) {
            case 0:
                return 'Заказчик'
            case 1:
                return 'Исполнитель'
            case 2:
                return 'Исполнитель / Заказчик'
            default:
                break;
        }
    }

    useEffect(() => {
        if(userId && user === null) {
            getUser()
        }
        if (debouncedCitySearch !== undefined) {
            // Сделать запрос к АПИ
            searchCity()
        } else if(!citiesReq) {
            getCities()
        }
    }, [user, userId, debouncedCitySearch]);

    return(
        <React.Fragment>
            <div className="user-info">
                <div className="profile-avatar">
                    <img src={user?.avatar ? 'http://185.22.63.194:998/' + user?.avatar : userIcon} alt="" className="avatar" />
                    <input type="file" accept="image/x-png,image/gif,image/jpeg" onClick={(e:any) => e.target.value = ''} onChange={onSelectFile} disabled={user?.avatar ? true : false} />
                    <button className={ !user?.avatar ? 'edit-avatar-btn avatar-active-action' : 'edit-avatar-btn'}>
                        <img src={uploadAvatarIcon} alt=""/>
                    </button>
                    <button className={ user?.avatar ? 'delete-avatar-btn avatar-active-action' : 'delete-avatar-btn'} type="button" onClick={deleteAvatar}><img src={deleteIcon} alt=""/></button>
                </div>
                <div className="user-row user-row_activation">
                    {
                        user?.isActive ?
                            <img src={blockIcon} alt="" className="block-btn" onClick={() => blockUser(user.id, user.isActive)} /> :
                            <img src={blockedIcon} alt="" className="block-btn block-btn_blocked" onClick={() => blockUser(userId ? +userId : NaN, false)} />
                    }
                </div>
                <div className="user-links">
                    <button onClick={() => history.push(`/users/user/${userId}/files`)}><img src={folderIcon} alt=""/></button>
                    { user?.package && user?.package.length ? <button onClick={() => changePackagesVision(!packagesVision)}><img src={packageIcon} alt=""/></button> : null }
                    {
                        packagesVision ?
                            <div className="user-packages-list">
                                { user?.package?.map((pack:PackageResponse) => <NavLink to={`/packages/package/${pack.id}`}>{pack.name} <span>{moment(new Date(pack.dateTime)).format('DD-MM-YY')}</span></NavLink>) }
                            </div> :
                            null
                    }
                    {/* { user?.client?.reviews.length ? <button onClick={() => history.push(`/users/user/${userId}/reviews`)}><img src={reviewIcon} alt=""/></button> : null } */}
                </div>
                <div className="main-select-container">
                    <button onClick={() => changeOptionsListVision(!optionsListVision)}>{getUserStatus(userStatus)}</button>
                    {
                        optionsListVision ? 
                            <div className="main-select">
                                {/* <p onClick={() => changeUserStatus(0)}>Исполнитель</p> */}
                                <p onClick={() => changeUserStatus(0)}>Заказчик</p>
                                <p onClick={() => changeUserStatus(1)}>Исполнитель</p>
                                <p onClick={() => changeUserStatus(2)}>Исполнитель / Заказчик</p>
                            </div> :
                            null
                    }
                </div>
                <div className="main-input_with-label">
                    <input type="text" id="name" className="main-input" {...name} />
                    <label htmlFor="name" className={name.value === '' ? 'null' : 'filled-input_label'}>Имя</label>
                </div>
                <div className="main-input_with-label">
                    <input type="tel" id="tel" className="main-input" {...phoneNumber} />
                    <label htmlFor="tel" className={phoneNumber.value === '' ? 'null' : 'filled-input_label'}>Телефон</label>
                </div>
                {/* <div className="country-picker">
                    <div className="main-input_with-label">
                        <input type="text" id="category" className="main-input"
                            value={citySearchString} 
                            onChange={(e:any) => setCitySearchString(e.target.value)} 
                            onBlur={() => setTimeout(() => toggleCitiesVision(false), 500)} 
                            onFocus={() => toggleCitiesVision(true)}
                        />
                        <label htmlFor="category" className={citySearchString === '' ? 'null' : 'filled-input_label'}>Город</label>
                    </div>
                    { citiesVision ? <div className="countries-list">
                        {citiesState?.map((item:any) => <p key={item.id} onClick={() => onChangeCity(item)}>{ item.name }</p> )}
                    </div> : null}
                </div> */}
                <div className="user-row">
                    <button className={!sex ? "selected-sex" : ''} onClick={() => setSex(0)}><img src={maleIcon} alt=""/></button>
                    <button className={sex ? "selected-sex selected-sex_woman" : ''} onClick={() => setSex(1)}><img src={womanIcon} alt=""/></button>
                </div>
                {
                    user?.client ?
                        <React.Fragment>
                            <div className="main-input_with-label">
                                <input type="text" id="main" className="main-input" {...mainInfo} />
                                <label htmlFor="main" className={mainInfo.value === '' ? 'null' : 'filled-input_label'}>Основная информация</label>
                            </div>
                            <div className="main-input_with-label">
                                <input type="text" id="educ" className="main-input" {...education} />
                                <label htmlFor="educ" className={education.value === '' ? 'null' : 'filled-input_label'}>Образование</label>
                            </div>
                            <div className="main-input_with-label">
                                <input type="text" id="job" className="main-input" {...jobHistory} />
                                <label htmlFor="job" className={jobHistory.value === '' ? 'null' : 'filled-input_label'}>История работы</label>
                            </div>
                            <div className="main-input_with-label">
                                <input type="date" id="birth" className="main-input" {...birthDate} />
                                <label htmlFor="birth" className={birthDate.value === '' ? 'null' : 'filled-input_label'}>Дата рождения</label>
                            </div>

                        </React.Fragment> :
                        null
                }
                <button className="main-btn" onClick={updateUserData}>Сохранить</button>
                <a className="delete-btn" onClick={deleteUser}>Удалить пользователя</a>
                <div className="main-textarea_with-label">
                    <textarea id="comment" {...comment} />
                    <label htmlFor="comment" className={comment.value === '' ? 'null' : 'filled-input_label'}>Комментарий</label>
                </div>
                <button className="main-btn" onClick={saveComment}>Сохранить комментарий</button>
            </div>
        </React.Fragment>
    );
})

export default User;
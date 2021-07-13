import React, { useEffect, useState } from 'react';
import './Users.scss';
import DefaultTable from '../../ui/DefaultTable/DefaultTable';
import { useHistory, useParams } from 'react-router';
import { useSelector, useDispatch } from 'react-redux';
import { AxiosResponse } from 'axios';
import checkLoggedIn from '../../../common/checkLoggedIn';
import { UserModel } from '../../../api/models/user.model';
import UsersAPI from '../../../api/users';
import { setUsers, blockUser as setBlockUser,setDeleteUser } from '../../../store/actions/users-actions';
import blockIcon from '../../../assets/images/icons/block-icon.svg';
import blockedIcon from '../../../assets/images/icons/blocked-icon.svg';
import useDebounce from '../../../common/customHooks/useDebounce';

const Users: React.FC = React.memo((props:any) => {

    checkLoggedIn();

    const { searchStr } = useParams<{searchStr: string}>();
    const history = useHistory();
    const usersState = useSelector((state:any) => state.UsersReducer);
    const dispatch = useDispatch();

    const [pageNumber, setPageNumber] = useState(1)
    const [req, doReq] = useState(false)

    const [searchString, setSearchString] = useState<string | undefined>(undefined)
    const debouncedSearch = useDebounce(searchString, 500)

    const [searchType, setSearchType] = useState(3)

    const search = () => {
        getUsersList(pageNumber, searchString)
    }

    const getUsersList = (page?:number, search: string = '', status: number = 3) => {
        UsersAPI.getUsers(8, page ? page : pageNumber, search, status)
            .then((response:AxiosResponse<UserModel[]>) => {
                doReq(true)
                if(search != '' && page === pageNumber) {
                    dispatch(setUsers(response.data))
                }
                else if(response.data.length) {
                    page ? setPageNumber(page) : null
                    dispatch(setUsers(response.data))
                    setSearchType(status)
                } else {
                    !page ? dispatch(setUsers(response.data)) : null
                }
            })
            .catch(err => console.log(err))
    }

    const getFilteredList = (type:number) => {
        getUsersList(pageNumber, searchStr, type)
    }

    const blockUser = (id:number, block: boolean) => {
        UsersAPI.blockUser(id, block)
            .then((res:AxiosResponse<UserModel>) => {
                dispatch(setBlockUser({userId: id, isActive: !block}))
                console.log(res.data)
            })
    }

    const deleteUser = (id:number) => {
        UsersAPI.deleteUser(id ? +id : 0)
            .then((res:AxiosResponse<UserModel[]>) => {
                dispatch(setDeleteUser({userId: id ? +id : 0}))
            })
    }

    const getUserStatus = (user: UserModel) => {
        switch (user.userStatus) {
            case 0:
                return 'Заказчик'
            case 1:
                return 'Исполнитель'
            case 2:
                return 'Исп / Зак'
            case 3:
                return 'Все'
            default:
                break;
        }
    }

    const onSelectPrevPage = () => {
        if (pageNumber > 1) {
            getUsersList(pageNumber - 1)
        }
    }

    const onSelectNextPage = () => {
        getUsersList(pageNumber + 1);
    }

    useEffect(() => {
        if (debouncedSearch !== undefined) {
            // Сделать запрос к АПИ
            search()
        } else if(!req) {
            getUsersList()
            searchStr ? setSearchString(searchStr) : null
        }
        // categoriesState.length ? console.log(categoriesState) : null
    }, [debouncedSearch]);

    return(
        <React.Fragment>
            <div className="search-head">
                <input type="text" name="search" className="search" value={searchString} onChange={(e:any) => setSearchString(e.target.value)} />
                <div className="select-row">
                    <button onClick={() => getFilteredList(0)} className="custom-option">Заказчики</button>
                    <button onClick={() => getFilteredList(1)} className="custom-option">Исполнители</button>
                    <button onClick={() => getFilteredList(2)} className="custom-option">Исп / Зак</button>
                    <button onClick={() => getFilteredList(3)} className="custom-option">Все</button>
                    <div className="selected-border" style={{left: searchType * 170 + 'px'}}></div>
                </div>
            </div>
            <div className="table-container">
                <DefaultTable 
                    list={usersState?.map((item:UserModel) => ({
                        id: item.id,
                        userUniqueNumber: item.id,
                        avatar: item.avatar ? 'http://185.22.63.194:998/' + item.avatar : '',
                        name: item.person.name,
                        phone: item.person.phoneNumber,
                        // city: item.person.city.name,
                        registerDate: new Date(item.registrDate),
                        status: getUserStatus(item),
                        block: item.isActive ? <img src={blockIcon} alt="" className="block-btn" onClick={() => blockUser(item.id, item.isActive)} /> : <img src={blockedIcon} alt="" className="block-btn" onClick={() => blockUser(item.id, item.isActive)} />
                    }))}
                    // invisibleIndex={true}
                    realIndex={8 * (pageNumber - 1)}
                    // handleRowClick={(id) => history.push(`/users/${id}`)}
                    handleEditRowClick={(id) => history.push(`/users/user/${id}`)}
                    handleDeleteRowClick={(id) => deleteUser(id)}
                    headings={['Id','Аватар', 'Имя', 'Телефон', 'Дата регистрации', 'Тип аккаунта']}
                    // headings={['Id','Аватар', 'Имя', 'Телефон', 'Город', 'Дата регистрации', 'Тип аккаунта']}
                    caption="Пользователи"
                    pagination={<div className="page-switches">
                        <button className="prev-page" onClick={onSelectPrevPage}></button>
                        <div>{pageNumber}</div>
                        <button className="next-page" onClick={onSelectNextPage}></button>
                    </div>}
                />
            </div>
        </React.Fragment>
    );
})

export default Users;
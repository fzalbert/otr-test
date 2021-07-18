import React, { useState, useEffect } from 'react';
import './ClientsList.scss';
import { CSSTransition } from 'react-transition-group';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory } from 'react-router';
import { NavLink } from 'react-router-dom';
import { AxiosResponse } from 'axios';
import { ClientResponse } from '../../../../api/models/response/client-response.model';
import ClientsAPI from '../../../../api/clients';

const activityStyle = {
    // backgroundImage: `url(${activityIcon})`
}

const ClientsList = (props:any) => {
    const history = useHistory()

    const [clientsList, setClientsList] = useState<ClientResponse[]>([]);

    const [req, doReq] = useState(false)

    const getClients = () => {
        doReq(true)
        ClientsAPI.getAllClients()
            .then((res:AxiosResponse<ClientResponse[]>) => {
                setClientsList(res.data)
            })
            .catch(err => console.log(err))
    }

    const toggleBlock = (client: ClientResponse) => {
        (client.isActive ?
            ClientsAPI.blockById(client.id) :
            ClientsAPI.unblockById(client.id)
        ).then((response:AxiosResponse<void>) => {
            let index = clientsList.findIndex(item => item.id === client.id)
            client.isActive = !client.isActive;
            setClientsList([...clientsList.slice(0,index), client, ...clientsList.slice(index + 1)])
        }).catch(err => console.log(err))
    }

    useEffect(() => {
        !req ? getClients() : null
    })

    return (
        <div className="clients-list">
            <table className="custom-table">
                <thead>
                    <tr>
                        <th>№</th>
                        <th>ФИО</th>
                        <th>E-mail</th>
                        <th>Организация</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {
                        clientsList.map(item => (
                            <React.Fragment key={item.id}>
                                <tr>
                                    <td aria-label="№">{ item.id }</td>
                                    <td aria-label="ФИО">{ item.fio }</td>
                                    <td aria-label="E-mail">{ item.email }</td>
                                    <td aria-label="Организация">{ item.fullNameOrg }</td>
                                    <td><button className="common-btn" onClick={() => history.push(`/admin/client/${item.id}`)}>Подробнее</button></td>
                                    <td><button className={`common-btn transparent-btn${!item.isActive ? ' blocked' : ''}`} onClick={() => toggleBlock(item)}>{ item.isActive ? 'ЗА' : 'РАЗ' }БЛОКИРОВАТЬ</button></td>
                                </tr>
                                <tr className="spacer"></tr>
                            </React.Fragment>
                        ))
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ClientsList;
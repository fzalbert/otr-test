import React, { useState, useEffect, useRef } from 'react';
import './AppealCreation.scss';
import { CSSTransition } from 'react-transition-group';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory, useParams } from 'react-router';
import { AxiosError, AxiosResponse } from 'axios';
import ArrowLeftIcon from '../../../assets/images/icons/arrow-left.svg';
import AppealsAPI from '../../../api/appeals';
import { AppealItemClientModel, FileItem } from '../../../api/models/response/appeals-response.model';
import ThemeAPI from '../../../api/theme';
import { ThemeResponse } from '../../../api/models/response/theme-response.model';
import useFormState from '../../../common/customHooks/useFormState';
import appendArray from '../../../common/styles/appendArray_form-data';
import TnvedAPI from '../../../api/tnved';
import { TnvedResponse } from '../../../api/models/response/tnved-response.model';
import { setTnvedList } from '../../../store/actions/tnved-actions';
import { setThemesList } from '../../../store/actions/themes-actions';
import { setCatCostList } from '../../../store/actions/cat-cost-actions';
import ErrorModal from '../../ui/ErrorModal/ErrorModal';

const AppealCreation = (props:any) => {
    const [subjectVision, toggleSubjectVision] = useState(false)
    const [subject, setSubject] = useState<number | null>(0)

    const [tnvedVision, toggleTnvedVision] = useState(false)
    const [tnved, setTnved] = useState(0)

    const [catCostVision, toggleCatCostVision] = useState(false)
    const [catCost, setCatCost] = useState(0)

    const [filesList, setFilesList] = useState<File[]>([])
    const [attachments, setAttachments] = useState<string[]>([])
    const [attachmentsForEdit, setAttachmentsForEdit] = useState<FileItem[]>([])
    const [fileName, setFileName] = useState('')
    const [fileUrl, setFileUrl] = useState('')
    const fileRef = useRef<HTMLAnchorElement>(null)

    const { id } = useParams<{id:string}>();
    const history = useHistory()
    const dispatch = useDispatch()

    const [error, setError] = useState("");

    const transormDate = (date: Date):string => {
        return `${date.getFullYear()}-${("0" + (date.getMonth() + 1)).slice(-2)}-${("0" + date.getDate()).slice(-2)}`
    }

    const [appealData, setAppealData] = useState<AppealItemClientModel>()

    const appeal = {
        themeId: useFormState(0),
        endDate: useFormState(transormDate(new Date(Date.now()))),
        tnvedId: useFormState(0),
        catCostId: useFormState(0),
        amount: useFormState(0),
        description: useFormState("")
    }

    const [reqAppeal, doReqAppeal] = useState(false)
    const [reqSubjects, doReqSubjects] = useState(false)
    const [reqTnved, doReqTnved] = useState(false)
    const [reqCatCost, doReqCatCost] = useState(false)

    const themesList = useSelector((state: any) => state.ThemesReducer)
    const tnvedList = useSelector((state: any) => state.TnvedReducer)
    const catCostList = useSelector((state: any) => state.TnvedReducer)

    const createAppeal = () => {
        let formData: FormData = new FormData();
        //filesList.length ? formData = appendArray(formData, filesList, 'file') : formData.append('file', '')
        filesList.forEach((f, i) => {
            formData.append('file', f, f.name);
        })
        formData.append('request', JSON.stringify({
            themeId: appeal.themeId.value,
            endDate: new Date(appeal.endDate.value),
            tnvedId: appeal.tnvedId.value,
            catCostId: appeal.catCostId.value,
            amount: appeal.amount.value,
            description: appeal.description.value
        }))
        console.log(formData)
        AppealsAPI.createAppeal(formData)
            .then((response:AxiosResponse<AppealItemClientModel>) => {
                history.push(`${location.hash.slice(2,7) === 'admin' ? '/admin' : ''}/appeals`)
                // history.push(`${location.hash.slice(2,7) === 'admin' ? '/admin' : ''}/appeal/${response.data.id}`)
            })
            .catch((err:AxiosError) => {
                console.log(err)
                setError(err.response?.data.message)
                setTimeout(() => setError(""), 5000);
            })
    }

    const updateAppeal = () => {
        const body = {
            themeId: +appeal.themeId.value,
            endDate: new Date(appeal.endDate.value),
            tnvedId: +appeal.tnvedId.value,
            catCostId: +appeal.catCostId.value,
            amount: +appeal.amount.value,
            description: appeal.description.value
        }
        AppealsAPI.updateAppeal(+id, body)
            .then((response: AxiosResponse<AppealItemClientModel>) => {
                history.push(`/admin/appeals`)
                uploadPhotos()
            })
            .catch((err:AxiosError) => {
                console.log(err)
                setError(err.response?.data.message)
                setTimeout(() => setError(""), 5000);
            })
    }

    const uploadPhotos = () => {
        const formData = new FormData();
        appendArray(formData, filesList, 'file')
        AppealsAPI.uploadAppealPhotos(+id, formData)
            .then((response: AxiosResponse<void>) => {

            }).catch((err:AxiosError) => {
                console.log(err)
                setError(err.response?.data.message)
                setTimeout(() => setError(""), 5000);
            })
    }

    const downloadPhotos = (id: number, fileName: string) => {
        AppealsAPI.downloadAppealFile(id)
            .then((response: AxiosResponse<Blob>) => {
                setFileUrl(URL.createObjectURL(response.data));
                setFileName(fileName)
                fileRef.current?.click();
            }).catch((err:AxiosError) => {
                console.log(err)
                setError(err.response?.data.message)
                setTimeout(() => setError(""), 5000);
            })
    }

    const getAppealById = () => {
        AppealsAPI.getAppealById(+id)
            .then((response:AxiosResponse<AppealItemClientModel>) => {
                doReqAppeal(true)
                response.data.createDate = new Date((response.data.createDate as string).slice(0,19))
                response.data.report ? response.data.report.createDate = new Date((response.data.report.createDate as string)?.slice(0,19)) : null
                setAppealData(response.data)
                appeal.amount.onChange({target: { value: response.data.amount }})
                appeal.description.onChange({target: { value: response.data.description }})
                appeal.themeId.onChange({target: { value: response.data.theme.id }})
                appeal.tnvedId.onChange({target: { value: response.data.tnved.id }})
                appeal.catCostId.onChange({target: { value: response.data.costCat.id }})
                appeal.endDate.onChange({target: { value: transormDate(new Date(response.data.endDate)) }})
                setCatCost(response.data.costCat.id)
                setTnved(response.data.tnved.id)
                setSubject(response.data.theme.id)
                setAttachmentsForEdit(response.data.files)
            })
            .catch((err:AxiosError) => {
                console.log(err)
                setError(err.response?.data.message)
                setTimeout(() => setError(""), 5000);
            })
    }

    const getSubjects = () => {
        ThemeAPI.getAllThemes()
            .then((response:AxiosResponse<ThemeResponse[]>) => {
                doReqSubjects(true)
                dispatch(setThemesList(response.data))
                if(!id) {
                    appeal.themeId.onChange({target: { value: response.data[0].id }})
                    setSubject(response.data[0].id)
                }
            })
            .catch(err => console.log(err))
    }

    const getTnvedList = () => {
        TnvedAPI.getAllTnved()
            .then((response:AxiosResponse<TnvedResponse[]>) => {
                doReqTnved(true)
                dispatch(setTnvedList(response.data))
                if(!id) {
                    appeal.tnvedId.onChange({target: { value: response.data[0].id }})
                    setTnved(response.data[0].id)
                }
            })
            .catch(err => console.log(err))
    }

    const getCatCostList = () => {
        AppealsAPI.getCatCostList()
            .then((response:AxiosResponse<{id:number, name: string}[]>) => {
                doReqCatCost(true)
                dispatch(setCatCostList(response.data))
                if(!id) {
                    appeal.catCostId.onChange({target: { value: response.data[0].id }})
                    setCatCost(response.data[0].id)
                }
            })
            .catch(err => console.log(err))
    }

    const onChangeFile = (event: any) => {
        event.persist()
        if (event.target && event.target.files && event.target.files[0]) {
            setFilesList((state: any) => [...state, ...Array.from(event.target.files)]);
            const reader = new FileReader();
            let images = event.target.files;
            let counter = 0;
            reader.readAsDataURL(images[counter])
            reader.onload = (e: any) => {
                console.log(e.target.result)
                setAttachments((state) => [...state, e.target.result]);
                counter < images.length - 1 ? reader.readAsDataURL(images[++counter]) : event.target.files = [];
            }
        }
    }

    useEffect(() => {
        !reqSubjects && !themesList?.length ? getSubjects() : null;
        !reqTnved && !tnvedList?.length ? getTnvedList() : null;
        !reqCatCost && !catCostList?.length ? getCatCostList() : null;
        !reqAppeal && id ? getAppealById() : null;
    }, [subject, appeal.description.value, appeal.endDate.value, appeal.catCostId.value, appeal.themeId.value, appeal.tnvedId.value, appeal.amount.value, filesList, attachments])

    return (
        <div className="appeal-creation-component">
            <CSSTransition in={error.length !== 0} timeout={300} unmountOnExit classNames="show-hide-animation">
                <ErrorModal>
                    { error }
                </ErrorModal>
            </CSSTransition>
            <button className="back-btn" onClick={() => history.goBack()}><img src={ArrowLeftIcon} alt="" /> Назад</button>
            <h1>{ location.hash.slice(2,5) === 'new' ? 'Новое обращение' : 'Редактирование обращения' }</h1>
            <label className="select-label" onFocus={() => toggleSubjectVision(!subjectVision)} onBlur={() => toggleSubjectVision(false)}>
                Тема обращения
                <input type="text" className="main-input" value={themesList?.find((item:ThemeResponse) => item.id === subject)?.name} placeholder="Выберите тему из списка предложенных" readOnly />
                {
                    subjectVision ?
                        <ul className="select-list">
                            { 
                                themesList.map((item: ThemeResponse) => 
                                    <li className="select-list_item" key={item.id} onMouseDown={() => {
                                        setSubject(item.id)
                                        appeal.themeId.onChange({target: { value: item.id }})
                                    }}>{ item.name }</li>
                                )
                            }
                        </ul> :
                        null
                }
            </label>
            <label>
                Текст обращения
                <textarea name="appeal-text" className="main-input" cols={30} rows={10} {...appeal.description} placeholder="Введите текст обращения"></textarea>
            </label>
                <div className="attachments-container">
                    <label className="attachment-label">
                        <input type="file" name="attachment" multiple onChange={(e) => onChangeFile(e)} />
                        <button>Прикрепить файл</button>
                    </label>
                    {
                        attachments.length || attachmentsForEdit.length ?
                            <div className="attachments">
                                {
                                    !id ?
                                        attachments.map((item, index) => (
                                            <div className="attachment" key={item.slice(40,50)}>
                                                <a href={item} download>{ filesList[index].name }</a>
                                            </div>
                                        )) :
                                        attachmentsForEdit.map((item, index) => (
                                            <div className="attachment" key={item.id}>
                                                <p onClick={() => downloadPhotos(item.id, item.name)}>{item.name.slice(0,66)}</p>
                                            </div>
                                        ))
                                }
                            </div> :
                            null
                    }
                    <a href={fileUrl} style={{opacity: 0}} download={fileName} ref={fileRef}></a>
                </div>
            <label>
                Срок реализации проекта
                <input type="date" className="main-input" {...appeal.endDate} placeholder="Введите срок" />
            </label>
            <label className="select-label" onFocus={() => toggleCatCostVision(!catCostVision)} onBlur={() => toggleCatCostVision(false)}>
                Вид категории затрат
                <input type="text" className="main-input" value={catCostList?.find((item:{id: number, name: string}) => item.id === catCost)?.name} placeholder="Введите категорию" readOnly />
                {
                    catCostVision ?
                        <ul className="select-list">
                            { 
                                catCostList.map((item: {id:number, name: string}) => 
                                    <li className="select-list_item" key={item.id} onMouseDown={() => {
                                        setCatCost(item.id)
                                        appeal.catCostId.onChange({target: { value: item.id }})
                                    }}>{ item.name }</li>
                                )
                            }
                        </ul> :
                        null
                }
            </label>
            <label className="select-label" onFocus={() => toggleTnvedVision(!tnvedVision)} onBlur={() => toggleTnvedVision(false)}>
                ТН ВЭД
                <input type="text" className="main-input" value={`${tnvedList?.find((item:ThemeResponse) => item.id === tnved)?.code} ${tnvedList?.find((item:ThemeResponse) => item.id === tnved)?.name}`} placeholder="Введите ТН ВЭД" readOnly />
                {
                    tnvedVision ?
                        <ul className="select-list">
                            { 
                                tnvedList.map((item: TnvedResponse) => 
                                    <li className="select-list_item" key={item.id} onMouseDown={() => {
                                        setTnved(item.id)
                                        appeal.tnvedId.onChange({target: { value: item.id }})
                                    }}>{ `${item.code} ${item.name}` }</li>
                                )
                            }
                        </ul> :
                        null
                }
            </label>
            <label>
                Сумма расходов по заявке
                <input type="text" className="main-input" {...appeal.amount} placeholder="Введите сумму расходов" />
            </label>
            <button className="common-btn" onClick={() => id ? updateAppeal() : createAppeal()}>{ id ? 'СОХРАНИТЬ' : 'СОЗДАТЬ'}</button>
        </div>
    )
}

export default AppealCreation;
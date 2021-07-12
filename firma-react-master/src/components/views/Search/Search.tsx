import React, { useState, useEffect } from 'react';
import './Search.scss';
import checkLoggedIn from '../../../common/checkLoggedIn';
import useDebounce from '../../../common/customHooks/useDebounce';
import { useParams, useHistory } from 'react-router';
import SearchAPI from '../../../api/search';
import { SubCategoriesResponse } from '../../../api/models/response/sub-categories-response.model';
import { ServantsResponse } from '../../../api/models/response/servants-response.model';
import { CategoriesResponse } from '../../../api/models/response/categories-response.model';
import { AxiosResponse } from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { setCategories } from '../../../store/actions/categories-actions';
import { setSubCategories } from '../../../store/actions/subcategories-actions';
import { setServants } from '../../../store/actions/servants-actions';
import DefaultTable from '../../ui/DefaultTable/DefaultTable';
import { SearchResponse } from '../../../api/models/response/search-response.model';
import { handleDelete } from './handle-delete';
import CategoriesAPI from '../../../api/categories';

const Search: React.FC = React.memo((props:any) => {

    checkLoggedIn();

    const history = useHistory();

    const categoriesState = useSelector((state:any) => state.CategoriesReducer);
    const subCategoriesState = useSelector((state:any) => state.SubCategoriesReducer);
    const dispatch = useDispatch();

    const [pageNumber, setPageNumber] = useState(1)
    const [req, doReq] = useState(false)

    const [searchType, setSearchType] = useState(0)

    const [searchString, setSearchString] = useState(undefined)
    const debouncedSearch = useDebounce(searchString, 500)

    const search = () => {
        getList(pageNumber, searchString)
    }

    const getList = (page?: number, search:string = '', type?: number) => {
        if(type !== undefined) {
            !type ? 
                CategoriesAPI.getCategories(8, page ? page : pageNumber, search)
                    .then((response:AxiosResponse<CategoriesResponse[]>) => {
                        doReq(true)
                        setSearchType(type)
                        if (response.data.length) {
                            page ? setPageNumber(page) : null;
                            dispatch(setCategories(response.data))
                        } else {
                            !page ? dispatch(setCategories(response.data)) : null
                        }
                    }) :
                CategoriesAPI.getSubCategories(8, page ? page : pageNumber, search)
                    .then((response:AxiosResponse<CategoriesResponse[]>) => {
                        doReq(true)
                        setSearchType(type)
                        if (response.data.length) {
                            page ? setPageNumber(page) : null;
                            dispatch(setSubCategories(response.data))
                        } else {
                            !page ? dispatch(setSubCategories(response.data)) : null
                        }
                    })
        } else {
            CategoriesAPI.getCategories(8, page ? page : pageNumber, search)
                .then((response:AxiosResponse<CategoriesResponse[]>) => {
                    doReq(true)
                    if (response.data.length) {
                        page ? setPageNumber(page) : null;
                        dispatch(setCategories(response.data))
                    } else {
                        !page ? dispatch(setCategories(response.data)) : null
                    }
                })
        }
        // SearchAPI.getList(8, page ? page : pageNumber, search, type !== undefined ? type : searchType)
        //     .then((response:AxiosResponse<SearchResponse>) => {
        //         doReq(true)
        //         if (type !== undefined) {
        //             setSearchType(type)
        //         }
        //         switch (type !== undefined ? type : searchType) {
        //             case 0:
        //                 if(response.data.categories.length) {
        //                     page ? setPageNumber(page) : null;
        //                     dispatch(setCategories(response.data.categories))
        //                 }
        //                 break;
        //             case 1:
        //                 if(response.data.subCategories.length) {
        //                     page ? setPageNumber(page) : null;
        //                     dispatch(setSubCategories(response.data.subCategories))
        //                 }
        //                 break;
        //             case 2:
        //                 if(response.data.servants.length) {
        //                     page ? setPageNumber(page) : null;
        //                     dispatch(setServants(response.data.servants))
        //                 }
        //                 break;
        //             default:
        //                 break;
        //         }
        //     })
        //     .catch(err => console.log(err))
    }

    const getTransformedList = (type:number) => {
        switch (type) {
            case 0:
                return categoriesState.length ? categoriesState?.map((item:CategoriesResponse) => ({
                    id: item.id,
                    name: item.name
                })) : []
            case 1:
                return subCategoriesState.length ? subCategoriesState?.map((item:SubCategoriesResponse) => ({
                    id: item.id,
                    name: item.name,
                    category: item.parentId
                })) : []
            // case 2:
            //     return servantsState?.map((item:ServantsResponse) => ({
            //         id: item.id,
            //         name: item.name,
            //         subcategory: item.subCategory.id
            //     }))
            default:
                break;
        }
    }

    const getHeadings = (type:number) => {
        switch (type) {
            case 0:
                return ['Categories']
            case 1:
                return ['Subcategory', 'Category id']
            // case 2:
            //     return ['Услуга', 'Subcategory id']
            default:
                break;
        }
    }

    const getSelectedList = (type:number) => {
        getList(pageNumber, searchString, type)
        switch (type) {
            case 0:
                history.push('/search/categories')
                break;
            case 1:
                history.push('/search/subcategories')
                break;
            default:
                // history.push('/search/servants')
                break;
        }
    }

    const getTableCaption = (type: number) => {
        switch (type) {
            case 0:
                return 'Категории'
            case 1:
                return 'Подкатегории'
            // case 2:
            //     return 'Услуги'
            default:
                break;
        }
    }

    const handleEditClick = (id:number) => {
        switch (searchType) {
            case 0:
                history.push(`/categories/category/${id}`)
                break;
            case 1:
                history.push(`/categories/category/${subCategoriesState.find((item:SubCategoriesResponse) => item.id === id).parentId}/subcategory/${id}`)
                break;
            // case 2:
            //     history.push(`/categories/category/${servantsState.find((item:ServantsResponse) => item.id === id).subCategory.category.id}/subcategory/${servantsState.find((item:ServantsResponse) => item.id === id).subCategory.id}/servant/${id}`)
            //     break;
            default:
                break;
        }
    }

    const onSelectPrevPage = () => {
        if (pageNumber > 1) {
            getList(pageNumber - 1, searchString, searchType)
        }
    }

    const onSelectNextPage = () => {
        getList(pageNumber + 1, searchString, searchType);
    }
    
    useEffect(() => {
        if (debouncedSearch !== undefined) {
            // Сделать запрос к АПИ
            search()
        } else if(!req) {
            getList()
        }
    }, [debouncedSearch, categoriesState, subCategoriesState]);

    return (
        <React.Fragment>
            <div className="search-head">
                <input type="text" name="search" className="search" onChange={(e:any) => setSearchString(e.target.value)} />
                <div className="select-row">
                    <button onClick={() => getSelectedList(0)} className="custom-option">Категории</button>
                    <button onClick={() => getSelectedList(1)} className="custom-option">Подкатегории</button>
                    {/* <button onClick={() => getSelectedList(2)} className="custom-option">Услуги</button> */}
                    <div className="selected-border" style={{left: searchType * 170 + 'px'}}></div>
                </div>
            </div>
            <div className="table-container">
                <DefaultTable
                    list={getTransformedList(searchType)}
                    realIndex={8 * (pageNumber - 1)}
                    handleEditRowClick={(id) => handleEditClick(id)}
                    handleDeleteRowClick={(id) => handleDelete(id, searchType, dispatch, history)}
                    headings={getHeadings(searchType)}
                    caption={getTableCaption(searchType)}
                    pagination={<div className="page-switches">
                        <button className="prev-page" onClick={onSelectPrevPage}></button>
                        <div>{pageNumber}</div>
                        <button className="next-page" onClick={onSelectNextPage}></button>
                    </div>}
                >
                    { searchType === 0 ? <button onClick={() => history.push('/categories/category')} className="main-btn">Добавить категорию</button> : null }
                </DefaultTable>
            </div>
        </React.Fragment>
    );
})

export default Search;
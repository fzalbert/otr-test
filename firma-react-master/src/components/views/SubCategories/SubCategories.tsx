import React, { useEffect, useState } from 'react';
import './SubCategories.scss';
import DefaultTable from '../../ui/DefaultTable/DefaultTable';
import { useHistory, useParams } from 'react-router';
import { useSelector, useDispatch } from 'react-redux';
import { AxiosResponse } from 'axios';
import checkLoggedIn from '../../../common/checkLoggedIn';
import CategoriesAPI from '../../../api/categories';
import { SubCategoriesResponse } from '../../../api/models/response/sub-categories-response.model';
import { setSubCategories, deleteSubCategory as buildDeleteSubCategory } from '../../../store/actions/subcategories-actions';
import SubCategoriesAPI from '../../../api/subcategories';
import useDebounce from '../../../common/customHooks/useDebounce';

const SubCategories: React.FC = React.memo((props:any) => {

    checkLoggedIn();

    const { id } = useParams<{id: string}>();
    const history = useHistory();
    const subCategoriesState = useSelector((state:any) => state.SubCategoriesReducer);
    const dispatch = useDispatch();

    const [pageNumber, setPageNumber] = useState(1)
    const [req, doReq] = useState(false)

    const [searchString, setSearchString] = useState(undefined)
    const debouncedSearch = useDebounce(searchString, 500)

    const search = () => {
        getSubCategoriesList(pageNumber, searchString)
    }

    const getSubCategoriesList = (page?: number, search:string = '') => {
        localStorage.setItem('categoryId', id ? id : '')
        CategoriesAPI.getSubCategoriesById(id ? +id : 0, 8, page ? page : pageNumber, search)
            .then((response:AxiosResponse<SubCategoriesResponse[]>) => {
                doReq(true)
                if (response.data.length) {
                    page ? setPageNumber(page) : null;
                    dispatch(setSubCategories(response.data))
                } else {
                    !page ? dispatch(setSubCategories(response.data)) : null
                }
            })
            .catch(err => console.log(err))
    }

    const deleteSubCategory = (ID:number) => {
        CategoriesAPI.deleteCategory(ID)
            .then((response:AxiosResponse) => {
                dispatch(buildDeleteSubCategory({id: ID ? +ID : 0}))
            })
            .catch(err => console.log(err))
    }

    const onSelectPrevPage = () => {
        if (pageNumber > 1) {
            getSubCategoriesList(pageNumber - 1)
        }
    }

    const onSelectNextPage = () => {
        getSubCategoriesList(pageNumber + 1);
    }

    useEffect(() => {
        id && (localStorage.getItem('categoryId') != id || !req) ? getSubCategoriesList() : null
        if (debouncedSearch !== undefined) {
            // Сделать запрос к АПИ
            search()
        } else if(!req) {
            getSubCategoriesList()
        }
    }, [debouncedSearch, localStorage.getItem('categoryId')]);

    return(
        <React.Fragment>
            <input type="text" name="search" className="search" onChange={(e:any) => setSearchString(e.target.value)} />
            <div className="table-container">
                <DefaultTable 
                    list={subCategoriesState?.map((item:SubCategoriesResponse) => ({
                        id: item.id,
                        name: item.name,
                        // imageUrl: item.imageUrl ? item.imageUrl : 'nothing',
                        category: item.parentId
                    }))}
                    realIndex={8 * (pageNumber - 1)}
                    // handleRowClick={(identificator) => history.push(`/categories/category/${id}/subcategory/${identificator}`)}
                    handleEditRowClick={(identificator) => history.push(`/categories/category/${id}/subcategory/${identificator}`)}
                    handleDeleteRowClick={(identificator) => deleteSubCategory(identificator)}
                    headings={['Subcategory', 'Category id']}
                    caption="Подкатегории"
                    pagination={<div className="page-switches">
                        <button className="prev-page" onClick={onSelectPrevPage}></button>
                        <div>{pageNumber}</div>
                        <button className="next-page" onClick={onSelectNextPage}></button>
                    </div>}
                >
                    {
                        <button onClick={() => history.push(`/categories/category/${id}/subcategory`)} className="main-btn">Добавить подкатегорию</button>
                    }
                </DefaultTable>
            </div>
        </React.Fragment>
    );
})

export default SubCategories;
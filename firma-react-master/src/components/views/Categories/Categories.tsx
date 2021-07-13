import React, { useEffect, useState } from 'react';
import './Categories.scss';
import DefaultTable from '../../ui/DefaultTable/DefaultTable';
import { useHistory } from 'react-router';
import { useSelector, useDispatch } from 'react-redux';
import { CategoriesResponse } from '../../../api/models/response/categories-response.model';
import { AxiosResponse } from 'axios';
import { setCategories, deleteCategory as buildDeleteCategory } from '../../../store/actions/categories-actions';
import checkLoggedIn from '../../../common/checkLoggedIn';
import CategoriesAPI from '../../../api/categories';
import useDebounce from '../../../common/customHooks/useDebounce';

const Categories: React.FC = React.memo((props:any) => {

    checkLoggedIn();

    const history = useHistory();
    const categoriesState = useSelector((state:any) => state.CategoriesReducer);
    const dispatch = useDispatch();

    const [pageNumber, setPageNumber] = useState(1)
    const [req, doReq] = useState(false)

    const [searchString, setSearchString] = useState(undefined)
    const debouncedSearch = useDebounce(searchString, 500)

    const search = () => {
        getCategoriesList(pageNumber, searchString)
    }

    const getCategoriesList = (page?: number, search:string = '') => {
        CategoriesAPI.getCategories(8, page ? page : pageNumber, search)
            .then((response:AxiosResponse<CategoriesResponse[]>) => {
                doReq(true)
                if(response.data.length) {
                    page ? setPageNumber(page) : null;
                    dispatch(setCategories(response.data))
                }
            })
            .catch(err => console.log(err))
    }

    const deleteCategory = (e:number) => {
        // e.preventDefault();
        CategoriesAPI.deleteCategory(e ? +e : 0)
            .then((response:AxiosResponse) => {
                dispatch(buildDeleteCategory({id: e ? +e : 0}))
            })
            .catch(err => console.log(err))
    }

    const onSelectPrevPage = () => {
        if (pageNumber > 1) {
            getCategoriesList(pageNumber - 1)
        }
    }

    const onSelectNextPage = () => {
        getCategoriesList(pageNumber + 1);
    }

    useEffect(() => {
        if(!req) {
            getCategoriesList()
        }
        if (debouncedSearch !== undefined) {
            // Сделать запрос к АПИ
            search()
        } else if(!req) {
            getCategoriesList()
        }
        // categoriesState.length ? console.log(categoriesState) : null
    }, [debouncedSearch]);

    return(
        <React.Fragment>
            <input type="text" name="search" className="search" onChange={(e:any) => setSearchString(e.target.value)} />
            <div className="table-container">
                <DefaultTable 
                    list={categoriesState.map((item:CategoriesResponse) => ({
                        id: item.id,
                        name: item.name
                        // imageUrl: item.imageUrl ? item.imageUrl : 'nothing'
                    }))}
                    // handleRowClick={(id) => history.push(`/categories/category/${id}`)}
                    realIndex={8 * (pageNumber - 1)}
                    handleEditRowClick={(id) => history.push(`/categories/category/${id}`)}
                    handleDeleteRowClick={(id) => deleteCategory(id)}
                    headings={['Category']}
                    caption="Категории"
                    pagination={<div className="page-switches">
                        <button className="prev-page" onClick={onSelectPrevPage}></button>
                        <div>{pageNumber}</div>
                        <button className="next-page" onClick={onSelectNextPage}></button>
                    </div>}
                >
                    <button onClick={() => history.push('/categories/category')} className="main-btn">Добавить категорию</button>
                </DefaultTable>
            </div>
        </React.Fragment>
    );
})

export default Categories;
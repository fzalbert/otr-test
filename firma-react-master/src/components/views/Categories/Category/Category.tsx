import React, { useEffect, useState } from 'react';
import './Category.scss';
import useFormState from '../../../../common/customHooks/useFormState';
import { useParams, useHistory } from 'react-router';
import { changeCategory as buildChangeCategory,
         deleteCategory as buildDeleteCategory,
         createCategory as buildCreateCategory  } from '../../../../store/actions/categories-actions';
import { AxiosResponse } from 'axios';
import { CategoriesResponse } from '../../../../api/models/response/categories-response.model';
import { useDispatch, useSelector } from 'react-redux';
import CategoriesAPI from '../../../../api/categories';
import SubCategories from '../../SubCategories/SubCategories';

const Category = () => {
    const { id } = useParams<{id: string}>();
    const history = useHistory();
    const dispatch = useDispatch();
    const categoriesState = useSelector((state:any) => state.CategoriesReducer);
    const [req, doReq] = useState(false);
    const [formFile, setFormFile] = useState('')

    const category = {
        name: useFormState(categoriesState?.find((item:CategoriesResponse) => id && item.id === +id)?.name)
    }

    const getCategoryData = () => {
        let cat = categoriesState?.find((item:CategoriesResponse) => id && item.id === +id)
        // cat ? setLocalState(cat) : null
    }

    const onSelectFile = (event:any) => {
        if (event.target.files && event.target.files[0]) {
            let reader: FileReader = new FileReader();
      
            const file: File = event.target.files[0];
      
            const files1 = event.target.files || event.srcElement.files;
            const file1 = files1[0];
      
            reader.readAsBinaryString(file); // read file as data url
            reader.onload = (event: any) => { // called once readAsDataURL is completed
                console.log(event)
                setFormFile(event.target.result);
      
            }
          }
    }

    const changeCategoryData = () => {
        let form = new FormData()
        console.log(formFile)
        form.append('Name', category.name.value)
        form.append('FormFile', formFile)
        CategoriesAPI.changeCategory(
            id ? +id : 0,
            form)
            .then((response:AxiosResponse<CategoriesResponse>) => {
                dispatch(buildChangeCategory({
                    id: id ? +id : 0,
                    name: category.name.value
                }));
            })
            .catch(err => console.log(err))
    }

    const createCategory = () => {
        let form = new FormData()
        console.log(formFile)
        form.append('Name', category.name.value)
        form.append('FormFile', formFile)
        form.append('ParentId', 0 + '')
        CategoriesAPI.createCategory(form)
            .then((response:AxiosResponse<CategoriesResponse>) => {
                dispatch(buildCreateCategory(response.data));
                history.push(`/categories/category/${response.data.id}`)
            })
            .catch(err => console.log(err))
    }

    const deleteCategory = (e:any) => {
        e.preventDefault();
        CategoriesAPI.deleteCategory(id ? +id : 0)
            .then((response:AxiosResponse) => {
                dispatch(buildDeleteCategory({id: id ? +id : 0}))
                setTimeout(()=>history.push('/search'),0)
            })
            .catch(err => console.log(err))
    }

    const submitForm = (e:React.FormEvent) => {
        e.preventDefault();
        id ? changeCategoryData() : createCategory();
    }

    useEffect(() => {
        if(!req && id !== undefined) {
            getCategoryData();
        }
    })
 
    return(
        <React.Fragment>
            <div className="container">
                <div className="category">
                    <h2>Категория #{id ? id: 'new'}</h2>
                    <form className="category-form" noValidate onSubmit={(e) => submitForm(e)}>
                        <input type="text" {...category.name} className="main-input" placeholder="Категория" />
                        <input type="file" onChange={onSelectFile} className="main-input" placeholder="File" />
                        {/* <input type="text" {...testRestaurant.regLink} className="main-input" placeholder="Ссылка для регистрации" /> */}
                        <button type="submit" className="main-btn">{ id != undefined ? 'Сохранить' : 'Добавить' }</button>
                        {id !== undefined ? <a onClick={deleteCategory}>Удалить категорию</a> : null }
                    </form>
                </div>
                <div className="subcategories-container">
                    <SubCategories />
                </div>
            </div>
        </React.Fragment>
    );
}

export default Category;
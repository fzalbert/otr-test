import React, { useEffect, useState } from 'react';
import './SubCategory.scss';
import useFormState from '../../../../common/customHooks/useFormState';
import { useParams, useHistory } from 'react-router';
import { changeSubCategory as buildChangeSubCategory,
         deleteSubCategory as buildDeleteSubCategory,
         createSubCategory as buildCreateSubCategory  } from '../../../../store/actions/subcategories-actions';
import { AxiosResponse } from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { SubCategoriesResponse } from '../../../../api/models/response/sub-categories-response.model';
import CategoriesAPI from '../../../../api/categories';

const SubCategory = () => {
    const { id } = useParams<{id: string}>();
    const { subCategoryId } = useParams<{subCategoryId: string}>();
    const history = useHistory();
    const dispatch = useDispatch();
    const subCategoriesState = useSelector((state:any) => state.SubCategoriesReducer);
    const [req, doReq] = useState(false);
    const [formFile, setFormFile] = useState('')

    const subcategory = {
        name: useFormState(subCategoriesState?.find((item:SubCategoriesResponse) => subCategoryId && item.id === +subCategoryId)?.name),
        categoryId: useFormState(id ? +id : '')
    }

    // const setLocalState = (state:SubCategoriesResponse) => {
    //     subcategory.name.onChange({target: {value: state?.name}})
    // }

    const getSubCategoryData = () => {
        let cat = subCategoriesState?.find((item:SubCategoriesResponse) => subCategoryId && item.id === +subCategoryId)
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

    const changeSubCategoryData = () => {
        let form = new FormData()
        console.log(formFile)
        form.append('Name', subcategory.name.value)
        form.append('FormFile', formFile)
        form.append('ParentId', subcategory.categoryId.value)
        CategoriesAPI.changeCategory(
            subCategoryId ? +subCategoryId : 0,
            form)
            .then((response:AxiosResponse<SubCategoriesResponse>) => {
                dispatch(buildChangeSubCategory({
                    id: subCategoryId ? +subCategoryId : NaN,
                    name: subcategory.name.value,
                    parentId: +subcategory.categoryId
                }));
            })
            .catch(err => console.log(err))
    }

    const createSubCategory = () => {
        let form = new FormData()
        console.log(formFile)
        form.append('Name', subcategory.name.value)
        form.append('FormFile', formFile)
        form.append('ParentId', id ? id : '')
        CategoriesAPI.createCategory(form)
            .then((response:AxiosResponse<SubCategoriesResponse>) => {
                dispatch(buildCreateSubCategory(response.data));
                history.push(`/categories/category/${id}/subcategory/${response.data.id}`)
            })
            .catch(err => console.log(err))
    }

    const deleteSubCategory = (e:any) => {
        e.preventDefault();
        CategoriesAPI.deleteCategory(subCategoryId ? +subCategoryId : 0)
            .then((response:AxiosResponse) => {
                dispatch(buildDeleteSubCategory({id: subCategoryId ? +subCategoryId : 0}))
                setTimeout(() => history.push(`/categories/category/${id}`), 0)
            })
            .catch(err => console.log(err))
    }

    const submitForm = (e:React.FormEvent) => {
        e.preventDefault();
        subCategoryId ? changeSubCategoryData() : createSubCategory();
    }

    useEffect(() => {
        if(!req && id !== undefined) {
            getSubCategoryData();
        }
    })

    return(
        <React.Fragment>
            <div className="container">
                <div className="category subcategory">
                    <h2>Подкатегория #{subCategoryId || 'new'}</h2>
                    <form className="category-form subcategory-form" noValidate onSubmit={(e) => submitForm(e)}>
                        <input type="text" {...subcategory.name} className="main-input" placeholder="Подкатегория" />
                        <div className="main-input_with-label">
                            <input type="text" id="categoryId" {...subcategory.categoryId} className="main-input" />
                            <label htmlFor="categoryId" className={subcategory.categoryId.value === '' ? 'null' : 'filled-input_label'}>ID Категории</label>
                        </div>
                        <input type="file" onChange={onSelectFile} className="main-input" placeholder="File" />
                        {/* <input type="text" {...testRestaurant.regLink} className="main-input" placeholder="Ссылка для регистрации" /> */}
                        <button type="submit" className="main-btn">{ subCategoryId != undefined ? 'Сохранить' : 'Добавить' }</button>
                        {id !== undefined ? <a onClick={deleteSubCategory}>Удалить подкатегорию</a> : null }
                    </form>
                </div>
                {/* <div className="tasks-container">
                    <Tasks />
                </div> */}
            </div>
        </React.Fragment>
    );
}

export default SubCategory;
const appendArray = (form_data: FormData, values: any[] | FileList, name: string) => {
    if(!values && name)
        form_data.append(name, '');
    else {
        if(typeof values == 'object') {
            for(const key in values) {
                typeof values[key] == 'object' ?
                    appendArray(form_data, values[key], name + '[' + key + ']') :
                    form_data.append(name + '[' + key + ']', values[key]);
            }
        } else form_data.append(name, values);
    }
    return form_data;
}

export default appendArray;
import React, { useState } from "react";
import './DefaultTable.scss';
import Moment from 'react-moment';
import sortTableIcon from '../../../assets/images/icons/sort-table-icon.svg';
import editIcon from '../../../assets/images/icons/edit.svg';
import deleteIcon from '../../../assets/images/icons/delete.svg';

interface DefaultTableProps {
    list: any[] | [];
    caption?:string;
    headings?:any[];
    invisibleIndex?:boolean;
    realIndex?:number;
    handleRowClick?:(id:any) => any;
    handleEditRowClick?:(id: any) => any;
    handleDeleteRowClick?:(id: any) => any;
    children?:any; // Just for filter
    pagination?:any;
}

const DefaultTable = (props:DefaultTableProps) => {
    const caption = () => props.caption ? <h2>{props.caption}</h2> : null;

    if(!props.list || !props.list.length) {
        return (
            <React.Fragment>
                <div className="table-caption">
                    {caption()}
                    {props?.children}
                </div>
                <p className="empty-table">Table is empty</p>
            </React.Fragment>
        );
    }

    let headings:string[]=[];
    let headingsList:any[]=[];

    // Sorting
    const [tableSortProps, setTableSortProps] = useState<{heading:string,sequence:string, type:string}[]>([]); // Sorting state

    const generateSortState = () => {
                // Here typeof
        let st:any[]=[];
                headings
                    .forEach((heading:string) => {
                        if(typeof(props.list[0][heading]) === 'number' && heading != 'index' && !heading.toLowerCase().includes('id') || 
                        (props.list[0][heading]?.getDay && typeof(props.list[0][heading]) === 'object')) {
                            st.push({heading: heading, sequence: 'increasing', type: 'number'})
                        } else {
                            st.push({heading: heading, sequence: 'increasing', type: 'string'})
                        }
                    })
                    // Only for number type
                    // .map(h => ({heading: h, sequence: 'increasing'}));
                    // .filter((heading:string) => 
                    //         typeof(props.list[0][heading]) === 'number' && heading != 'index' && !heading.toLowerCase().includes('id') || 
                    //         (props.list[0][heading]?.getDay && typeof(props.list[0][heading]) === 'object'))
                    // .map(h => ({heading: h, sequence: 'increasing'}));
        st.length ? setTableSortProps(st) : null;
    }

    const sortList = (h:string) => {
        const headingStateIndex = tableSortProps.findIndex(item => item.heading === h);
        const st = [...tableSortProps];

        if(tableSortProps[headingStateIndex].type === 'number') {
            if (tableSortProps[headingStateIndex].sequence === 'increasing') {
                rowsList = generateRowsList(props.list.sort((a, b) => a[h] - b[h]))
                st[headingStateIndex].sequence = 'decreasing';
                setTableSortProps(st);
            } else {
                rowsList = generateRowsList(props.list.sort((a, b) => b[h] - a[h]));
                st[headingStateIndex].sequence = 'increasing';
                setTableSortProps(st);
            }
        } else {
            // For string params
            if (tableSortProps[headingStateIndex].sequence === 'increasing') {
                rowsList = generateRowsList(props.list.sort((a,b) => a[h] < b[h] ? -1 : 1))
                st[headingStateIndex].sequence = 'decreasing';
                setTableSortProps(st);
            } else {
                rowsList = generateRowsList(props.list.sort((a,b) => b[h] < a[h] ? -1 : 1));
                st[headingStateIndex].sequence = 'increasing';
                setTableSortProps(st);

            }
        }
    }

    // Headings List
    const getHeadingsList = (th:string[], customHeadings?:string[]) => {
        if(tableSortProps.length === 0) {
            generateSortState(); // Get initial state for sorting
        }
        
        let headingListCells = (customHeadings != undefined ? customHeadings : th).map((heading, index) => 
            typeof(props.list[0][th[index]]) === 'number' || (props.list[0][th[index]]?.getDate && typeof(props.list[0][th[index]]) === 'object') ?
                <th key={th[index].slice(0,5)} onClick={() => sortList(th[index])} className="sortable-heading">{heading}&nbsp;<img src={sortTableIcon} alt="arrow-icon" /></th> :
                <th key={th[index].slice(0,5)} onClick={() => sortList(th[index])} className="sortable-heading">{heading}&nbsp;<img src={sortTableIcon} alt="arrow-icon" /></th>);
            
        props.handleEditRowClick ? headingListCells.push(<th key="edit" style={{width: '30px'}}></th>) : null
        props.handleDeleteRowClick ? headingListCells.push(<th key="delete" style={{width: '30px'}}></th>) : null

        return headingListCells;
    };

    // Build headings
    // check if there are custom headings
    const buildHeadings = () => {
        headings = props.invisibleIndex ? [...Object.keys(props.list[0])] : ['#', ...Object.keys(props.list[0])];
        
        if(!props.headings || !props.headings.length) {
            headingsList = getHeadingsList(headings.filter(h => !h.toLowerCase().includes('id')));
        } else {
            const customHeadings =  props.invisibleIndex ? [...props.headings] : ['#', ...props.headings];
            headingsList = getHeadingsList(headings.filter(h => !h.toLowerCase().includes('id')), customHeadings);
        }
    }

    // Rating Cell
    const generateRatingStars = (data:any, key:string) => {
        const ratings = [];
        let i = data[key];
        while(i > 0) {
            i > 1 ? ratings.push({value: 1, itemKey: i*111}) : ratings.push({value: i, itemKey: i*111});
            i--;
        }
        for(let i = ratings.length; i < 5; i++) {
            ratings.push({value: 0, itemKey: ratings.length * 11});
        }

        return(
            <td key={key}>
                <div className="ratings-container">
                    <div className="rating-stars-container">
                        {ratings.map((star) => {
                            return <span className="star" key={star.itemKey}
                                style={{
                                    background: 'linear-gradient(90deg, ' + 
                                    ((star.value === 0) ? 
                                        '#EEEEEE 0%, #EEEEEE 100%)' : 
                                        '#81FEC6 0%, ' + ((star.value === 1) ? ('#81FEC6 100%)') : 
                                        ('#81FEC6 ' + (star.value * 100) + '%, #EEEEEE' + (star.value * 100 + 0.1) + '%, ' + '#EEEEEE 100%)')))
                                }}>
                            </span>
                        })}
                    </div>
                    <p>{ data[key].toFixed(2) }</p>     
                </div>
            </td>
        );
    }

    const generateRowsList = (list:any[]) => list.map((row: typeof props.list[0], rowIndex:number) => {
        row = props.invisibleIndex ?
            {...row} :
            {index: props.realIndex === undefined ? rowIndex + 1 : props.realIndex + rowIndex + 1, ...row}; // index for row order
        const cells = Object.entries(row)
                            .map(([key, value]:[string, any]) => {
                                if (key === 'id' || key.includes('Id')) {
                                    return;
                                }
                                if(key === 'avatar') {
                                    return (
                                        <td key={key + row.id} className="avatar-cell">
                                            {value.length ? <img src={value} alt=""/> : <div className="avatar-null"></div>}
                                        </td>
                                    )
                                }
                                if(typeof value === 'boolean') {
                                    return <td key={key + row.id} className={value ? 'confirmed-row' : 'not-confirmed-row'}><span></span></td>
                                }
                                // Check if there is rating cell, that need to be rendered like stars
                                if(key.toLowerCase().includes('rating') || key.toLowerCase().includes('stars')) {
                                    return generateRatingStars(row, key);
                                }
                                // If there is date property
                                if(value?.getDate !== undefined && value.getTimezoneOffset) {
                                    return <td key={key + row.id}>
                                        <Moment format="DD/MM/YYYY">{value}</Moment>
                                    </td>;
                                }
                                // Simple value
                                return <td key={key + row.id}>{value}</td>;
                            });

        props.handleEditRowClick ? cells.push(<td className="edit-cell" key={'edit-cell'} onClick={() => props.handleEditRowClick ? props.handleEditRowClick(row.id) : null}><img src={editIcon} alt=""/></td>) : null
        props.handleDeleteRowClick ? cells.push(<td className="delete-cell" key={'delete-cell'} onClick={(e) => {
            e.preventDefault()
            e.stopPropagation()
            props.handleDeleteRowClick ? props.handleDeleteRowClick(row.id) : null}
        }><img src={deleteIcon} alt=""/></td>) : null

        return props.handleRowClick != undefined ?
                                    <tr className="default-table_data-row"
                                        key={row.id} 
                                        style={{cursor: 'pointer'}}
                                        onClick={() => props.handleRowClick ? props.handleRowClick(row.id) : null}>{cells}</tr> :
                                    <tr className="default-table_data-row" key={row.id}>{cells}</tr>
    });

    buildHeadings();
    // values
    let rowsList = generateRowsList(props.list);

    return (
        <React.Fragment>
            <div className="default-table-wrapper">
                <div className="table-caption">
                    {caption()}
                    {props.children}
                </div>
                <div className="default-table-container">
                    <table className="default-table">
                        <thead>
                            <tr className="default-table_headings-row">
                                {headingsList}
                            </tr>
                        </thead>
                        <tbody>
                            {rowsList}
                        </tbody>
                    </table>
                </div>
                <div className="pagination">
                    {props.pagination ? props.pagination : null}
                </div>
            </div>

            {/* <div className="switches-container">
                <button className="prev-btn">
                    <svg width="6" height="8" viewBox="0 0 6 8" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path fillRule="evenodd" clipRule="evenodd" d="M0.92831 3.24063L4.63633 0.0756431C4.7618 -0.0140025 4.91762 0.000932952 5.03529 0.000932952C5.50599 0.000932952 5.50391 0.437028 5.50391 0.547511V7.10658C5.50391 7.19998 5.50597 7.65319 5.03529 7.65319C4.91762 7.65319 4.76178 7.66806 4.63633 7.57844L0.928338 4.32524C0.579954 4.07649 0.640152 3.78292 0.640152 3.78292C0.640152 3.78292 0.579926 3.48935 0.92831 3.24063Z" fill="#075497"/>
                    </svg>
                </button>
                <button>{Math.trunc(props.list.length / 10 + 1)}</button>
                <button className="next-btn">
                    <svg width="6" height="8" viewBox="0 0 6 8" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path fillRule="evenodd" clipRule="evenodd" d="M5.07169 3.24063L1.36367 0.0756431C1.2382 -0.0140025 1.08238 0.000932952 0.964709 0.000932952C0.494006 0.000932952 0.496094 0.437028 0.496094 0.547511V7.10658C0.496094 7.19998 0.494033 7.65319 0.964709 7.65319C1.08238 7.65319 1.23822 7.66806 1.36367 7.57844L5.07166 4.32524C5.42005 4.07649 5.35985 3.78292 5.35985 3.78292C5.35985 3.78292 5.42007 3.48935 5.07169 3.24063Z" fill="#075497"/>
                    </svg>
                </button>
            </div> */}
        </React.Fragment>
    );
}

export default DefaultTable;
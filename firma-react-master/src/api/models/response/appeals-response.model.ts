export interface AppealItemModel {
    employeeId: number;
    createDate: Date;
    description: string;
    id: number;
    statusAppeal: number;
    theme: Theme;
    nameOrg: string;
}

export class AppealItemClientModel {
    constructor() {}
    public amount: number=0;
    public clientId: number=0;
    public createDate: Date | string = new Date(Date.now());
    public description: string = "";
    public endDate: Date = new Date(Date.now());
    public files: FileItem[]=[];
    public id: number=0;
    public startDate: Date = new Date(Date.now());
    public statusAppeal: number=0;
    public costCat: { id:number, name: string }={ id:0, name: "" };
    public report?: Report;
    public theme: Theme={id:0,name: ""};
    public tnved: Tnved={id:0,code:"",name:""};
    public lastTask: TaskClass = new TaskClass();
}

export class TaskClass {
    constructor() {}
    public id: number=0;
    public appealId: number=0;
    public employeeId: number=0;
    public isOver: boolean=false;
    public date: Date | string="";
    public taskStatus: TaskStatusEnum = 0
} 

export enum TaskStatusEnum {
    NEEDCHECK = 0,
    NEEDUPDATE = 1,
    NEEDREJECT = 2,
    NEEDSUCCESS = 3
}

export interface File {
    appealId: number;
    id: number;
    name: string;
    size: number;
    type: string;
    url: string;
}

export interface Tnved {
    code: string;
    id: number;
    name: string;
}

export interface Theme {
    id: number;
    name: string;
}

export interface Report {
    appealId: number;
    createDate: Date| string;
    employeeId: number;
    id: number;
    reportStatus: number;
    text: string;
}

export interface FileItem {
    appealId: number;
    id: number;
    name: string;
    type: string;
    url: string;
}
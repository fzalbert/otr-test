export class FilterRequest {
    constructor() {}
    public themeId: number | null=null;
    public date: Date | null = null;
    public statusAppeal: number | null = null;
    public employeeId?: number | null = null;
    public nameOrg?: string | null = "";
}
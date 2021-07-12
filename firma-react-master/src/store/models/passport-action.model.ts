import { PassportResponse } from "../../api/models/response/passport-response.model";

export interface SetPassportAction extends PassportResponse {}

export interface VerifyPassportAction {
    passportId: number;
    dateDeadline: string;
    type: number;
}
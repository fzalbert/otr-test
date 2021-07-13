export class RegistrationRequest {
    constructor(
        public firstName:string,
        public secondName:string,
        public thirdName:string,
        public email:string,
        public password:string,
        public address:string,
        public passport:string
    ) {}
}
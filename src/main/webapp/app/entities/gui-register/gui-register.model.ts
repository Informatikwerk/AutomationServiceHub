import { BaseEntity } from './../../shared';

export class GuiRegister implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public js_file?: any,
        public html_file?: any,
    ) {
    }
}

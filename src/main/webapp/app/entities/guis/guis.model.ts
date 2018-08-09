import { BaseEntity } from './../../shared';

export class Guis implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public jsFile?: any,
        public htmlFile?: any,
    ) {
    }
}

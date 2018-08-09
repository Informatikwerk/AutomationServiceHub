import { BaseEntity } from './../../shared';

export class LibraryRegistry implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public author?: string,
        public description?: string,
        public platform?: string,
        public version?: string,
        public url?: string,
        public userId?: number
    ) {
    }
}

import { BaseEntity } from './../../shared';

export class Sources implements BaseEntity {
    constructor(
        public id?: number,
        public sourceCode?: any,
        public fileName?: string,
        public libraryRegistry?: BaseEntity,
    ) {
    }
}

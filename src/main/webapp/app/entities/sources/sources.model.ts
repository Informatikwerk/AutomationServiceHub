import { BaseEntity } from './../../shared';

export class Sources implements BaseEntity {
    constructor(
        public id?: number,
        public sourceCode?: any,
        public libraryRegistry?: BaseEntity,
    ) {
    }
}

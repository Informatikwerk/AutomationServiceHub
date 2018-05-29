import { BaseEntity } from './../../shared';

export class NodesMeta implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public description?: string,
        public iconUrl?: string,
        public label?: string,
        public actionElements?: any,
        public valueElements?: any,
    ) {
    }
}

import { BaseEntity } from './../../shared';

export class NodeMetaRegistry implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public description?: string,
        public icon_url?: string,
        public label?: string,
        public action_elements?: any,
        public value_elements?: any,
    ) {
    }
}

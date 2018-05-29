import { BaseEntity } from './../../shared';

export class NodeRegistry implements BaseEntity {
    constructor(
        public id?: number,
        public ip?: string,
        public reaml_key?: string,
        public type?: string,
    ) {
    }
}

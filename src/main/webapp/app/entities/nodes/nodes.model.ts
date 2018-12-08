import { BaseEntity } from './../../shared';

export class Nodes implements BaseEntity {
    constructor(
        public id?: number,
        public ip?: string,
        public realmKey?: string,
        public type?: string,
        public nodeName?: string,
        public nodeId?: string,
    ) {
    }
}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutomationServiceHubSharedModule } from '../../shared';
import {
    NodesMetaService,
    NodesMetaPopupService,
    NodesMetaComponent,
    NodesMetaDetailComponent,
    NodesMetaDialogComponent,
    NodesMetaPopupComponent,
    NodesMetaDeletePopupComponent,
    NodesMetaDeleteDialogComponent,
    nodesMetaRoute,
    nodesMetaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...nodesMetaRoute,
    ...nodesMetaPopupRoute,
];

@NgModule({
    imports: [
        AutomationServiceHubSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NodesMetaComponent,
        NodesMetaDetailComponent,
        NodesMetaDialogComponent,
        NodesMetaDeleteDialogComponent,
        NodesMetaPopupComponent,
        NodesMetaDeletePopupComponent,
    ],
    entryComponents: [
        NodesMetaComponent,
        NodesMetaDialogComponent,
        NodesMetaPopupComponent,
        NodesMetaDeleteDialogComponent,
        NodesMetaDeletePopupComponent,
    ],
    providers: [
        NodesMetaService,
        NodesMetaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutomationServiceHubNodesMetaModule {}

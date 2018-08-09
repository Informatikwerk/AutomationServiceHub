import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutomationServiceHubSharedModule } from '../../shared';
import {
    NodesService,
    NodesPopupService,
    NodesComponent,
    NodesDetailComponent,
    NodesDialogComponent,
    NodesPopupComponent,
    NodesDeletePopupComponent,
    NodesDeleteDialogComponent,
    nodesRoute,
    nodesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...nodesRoute,
    ...nodesPopupRoute,
];

@NgModule({
    imports: [
        AutomationServiceHubSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NodesComponent,
        NodesDetailComponent,
        NodesDialogComponent,
        NodesDeleteDialogComponent,
        NodesPopupComponent,
        NodesDeletePopupComponent,
    ],
    entryComponents: [
        NodesComponent,
        NodesDialogComponent,
        NodesPopupComponent,
        NodesDeleteDialogComponent,
        NodesDeletePopupComponent,
    ],
    providers: [
        NodesService,
        NodesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutomationServiceHubNodesModule {}

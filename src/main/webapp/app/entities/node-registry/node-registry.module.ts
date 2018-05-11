import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutomationServiceHubSharedModule } from '../../shared';
import {
    NodeRegistryService,
    NodeRegistryPopupService,
    NodeRegistryComponent,
    NodeRegistryDetailComponent,
    NodeRegistryDialogComponent,
    NodeRegistryPopupComponent,
    NodeRegistryDeletePopupComponent,
    NodeRegistryDeleteDialogComponent,
    nodeRegistryRoute,
    nodeRegistryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...nodeRegistryRoute,
    ...nodeRegistryPopupRoute,
];

@NgModule({
    imports: [
        AutomationServiceHubSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NodeRegistryComponent,
        NodeRegistryDetailComponent,
        NodeRegistryDialogComponent,
        NodeRegistryDeleteDialogComponent,
        NodeRegistryPopupComponent,
        NodeRegistryDeletePopupComponent,
    ],
    entryComponents: [
        NodeRegistryComponent,
        NodeRegistryDialogComponent,
        NodeRegistryPopupComponent,
        NodeRegistryDeleteDialogComponent,
        NodeRegistryDeletePopupComponent,
    ],
    providers: [
        NodeRegistryService,
        NodeRegistryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutomationServiceHubNodeRegistryModule {}

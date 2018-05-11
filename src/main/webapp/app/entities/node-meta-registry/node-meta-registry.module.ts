import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutomationServiceHubSharedModule } from '../../shared';
import {
    NodeMetaRegistryService,
    NodeMetaRegistryPopupService,
    NodeMetaRegistryComponent,
    NodeMetaRegistryDetailComponent,
    NodeMetaRegistryDialogComponent,
    NodeMetaRegistryPopupComponent,
    NodeMetaRegistryDeletePopupComponent,
    NodeMetaRegistryDeleteDialogComponent,
    nodeMetaRegistryRoute,
    nodeMetaRegistryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...nodeMetaRegistryRoute,
    ...nodeMetaRegistryPopupRoute,
];

@NgModule({
    imports: [
        AutomationServiceHubSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NodeMetaRegistryComponent,
        NodeMetaRegistryDetailComponent,
        NodeMetaRegistryDialogComponent,
        NodeMetaRegistryDeleteDialogComponent,
        NodeMetaRegistryPopupComponent,
        NodeMetaRegistryDeletePopupComponent,
    ],
    entryComponents: [
        NodeMetaRegistryComponent,
        NodeMetaRegistryDialogComponent,
        NodeMetaRegistryPopupComponent,
        NodeMetaRegistryDeleteDialogComponent,
        NodeMetaRegistryDeletePopupComponent,
    ],
    providers: [
        NodeMetaRegistryService,
        NodeMetaRegistryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutomationServiceHubNodeMetaRegistryModule {}

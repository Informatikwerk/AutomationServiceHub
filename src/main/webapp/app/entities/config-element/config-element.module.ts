import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutomationServiceHubSharedModule } from '../../shared';
import {
    ConfigElementService,
    ConfigElementPopupService,
    ConfigElementComponent,
    ConfigElementDetailComponent,
    ConfigElementDialogComponent,
    ConfigElementPopupComponent,
    ConfigElementDeletePopupComponent,
    ConfigElementDeleteDialogComponent,
    configElementRoute,
    configElementPopupRoute,
} from './';

const ENTITY_STATES = [
    ...configElementRoute,
    ...configElementPopupRoute,
];

@NgModule({
    imports: [
        AutomationServiceHubSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ConfigElementComponent,
        ConfigElementDetailComponent,
        ConfigElementDialogComponent,
        ConfigElementDeleteDialogComponent,
        ConfigElementPopupComponent,
        ConfigElementDeletePopupComponent,
    ],
    entryComponents: [
        ConfigElementComponent,
        ConfigElementDialogComponent,
        ConfigElementPopupComponent,
        ConfigElementDeleteDialogComponent,
        ConfigElementDeletePopupComponent,
    ],
    providers: [
        ConfigElementService,
        ConfigElementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutomationServiceHubConfigElementModule {}

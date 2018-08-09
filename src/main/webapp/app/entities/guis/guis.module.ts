import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutomationServiceHubSharedModule } from '../../shared';
import {
    GuisService,
    GuisPopupService,
    GuisComponent,
    GuisDetailComponent,
    GuisDialogComponent,
    GuisPopupComponent,
    GuisDeletePopupComponent,
    GuisDeleteDialogComponent,
    guisRoute,
    guisPopupRoute,
} from './';

const ENTITY_STATES = [
    ...guisRoute,
    ...guisPopupRoute,
];

@NgModule({
    imports: [
        AutomationServiceHubSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GuisComponent,
        GuisDetailComponent,
        GuisDialogComponent,
        GuisDeleteDialogComponent,
        GuisPopupComponent,
        GuisDeletePopupComponent,
    ],
    entryComponents: [
        GuisComponent,
        GuisDialogComponent,
        GuisPopupComponent,
        GuisDeleteDialogComponent,
        GuisDeletePopupComponent,
    ],
    providers: [
        GuisService,
        GuisPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutomationServiceHubGuisModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutomationServiceHubSharedModule } from '../../shared';
import {
    SourcesService,
    SourcesPopupService,
    SourcesComponent,
    SourcesDetailComponent,
    SourcesDialogComponent,
    SourcesPopupComponent,
    SourcesDeletePopupComponent,
    SourcesDeleteDialogComponent,
    sourcesRoute,
    sourcesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sourcesRoute,
    ...sourcesPopupRoute,
];

@NgModule({
    imports: [
        AutomationServiceHubSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SourcesComponent,
        SourcesDetailComponent,
        SourcesDialogComponent,
        SourcesDeleteDialogComponent,
        SourcesPopupComponent,
        SourcesDeletePopupComponent,
    ],
    entryComponents: [
        SourcesComponent,
        SourcesDialogComponent,
        SourcesPopupComponent,
        SourcesDeleteDialogComponent,
        SourcesDeletePopupComponent,
    ],
    providers: [
        SourcesService,
        SourcesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutomationServiceHubSourcesModule {}

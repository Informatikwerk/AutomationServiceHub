import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutomationServiceHubSharedModule } from '../../shared';
import {
    LibraryService,
    LibraryComponent,
    LibraryRoute,
} from './';

const PAGE_SET_STATES = [
    ...LibraryRoute,
];

@NgModule({
    imports: [
        AutomationServiceHubSharedModule,
        RouterModule.forRoot(PAGE_SET_STATES, { useHash: true })
    ],
    declarations: [
    LibraryComponent,
],
    entryComponents: [
    LibraryComponent,
],
    providers: [
    LibraryService,
],
schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class AutomationServiceHubLibraryModule {}

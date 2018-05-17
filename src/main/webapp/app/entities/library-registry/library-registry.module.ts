import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FilterPipeModule } from 'ngx-filter-pipe';

import { AutomationServiceHubSharedModule } from '../../shared';
import {
    LibraryRegistryService,
    LibraryRegistryPopupService,
    LibraryRegistryComponent,
    LibraryRegistryDetailComponent,
    LibraryRegistryDialogComponent,
    LibraryRegistryPopupComponent,
    LibraryRegistryDeletePopupComponent,
    LibraryRegistryDeleteDialogComponent,
    libraryRegistryRoute,
    libraryRegistryPopupRoute
} from './';

const ENTITY_STATES = [
    ...libraryRegistryRoute,
    ...libraryRegistryPopupRoute
];

@NgModule({
    imports: [
        AutomationServiceHubSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        FilterPipeModule
    ],
    declarations: [
        LibraryRegistryComponent,
        LibraryRegistryDetailComponent,
        LibraryRegistryDialogComponent,
        LibraryRegistryDeleteDialogComponent,
        LibraryRegistryPopupComponent,
        LibraryRegistryDeletePopupComponent
    ],
    entryComponents: [
        LibraryRegistryComponent,
        LibraryRegistryDialogComponent,
        LibraryRegistryPopupComponent,
        LibraryRegistryDeleteDialogComponent,
        LibraryRegistryDeletePopupComponent
    ],
    providers: [
        LibraryRegistryService,
        LibraryRegistryPopupService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutomationServiceHubLibraryRegistryModule {
}

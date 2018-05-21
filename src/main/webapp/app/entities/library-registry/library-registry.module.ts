import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Ng2SearchPipeModule } from 'ng2-search-filter';

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
import {
    LibraryRegistryDownloadDialogComponent,
    LibraryRegistryDownloadPopupComponent
} from './library-registry-download-dialog.component';

const ENTITY_STATES = [
    ...libraryRegistryRoute,
    ...libraryRegistryPopupRoute
];

@NgModule({
    imports: [
        AutomationServiceHubSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        Ng2SearchPipeModule
    ],
    declarations: [
        LibraryRegistryComponent,
        LibraryRegistryDetailComponent,
        LibraryRegistryDialogComponent,
        LibraryRegistryDeleteDialogComponent,
        LibraryRegistryPopupComponent,
        LibraryRegistryDeletePopupComponent,
        LibraryRegistryDownloadPopupComponent,
        LibraryRegistryDownloadDialogComponent
    ],
    entryComponents: [
        LibraryRegistryComponent,
        LibraryRegistryDialogComponent,
        LibraryRegistryPopupComponent,
        LibraryRegistryDeleteDialogComponent,
        LibraryRegistryDeletePopupComponent,
        LibraryRegistryDownloadPopupComponent,
        LibraryRegistryDownloadDialogComponent
    ],
    providers: [
        LibraryRegistryService,
        LibraryRegistryPopupService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutomationServiceHubLibraryRegistryModule {
}

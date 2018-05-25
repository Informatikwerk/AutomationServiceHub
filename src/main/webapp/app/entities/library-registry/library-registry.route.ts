import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LibraryRegistryComponent } from './library-registry.component';
import { LibraryRegistryDetailComponent } from './library-registry-detail.component';
import { LibraryRegistryPopupComponent } from './library-registry-dialog.component';
import { LibraryRegistryDeletePopupComponent } from './library-registry-delete-dialog.component';
import { LibraryRegistryDownloadPopupComponent } from './library-registry-download-dialog.component';

export const libraryRegistryRoute: Routes = [
    {
        path: 'library-registry',
        component: LibraryRegistryComponent,
        data: {
            authorities: [],
            pageTitle: 'automationServiceHubApp.libraryRegistry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'library-registry/:id',
        component: LibraryRegistryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.libraryRegistry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const libraryRegistryPopupRoute: Routes = [
    {
        path: 'library-registry-new',
        component: LibraryRegistryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.libraryRegistry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'library-registry/:id/edit',
        component: LibraryRegistryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.libraryRegistry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'library-registry/:id/download',
        component: LibraryRegistryDownloadPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.libraryRegistry.home.download'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'library-registry/:id/delete',
        component: LibraryRegistryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.libraryRegistry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

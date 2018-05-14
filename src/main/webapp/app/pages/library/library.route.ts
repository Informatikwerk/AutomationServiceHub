import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { LibraryComponent } from './library.component';

export const LibraryRoute: Routes = [
    {
        path: 'library-library',
        component: LibraryComponent,
        data: {
            authorities: [],
            pageTitle: 'automationServiceHubApp.library-library.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
];

import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SourcesComponent } from './sources.component';
import { SourcesDetailComponent } from './sources-detail.component';
import { SourcesPopupComponent } from './sources-dialog.component';
import { SourcesDeletePopupComponent } from './sources-delete-dialog.component';

export const sourcesRoute: Routes = [
    {
        path: 'sources',
        component: SourcesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.sources.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sources/:id',
        component: SourcesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.sources.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sourcesPopupRoute: Routes = [
    {
        path: 'sources-new',
        component: SourcesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.sources.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sources/:id/edit',
        component: SourcesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.sources.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sources/:id/delete',
        component: SourcesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.sources.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

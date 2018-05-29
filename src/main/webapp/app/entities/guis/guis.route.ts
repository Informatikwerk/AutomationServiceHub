import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { GuisComponent } from './guis.component';
import { GuisDetailComponent } from './guis-detail.component';
import { GuisPopupComponent } from './guis-dialog.component';
import { GuisDeletePopupComponent } from './guis-delete-dialog.component';

export const guisRoute: Routes = [
    {
        path: 'guis',
        component: GuisComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.guis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'guis/:id',
        component: GuisDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.guis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const guisPopupRoute: Routes = [
    {
        path: 'guis-new',
        component: GuisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.guis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'guis/:id/edit',
        component: GuisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.guis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'guis/:id/delete',
        component: GuisDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.guis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

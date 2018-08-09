import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { NodesComponent } from './nodes.component';
import { NodesDetailComponent } from './nodes-detail.component';
import { NodesPopupComponent } from './nodes-dialog.component';
import { NodesDeletePopupComponent } from './nodes-delete-dialog.component';

export const nodesRoute: Routes = [
    {
        path: 'nodes',
        component: NodesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'nodes/:id',
        component: NodesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nodesPopupRoute: Routes = [
    {
        path: 'nodes-new',
        component: NodesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nodes/:id/edit',
        component: NodesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nodes/:id/delete',
        component: NodesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

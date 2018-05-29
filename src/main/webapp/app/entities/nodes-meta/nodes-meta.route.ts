import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { NodesMetaComponent } from './nodes-meta.component';
import { NodesMetaDetailComponent } from './nodes-meta-detail.component';
import { NodesMetaPopupComponent } from './nodes-meta-dialog.component';
import { NodesMetaDeletePopupComponent } from './nodes-meta-delete-dialog.component';

export const nodesMetaRoute: Routes = [
    {
        path: 'nodes-meta',
        component: NodesMetaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodesMeta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'nodes-meta/:id',
        component: NodesMetaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodesMeta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nodesMetaPopupRoute: Routes = [
    {
        path: 'nodes-meta-new',
        component: NodesMetaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodesMeta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nodes-meta/:id/edit',
        component: NodesMetaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodesMeta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nodes-meta/:id/delete',
        component: NodesMetaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodesMeta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

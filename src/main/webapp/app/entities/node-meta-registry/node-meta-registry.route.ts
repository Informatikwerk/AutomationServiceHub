import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { NodeMetaRegistryComponent } from './node-meta-registry.component';
import { NodeMetaRegistryDetailComponent } from './node-meta-registry-detail.component';
import { NodeMetaRegistryPopupComponent } from './node-meta-registry-dialog.component';
import { NodeMetaRegistryDeletePopupComponent } from './node-meta-registry-delete-dialog.component';

export const nodeMetaRegistryRoute: Routes = [
    {
        path: 'node-meta-registry',
        component: NodeMetaRegistryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodeMetaRegistry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'node-meta-registry/:id',
        component: NodeMetaRegistryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodeMetaRegistry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nodeMetaRegistryPopupRoute: Routes = [
    {
        path: 'node-meta-registry-new',
        component: NodeMetaRegistryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodeMetaRegistry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'node-meta-registry/:id/edit',
        component: NodeMetaRegistryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodeMetaRegistry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'node-meta-registry/:id/delete',
        component: NodeMetaRegistryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodeMetaRegistry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

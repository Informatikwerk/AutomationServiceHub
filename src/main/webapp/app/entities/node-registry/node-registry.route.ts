import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { NodeRegistryComponent } from './node-registry.component';
import { NodeRegistryDetailComponent } from './node-registry-detail.component';
import { NodeRegistryPopupComponent } from './node-registry-dialog.component';
import { NodeRegistryDeletePopupComponent } from './node-registry-delete-dialog.component';

export const nodeRegistryRoute: Routes = [
    {
        path: 'node-registry',
        component: NodeRegistryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodeRegistry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'node-registry/:id',
        component: NodeRegistryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodeRegistry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nodeRegistryPopupRoute: Routes = [
    {
        path: 'node-registry-new',
        component: NodeRegistryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodeRegistry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'node-registry/:id/edit',
        component: NodeRegistryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodeRegistry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'node-registry/:id/delete',
        component: NodeRegistryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.nodeRegistry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { GuiRegisterComponent } from './gui-register.component';
import { GuiRegisterDetailComponent } from './gui-register-detail.component';
import { GuiRegisterPopupComponent } from './gui-register-dialog.component';
import { GuiRegisterDeletePopupComponent } from './gui-register-delete-dialog.component';

export const guiRegisterRoute: Routes = [
    {
        path: 'gui-register',
        component: GuiRegisterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.guiRegister.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'gui-register/:id',
        component: GuiRegisterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.guiRegister.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const guiRegisterPopupRoute: Routes = [
    {
        path: 'gui-register-new',
        component: GuiRegisterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.guiRegister.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gui-register/:id/edit',
        component: GuiRegisterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.guiRegister.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gui-register/:id/delete',
        component: GuiRegisterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.guiRegister.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

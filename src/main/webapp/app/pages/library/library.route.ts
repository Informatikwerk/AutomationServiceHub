import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LibraryComponent } from './library.component';
export const LibraryRoute: Routes = [
    {
        path: 'library-library',
        component: LibraryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'automationServiceHubApp.library-library.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
];

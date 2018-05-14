import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { Observable } from 'rxjs/Rx';

import { Library } from './library.model';
import { LibraryService } from './library.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-library',
    templateUrl: './library.component.html'
})
export class LibraryComponent implements OnInit {

    currentAccount: any;
    eventSubscriber: Subscription;
    isSaving: Boolean;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    libraries: Library[];

    constructor(
        private libraryService: LibraryService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        console.log('load all fired');
        this.libraries = new Array();
        for (let i = 0; i < 10; i++) {
            this.libraries.push(new Library('Cool library ' + i,
                'Do cool stuff ',
                'Arduino',
                '1.' + i,
                'Jacob',
                'www.home.page'));
        }
    }

    ngOnInit() {

        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });

    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

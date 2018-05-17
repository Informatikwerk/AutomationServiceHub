import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LibraryRegistry } from './library-registry.model';
import { LibraryRegistryService } from './library-registry.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-library-registry',
    templateUrl: './library-registry.component.html'
})
export class LibraryRegistryComponent implements OnInit, OnDestroy {
    libraryRegistries: LibraryRegistry[];
    currentAccount: any;
    eventSubscriber: Subscription;
    term: any = {name: ''};

    constructor(
        private libraryRegistryService: LibraryRegistryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.libraryRegistryService.query().subscribe(
            (res: HttpResponse<LibraryRegistry[]>) => {
                this.libraryRegistries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLibraryRegistries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LibraryRegistry) {
        return item.id;
    }

    registerChangeInLibraryRegistries() {
        this.eventSubscriber = this.eventManager.subscribe('libraryRegistryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

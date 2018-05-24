import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';


import { LibraryRegistry } from './library-registry.model';
import { LibraryRegistryService } from './library-registry.service';
import { Sources, SourcesService } from '../sources';
import { Principal, User } from '../../shared';

@Component({
    selector: 'jhi-library-registry-detail',
    templateUrl: './library-registry-detail.component.html'
})
export class LibraryRegistryDetailComponent implements OnInit, OnDestroy {

    libraryRegistry: LibraryRegistry;
    sources: Sources[];
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    currentAccount: User;
    admin = false;

    constructor(
        private eventManager: JhiEventManager,
        private libraryRegistryService: LibraryRegistryService,
        private route: ActivatedRoute,
        private sourcesService: SourcesService,
        private jhiAlertService: JhiAlertService,
        private principal: Principal
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
            this.loadSources(params['id'])
        });
        this.principal.identity().then((account: User) => {
            this.admin = account.authorities.indexOf('ROLE_ADMIN') !== -1;
            this.currentAccount = account;
        });
        this.registerChangeInLibraryRegistries();
    }

    load(id) {
        this.libraryRegistryService.find(id)
            .subscribe((libraryRegistryResponse: HttpResponse<LibraryRegistry>) => {
                this.libraryRegistry = libraryRegistryResponse.body;
            });
    }

    loadSources(id) {
        this.sourcesService.findByLibraryId(id).subscribe(
            (res: HttpResponse<Sources[]>) => {
                this.sources = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLibraryRegistries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'libraryRegistryListModification',
            (response) => this.load(this.libraryRegistry.id)
        );
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

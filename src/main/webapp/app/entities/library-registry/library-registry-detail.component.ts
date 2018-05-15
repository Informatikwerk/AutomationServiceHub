import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LibraryRegistry } from './library-registry.model';
import { LibraryRegistryService } from './library-registry.service';

@Component({
    selector: 'jhi-library-registry-detail',
    templateUrl: './library-registry-detail.component.html'
})
export class LibraryRegistryDetailComponent implements OnInit, OnDestroy {

    libraryRegistry: LibraryRegistry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private libraryRegistryService: LibraryRegistryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLibraryRegistries();
    }

    load(id) {
        this.libraryRegistryService.find(id)
            .subscribe((libraryRegistryResponse: HttpResponse<LibraryRegistry>) => {
                this.libraryRegistry = libraryRegistryResponse.body;
            });
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
}

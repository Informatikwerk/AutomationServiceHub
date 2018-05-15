import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LibraryRegistry } from './library-registry.model';
import { LibraryRegistryPopupService } from './library-registry-popup.service';
import { LibraryRegistryService } from './library-registry.service';

@Component({
    selector: 'jhi-library-registry-dialog',
    templateUrl: './library-registry-dialog.component.html'
})
export class LibraryRegistryDialogComponent implements OnInit {

    libraryRegistry: LibraryRegistry;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private libraryRegistryService: LibraryRegistryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.libraryRegistry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.libraryRegistryService.update(this.libraryRegistry));
        } else {
            this.subscribeToSaveResponse(
                this.libraryRegistryService.create(this.libraryRegistry));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LibraryRegistry>>) {
        result.subscribe((res: HttpResponse<LibraryRegistry>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LibraryRegistry) {
        this.eventManager.broadcast({ name: 'libraryRegistryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-library-registry-popup',
    template: ''
})
export class LibraryRegistryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private libraryRegistryPopupService: LibraryRegistryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.libraryRegistryPopupService
                    .open(LibraryRegistryDialogComponent as Component, params['id']);
            } else {
                this.libraryRegistryPopupService
                    .open(LibraryRegistryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

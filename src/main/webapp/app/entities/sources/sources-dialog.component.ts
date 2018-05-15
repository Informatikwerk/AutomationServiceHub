import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Sources } from './sources.model';
import { SourcesPopupService } from './sources-popup.service';
import { SourcesService } from './sources.service';
import { LibraryRegistry, LibraryRegistryService } from '../library-registry';

@Component({
    selector: 'jhi-sources-dialog',
    templateUrl: './sources-dialog.component.html'
})
export class SourcesDialogComponent implements OnInit {

    sources: Sources;
    isSaving: boolean;

    libraryregistries: LibraryRegistry[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private sourcesService: SourcesService,
        private libraryRegistryService: LibraryRegistryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.libraryRegistryService.query()
            .subscribe((res: HttpResponse<LibraryRegistry[]>) => { this.libraryregistries = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sources.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sourcesService.update(this.sources));
        } else {
            this.subscribeToSaveResponse(
                this.sourcesService.create(this.sources));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Sources>>) {
        result.subscribe((res: HttpResponse<Sources>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Sources) {
        this.eventManager.broadcast({ name: 'sourcesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLibraryRegistryById(index: number, item: LibraryRegistry) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sources-popup',
    template: ''
})
export class SourcesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sourcesPopupService: SourcesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sourcesPopupService
                    .open(SourcesDialogComponent as Component, params['id']);
            } else {
                this.sourcesPopupService
                    .open(SourcesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LibraryRegistry } from './library-registry.model';
import { LibraryRegistryPopupService } from './library-registry-popup.service';
import { LibraryRegistryService } from './library-registry.service';
import { Sources, SourcesService } from '../sources';


@Component({
    selector: 'jhi-library-registry-dialog',
    templateUrl: './library-registry-dialog.component.html'
})
export class LibraryRegistryDialogComponent implements OnInit {

    libraryRegistry: LibraryRegistry;
    sourceCodes: string[] = new Array();
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private libraryRegistryService: LibraryRegistryService,
        private sourcesService: SourcesService,
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
        console.log('Save booom');
        console.log(this.sourceCodes);
        this.isSaving = true;
        if (this.libraryRegistry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.libraryRegistryService.update(this.libraryRegistry));
        } else {
            this.subscribeToSaveResponse(
                this.libraryRegistryService.create(this.libraryRegistry));
        }
    }

    onFileChange(event) {
        if (event.target.files && event.target.files.length) {
            const files = event.target.files;
            for (const singleFile of files) {
                this.setupReader(singleFile);
            }
        }
    }

    private setupReader(file) {
        const reader = new FileReader();
        let sources = this.sourceCodes;
        reader.onload = function (e) {
            const sourceCode = reader.result;
            console.log('text ', sourceCode);
            sources.push(sourceCode);
        }
        reader.readAsText(file);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LibraryRegistry>>) {
        result.subscribe((res: HttpResponse<LibraryRegistry>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LibraryRegistry) {
        this.eventManager.broadcast({name: 'libraryRegistryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
        for (const sourceCode of this.sourceCodes) {
            // this.subscribeToSaveResponse(
            //     this.sourcesService.create(new Sources(undefined, sourceCode, result)));
            console.log('save file ', new Sources(undefined, sourceCode, result));
        }
        console.log('Save done ', result.id);
        console.log('Save done ', result);
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
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
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

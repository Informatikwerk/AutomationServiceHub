import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LibraryRegistry } from './library-registry.model';
import { LibraryRegistryPopupService } from './library-registry-popup.service';
import { LibraryRegistryService } from './library-registry.service';
import { SourcesService } from '../sources';
import { Principal } from '../../shared';
import { RealmKeyGeneratorService } from './realm-key-generator.service';

@Component({
    selector: 'jhi-library-registry-download-dialog',
    templateUrl: './library-registry-download-dialog.component.html'
})
export class LibraryRegistryDownloadDialogComponent implements OnInit {

    libraryRegistry: LibraryRegistry;
    realmKey: string;
    sourceCodes: any [] = new Array();
    isSaving: boolean;
    files: File[];

    constructor(
        public activeModal: NgbActiveModal,
        private libraryRegistryService: LibraryRegistryService,
        private sourcesService: SourcesService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private realmKeyGeneratorService: RealmKeyGeneratorService
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.principal.identity().then((account) => {
            this.libraryRegistry.author = account.firstName;
        });

    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    generateRealmKey() {
        this.subscribeToSaveResponse(
            this.realmKeyGeneratorService.get());
    }

    download() {
        console.log('realmkey ', this.realmKey);
        // this.isSaving = true;
        // this.subscribeToSaveResponse(
        //     this.realmKeyGeneratorService.get();
    }


    private subscribeToSaveResponse(result: Observable<HttpResponse<string>>) {
        result.subscribe((res: HttpResponse<string>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: string) {
        console.log("result is ", result);
        this.eventManager.broadcast({name: 'libraryRegistryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        console.log("errrpr")
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-library-registry-download-popup',
    template: ''
})
export class LibraryRegistryDownloadPopupComponent implements OnInit, OnDestroy {

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
                    .open(LibraryRegistryDownloadDialogComponent as Component, params['id']);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

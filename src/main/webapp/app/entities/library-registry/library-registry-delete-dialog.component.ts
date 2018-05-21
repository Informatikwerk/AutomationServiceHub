import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LibraryRegistry } from './library-registry.model';
import { LibraryRegistryPopupService } from './library-registry-popup.service';
import { LibraryRegistryService } from './library-registry.service';
import { SourcesService } from '../sources';

@Component({
    selector: 'jhi-library-registry-delete-dialog',
    templateUrl: './library-registry-delete-dialog.component.html'
})
export class LibraryRegistryDeleteDialogComponent {

    libraryRegistry: LibraryRegistry;

    constructor(
        private libraryRegistryService: LibraryRegistryService,
        private sourcesService: SourcesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sourcesService.deleteByLibraryId(id).subscribe((sourcesResponce) => {
            this.libraryRegistryService.delete(id).subscribe((libararyResponse) => {
                this.eventManager.broadcast({
                    name: 'libraryRegistryListModification',
                    content: 'Deleted an libraryRegistry'
                });
                this.activeModal.dismiss(true);
            });
        });
    }
}

@Component({
    selector: 'jhi-library-registry-delete-popup',
    template: ''
})
export class LibraryRegistryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private libraryRegistryPopupService: LibraryRegistryPopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.libraryRegistryPopupService
                .open(LibraryRegistryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

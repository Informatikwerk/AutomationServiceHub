import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Sources } from './sources.model';
import { SourcesPopupService } from './sources-popup.service';
import { SourcesService } from './sources.service';

@Component({
    selector: 'jhi-sources-delete-dialog',
    templateUrl: './sources-delete-dialog.component.html'
})
export class SourcesDeleteDialogComponent {

    sources: Sources;

    constructor(
        private sourcesService: SourcesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sourcesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sourcesListModification',
                content: 'Deleted an sources'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sources-delete-popup',
    template: ''
})
export class SourcesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sourcesPopupService: SourcesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sourcesPopupService
                .open(SourcesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NodesMeta } from './nodes-meta.model';
import { NodesMetaPopupService } from './nodes-meta-popup.service';
import { NodesMetaService } from './nodes-meta.service';

@Component({
    selector: 'jhi-nodes-meta-delete-dialog',
    templateUrl: './nodes-meta-delete-dialog.component.html'
})
export class NodesMetaDeleteDialogComponent {

    nodesMeta: NodesMeta;

    constructor(
        private nodesMetaService: NodesMetaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nodesMetaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'nodesMetaListModification',
                content: 'Deleted an nodesMeta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-nodes-meta-delete-popup',
    template: ''
})
export class NodesMetaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nodesMetaPopupService: NodesMetaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.nodesMetaPopupService
                .open(NodesMetaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
